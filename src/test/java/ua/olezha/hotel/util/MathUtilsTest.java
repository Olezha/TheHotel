package ua.olezha.hotel.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathUtilsTest {

    @Test
    public void cleanIntTest() {
        assertEquals("123", MathUtils.cleanNumber("123"));
    }

    @Test
    public void doubleTest() {
        assertEquals("123.1", MathUtils.cleanNumber("123.1"));
        assertEquals("123.1", MathUtils.cleanNumber("123,1"));
    }

    @Test
    public void mereThanOneDotTest() {
        assertEquals("123.1", MathUtils.cleanNumber("1,23.1"));
        assertEquals("123.1", MathUtils.cleanNumber("123,,1"));
    }

    @Test
    public void onlySpecialCharacterTest() {
        assertEquals("", MathUtils.cleanNumber("."));
        assertEquals("", MathUtils.cleanNumber(","));
        assertEquals("", MathUtils.cleanNumber("-"));
        assertEquals("", MathUtils.cleanNumber("-."));
    }
}
