package input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IBank2ReaderParsingTest {

    IBank2Reader reader = new IBank2Reader();
    Method parse = IBank2Reader.class.getDeclaredMethod("parse", String.class);

    public IBank2ReaderParsingTest() throws NoSuchMethodException {
    }

    @BeforeEach
    void setAccessible(){
        parse.setAccessible(true);
    }


    @Test
    void shouldCorrectParse() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "name=value");

        Assertions.assertAll(
                ()-> Assertions.assertEquals("name", reader.getContent().getName(0)),
                ()-> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfNoEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "namevalue");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfStartsWithEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "=namevalue");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfEndsWithEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "namevalue=");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfOnlyEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "=");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfSeveralEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "name=value=city");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfSeveralEqualsInARow() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "name==value");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfEmpty() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfStartsWithSpaceAndEqual() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, " =namevalue");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldNotParseIfEndsWithSpaceAndEqual() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "namevalue= ");

        Assertions.assertAll(
                ()-> Assertions.assertEquals(0, reader.getContent().size()),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getName(0)),
                ()-> Assertions.assertThrows(IndexOutOfBoundsException.class, ()->reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfSpaceAfterEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "name= value");

        Assertions.assertAll(
                ()-> Assertions.assertEquals("name", reader.getContent().getName(0)),
                ()-> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfSpaceBeforeEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "name =value");

        Assertions.assertAll(
                ()-> Assertions.assertEquals("name", reader.getContent().getName(0)),
                ()-> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfSpaceBeforeAndAfterEquals() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "name = value");

        Assertions.assertAll(
                ()-> Assertions.assertEquals("name", reader.getContent().getName(0)),
                ()-> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfStartsWithSpace() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, "name=value ");

        Assertions.assertAll(
                ()-> Assertions.assertEquals("name", reader.getContent().getName(0)),
                ()-> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }

    @Test
    void shouldParseIfEndsWithSpace() throws InvocationTargetException, IllegalAccessException {
        parse.invoke(reader, " name=value");

        Assertions.assertAll(
                ()-> Assertions.assertEquals("name", reader.getContent().getName(0)),
                ()-> Assertions.assertEquals("value", reader.getContent().getValue(0))
        );
    }
}
