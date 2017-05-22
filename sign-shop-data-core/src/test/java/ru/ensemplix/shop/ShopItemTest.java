package ru.ensemplix.shop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ShopItemTest {

    @Rule
    public final ExpectedException expected = ExpectedException.none();

    @Test
    public void testNameLengthTooLong() {
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Item name can't be longer than 16 characters");

        new ShopItem("ABCDE_1234567_ABCDE", null);
    }

}
