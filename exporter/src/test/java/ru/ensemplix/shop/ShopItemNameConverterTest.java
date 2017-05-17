package ru.ensemplix.shop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShopItemNameConverterTest {

    @Test
    public void testConvertWool() {
        assertEquals("БЕЛАЯ_ШЕРСТЬ", ShopItemNameConverter.convert("Белая шерсть"));
    }

    @Test
    public void testConvertWoolWithSymbol() {
        assertEquals("БЕЛАЯ_ШЕРСТЬ", ShopItemNameConverter.convert("Белая - шерсть"));
    }

    @Test
    public void testConvertWoolWithWhitespaces() {
        assertEquals("БЕЛАЯ_ШЕРСТЬ", ShopItemNameConverter.convert("Белая   шерсть"));
    }

    @Test
    public void testConvertCentrifuge() {
        assertEquals("ТЕРМАЛЬ_ЦЕНТРИФУ", ShopItemNameConverter.convert("Термальная центрифуга"));
    }

}
