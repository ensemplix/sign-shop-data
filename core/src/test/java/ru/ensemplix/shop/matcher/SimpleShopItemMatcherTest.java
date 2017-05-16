package ru.ensemplix.shop.matcher;

import org.junit.Test;
import ru.ensemplix.shop.ShopItemStack;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.ensemplix.shop.matcher.SimpleShopItemMatcher.Capability.IGNORE_DATA;

public class SimpleShopItemMatcherTest {

    private final ShopItemStack itemStack = new ShopItemStack("minecraft:wool", 15);
    private final SimpleShopItemMatcher matcher = new SimpleShopItemMatcher(itemStack);

    @Test
    public void testMatch() {
        assertTrue(matcher.match(itemStack));
    }

    @Test
    public void testMatchWrongId() {
        assertFalse(matcher.match(new ShopItemStack("minecraft:brick", 15)));
    }

    @Test
    public void testMatchWrongData() {
        assertFalse(matcher.match(new ShopItemStack("minecraft:wool", 5)));
    }

    @Test
    public void testMatchIgnoreData() {
        SimpleShopItemMatcher matcherIgnoreData = new SimpleShopItemMatcher(itemStack, IGNORE_DATA);
        assertTrue(matcherIgnoreData.match(new ShopItemStack("minecraft:wool", 5)));
    }

}
