package ru.ensemplix.shop.matcher;

import org.junit.Test;
import ru.ensemplix.shop.ShopItemStack;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.ensemplix.shop.matcher.SimpleShopItemMatcher.Capability.*;

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
    public void testMatchIgnoreData() {
        SimpleShopItemMatcher matcherIgnoreData = new SimpleShopItemMatcher(itemStack, IGNORE_DATA);
        assertTrue(matcherIgnoreData.match(createItemStack("minecraft:wool", 5)));
    }

    private ShopItemStack createItemStack(String id, int data) {
        ShopItemStack itemStack =  mock(ShopItemStack.class);
        when(itemStack.getId()).thenReturn(id);
        when(itemStack.getData()).thenReturn(data);

        return itemStack;
    }

}
