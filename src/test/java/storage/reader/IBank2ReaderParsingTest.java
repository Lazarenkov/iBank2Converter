package storage.reader;

import config.Props;
import core.service.Service;
import exceptions.ConversionInterruptedException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import storage.reader.IBank2Reader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.ArgumentMatchers.anyInt;


public class IBank2ReaderParsingTest {

    IBank2Reader reader = new IBank2Reader();
    Method parse = IBank2Reader.class.getDeclaredMethod("parse", String.class);

    static MockedStatic<Props> utilityProps = Mockito.mockStatic(Props.class);
    static MockedStatic<Service.ExitWrapper> utilityService = Mockito.mockStatic(Service.ExitWrapper.class);

    IBank2ReaderParsingTest() throws NoSuchMethodException {
    }

    @BeforeAll
    static void createMocks() {
        utilityProps.when(() -> Props.isParseEmpty()).thenReturn(true);
        utilityService.when(() -> Service.ExitWrapper.exit(anyInt())).thenAnswer(invocation -> null);
    }

    @AfterAll
    static void closeMocks() {
        utilityProps.close();
        utilityService.close();
    }


    @BeforeEach
    void setAccessible() {
        parse.setAccessible(true);
    }

    Throwable invokeParseWithThrow(String testCase) {
        Exception exception = null;
        try {
            parse.invoke(reader, testCase);
        } catch (InvocationTargetException e) {
            exception = e;
        } catch (IllegalAccessException ignore) {

        }
        return exception.getCause();
    }

    @Test
    void shouldCorrectParse() throws InvocationTargetException, IllegalAccessException {
        String testCase = "name=value";
        parse.invoke(reader, testCase);

        Assertions.assertAll(
                () -> Assertions.assertEquals("name", reader.getContent().getName(0)),
                () -> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfEndsWithEquals() throws InvocationTargetException, IllegalAccessException {
        String testCase = "name=";
        parse.invoke(reader, testCase);

        Assertions.assertAll(
                () -> Assertions.assertEquals("name", reader.getContent().getName(0)),
                () -> Assertions.assertEquals("", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfSpaceAfterEquals() throws InvocationTargetException, IllegalAccessException {
        String testCase = "name= value";
        parse.invoke(reader, testCase);

        Assertions.assertAll(
                () -> Assertions.assertEquals("name", reader.getContent().getName(0)),
                () -> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfSpaceBeforeEquals() throws InvocationTargetException, IllegalAccessException {
        String testCase = "name =value";
        parse.invoke(reader, testCase);

        Assertions.assertAll(
                () -> Assertions.assertEquals("name", reader.getContent().getName(0)),
                () -> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfSpaceBeforeAndAfterEquals() throws InvocationTargetException, IllegalAccessException {
        String testCase = "name = value";
        parse.invoke(reader, testCase);

        Assertions.assertAll(
                () -> Assertions.assertEquals("name", reader.getContent().getName(0)),
                () -> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfNoEquals() {
        String testCase = "namevalue";

        Assertions.assertAll(
                () -> Assertions.assertThrows(InvocationTargetException.class, () -> parse.invoke(reader, testCase)),
                () -> Assertions.assertInstanceOf(ConversionInterruptedException.class, invokeParseWithThrow(testCase))
        );
    }

    @Test
    void shouldNotParseIfStartsWithEquals() {
        String testCase = "=value";

        Assertions.assertAll(
                () -> Assertions.assertThrows(InvocationTargetException.class, () -> parse.invoke(reader, testCase)),
                () -> Assertions.assertInstanceOf(ConversionInterruptedException.class, invokeParseWithThrow(testCase))
        );
    }

    @Test
    void shouldNotParseIfOnlyEquals() {
        String testCase = "=";

        Assertions.assertAll(
                () -> Assertions.assertThrows(InvocationTargetException.class, () -> parse.invoke(reader, testCase)),
                () -> Assertions.assertInstanceOf(ConversionInterruptedException.class, invokeParseWithThrow(testCase))
        );
    }

    @Test
    void shouldNotParseIfSeveralEquals() {
        String testCase = "name=value=value";

        Assertions.assertAll(
                () -> Assertions.assertThrows(InvocationTargetException.class, () -> parse.invoke(reader, testCase)),
                () -> Assertions.assertInstanceOf(ConversionInterruptedException.class, invokeParseWithThrow(testCase))
        );
    }

    @Test
    void shouldNotParseIfSeveralEqualsInARow() throws InvocationTargetException, IllegalAccessException, ConversionInterruptedException {
        String testCase = "name==value";

        Assertions.assertAll(
                () -> Assertions.assertThrows(InvocationTargetException.class, () -> parse.invoke(reader, testCase)),
                () -> Assertions.assertInstanceOf(ConversionInterruptedException.class, invokeParseWithThrow(testCase))
        );
    }

    @Test
    void shouldSkipParseIfEmpty() throws InvocationTargetException, IllegalAccessException {
        String testCase = "";
        parse.invoke(reader, testCase);

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> parse.invoke(reader, testCase)),
                () -> Assertions.assertNull(reader.getContent().getName(0)),
                () -> Assertions.assertNull(reader.getContent().getValue(0))
        );
    }
}
