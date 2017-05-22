package ru.ensemplix.shop.matcher;

import org.junit.Test;
import ru.ensemplix.shop.ShopItemStack;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.ensemplix.shop.matcher.SimpleShopItemMatcher.Capability.IGNORE_DATA;
import static ru.ensemplix.shop.matcher.SimpleShopItemMatcher.Capability.IGNORE_STATE;

public class SimpleShopItemMatcherTest {

    private final ShopItemStack itemStack = createItemStack("minecraft:wool", 15);
    private final SimpleShopItemMatcher matcher = new SimpleShopItemMatcher(itemStack);

    @Test
    public void testMatch() {
        assertTrue(matcher.match(itemStack));
    }

    @Test
    public void testMatchWrongId() {
        assertFalse(matcher.match(createItemStack("minecraft:brick", 15)));
    }

    @Test
    public void testMatchWrongData() {
        assertFalse(matcher.match(createItemStack("minecraft:wool", 5)));
    }

    @Test
    public void testMatchWrongState() {
        assertFalse(matcher.match(createItemStack("minecraft:wool", 5, new byte[0])));
    }

    @Test
    public void testMatchIgnoreData() {
        SimpleShopItemMatcher matcherIgnoreData = new SimpleShopItemMatcher(itemStack, IGNORE_DATA);
        assertTrue(matcherIgnoreData.match(createItemStack("minecraft:wool", 5)));
    }

    @Test
    public void testMatchIgnoreState() {
        SimpleShopItemMatcher matcherIgnoreData = new SimpleShopItemMatcher(itemStack, IGNORE_STATE);
        assertTrue(matcherIgnoreData.match(createItemStack("minecraft:wool", 15, new byte[0])));
    }

    private ShopItemStack createItemStack(String id, int data) {
        return createItemStack(id, data, null);
    }

    private ShopItemStack createItemStack(String id, int data, byte[] state) {
        return new ShopItemStack(id, data, state);
    }

}
