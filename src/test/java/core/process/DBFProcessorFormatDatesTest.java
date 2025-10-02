package core.process;

import config.Props;
import core.data.content.imported.ImportContent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DBFProcessorFormatDatesTest {

    ImportContent content = new ImportContent();
    DBFProcessor processor;

    Method formatDates = DBFProcessor.class.getDeclaredMethod("formatDates");

    static MockedStatic<Props> utilityProps = Mockito.mockStatic(Props.class);

    public DBFProcessorFormatDatesTest() throws NoSuchMethodException {
    }

    @AfterAll
    static void closeMocks() {
        utilityProps.close();
    }

    @BeforeEach
    void setAccessible() {
        formatDates.setAccessible(true);
    }

    void conversionFormat(String format) {
        utilityProps.when(() -> Props.getDateFormat()).thenReturn(format);
    }

    void invokeFormatDates(String testCase) {
        content.add("DATE", testCase);
        processor = new DBFProcessor(content);
        try {
            formatDates.invoke(processor);
        } catch (IllegalAccessException e) {
            System.out.println("Не найден метод formatDates в классе DBFProcessor");
            System.exit(1);
        } catch (InvocationTargetException e) {
            System.out.println("Ошибка при рефлексии formatDates в классе DBFProcessor");
            System.exit(1);
        }
    }

    String getActual() {
        try {
            Field field = processor.getClass().getDeclaredField("importContent");
            field.setAccessible(true);
            ImportContent importContent = (ImportContent) field.get(processor);
            return importContent.getValue(0);
        } catch (NoSuchFieldException e) {
            System.out.println("Не найдено поле importContent в классе DBFProcessor");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("Некорректный модификатор доступа поля importContent в классе DBFProcessor");
            System.exit(1);
        }
        return null;
    }

    @ParameterizedTest
    @CsvSource({
            "01.10.2025, yyyy-MM-dd, 2025-10-01",
            "01.10.2025, yyyy/MM/dd, 2025/10/01",
            "01.10.2025, dd/MM/yyyy, 01/10/2025",
            "01.10.2025, MM/dd/yyyy, 10/01/2025",
            "01.10.2025, dd.MM.yyyy, 01.10.2025",
            "01.10.2025, yyyyMMdd, 20251001"
    })
    void shouldCorrectlyConvertToValidFormat(String testCase, String format, String expected) {
        conversionFormat(format);
        invokeFormatDates(testCase);

        Assertions.assertEquals(expected, getActual());
    }

    @ParameterizedTest
    @CsvSource({
            "1.10.2025, dd.MM.yyyy, 1.10.2025",
            "A1.10.2025, dd.MM.yyyy, A1.10.2025",
            ".10.2025, dd.MM.yyyy, .10.2025",
            "01..2025, dd.MM.yyyy, 01..2025",
            "01.10, dd.MM.yyyy, 01.10",
            "USD, dd.MM.yyyy, USD",
            "12345, dd.MM.yyyy, 12345",
            "Аб Вг.123, dd.MM.yyyy, Аб Вг.123",
            "Lorem Ipsum! 123, dd.MM.yyyy, Lorem Ipsum! 123"
    })
    void shouldNotConvertWhenNotDate(String testCase, String format, String expected) {
        conversionFormat(format);
        invokeFormatDates(testCase);

        Assertions.assertEquals(expected, getActual());
    }

    @ParameterizedTest
    @CsvSource({
            "01.10.2025, unchanged, 01.10.2025",
            "1.10.2025, unchanged, 1.10.2025",
            "A1.10.2025, unchanged, A1.10.2025",
            ".10.2025, unchanged, .10.2025",
            "01..2025, unchanged, 01..2025",
            "01.10, unchanged, 01.10",
            "USD, unchanged, USD",
            "12345, unchanged, 12345",
            "Аб Вг.123, unchanged, Аб Вг.123",
            "Lorem Ipsum! 123, unchanged, Lorem Ipsum! 123",
            " , unchanged,  ",
            ", unchanged, ",
    })
    void shouldNotProcessWhenUnchanged(String testCase, String format, String expected) {
        conversionFormat(format);
        invokeFormatDates(testCase);

        Assertions.assertEquals(expected, getActual());
    }
}
