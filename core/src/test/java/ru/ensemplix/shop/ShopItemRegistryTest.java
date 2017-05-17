package ru.ensemplix.shop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.ensemplix.shop.matcher.SimpleShopItemMatcher;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopItemRegistryTest {

    @Rule
    public final ExpectedException expected = ExpectedException.none();
    private final ShopItemRegistry shopItemRegistry = new ShopItemRegistry();

    @Test
    public void testGetItemByName() {
        ShopItem shopItem = createShopItem("minecraft:brick", "КИРПИЧ");
        shopItemRegistry.addItem(shopItem);

        assertEquals(shopItem, shopItemRegistry.getItemByName("КИРПИЧ"));
    }

    @Test
    public void testGetItemsById() {
        ShopItem shopItem = createShopItem("minecraft:brick", "КИРПИЧ");
        shopItemRegistry.addItem(shopItem);

        List<ShopItem> items = shopItemRegistry.getItemsById("minecraft:brick");

        assertEquals(shopItem, items.get(0));
        assertEquals(1, items.size());
    }

    @Test
    public void testAddItemNameAlreadyRegistered() {
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Item with name БЕЛАЯ_ШЕРСТЬ already registered");

        shopItemRegistry.addItem(createShopItem("minecraft:brick", "БЕЛАЯ_ШЕРСТЬ"));
        shopItemRegistry.addItem(createShopItem("minecraft:wool", "БЕЛАЯ_ШЕРСТЬ"));
    }

    private ShopItem createShopItem(String id, String name) {
        ShopItemStack itemStack = new ShopItemStack(id, 0, null);

        return new ShopItem(name, itemStack, new SimpleShopItemMatcher(itemStack));
    }

}
