package ru.ensemplix.shop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.ensemplix.shop.ShopItemNameConverter.*;

public class ShopItemNameConverterTest {

    @Test
    public void testConvertWool() {
        assertEquals("БЕЛАЯ_ШЕРСТЬ", convert("Белая шерсть"));
    }

    @Test
    public void testConvertWoolWithSymbol() {
        assertEquals("БЕЛАЯ_ШЕРСТЬ", convert("Белая - шерсть"));
    }

    @Test
    public void testConvertWoolWithWhitespaces() {
        assertEquals("БЕЛАЯ_ШЕРСТЬ", convert("Белая   шерсть"));
    }

    @Test
    public void testConvertCentrifuge() {
        assertEquals("ТЕРМАЛЬН_ЦЕНТР", convert("Термальная центрифуга"));
    }

    @Test
    public void testConvertWire() {
        assertEquals("ИЗОЛ_ВЫСОК_ПРОВ", convert("Изолированный высоковольтный провод"));
    }

    @Test
    public void testConvertTropicalTree() {
        assertEquals("ДОСКИ_ИЗ_ТР_ДЕР", convert("Доски из тропического дерева"));
    }

    @Test
    public void testConvertYellowFiredClay() {
        assertEquals("ЖЁЛТ_ОБОЖ_ГЛИНА", convert("Жёлтая обожжённая глина"));
    }

}
