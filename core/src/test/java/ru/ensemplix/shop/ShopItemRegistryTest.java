package ru.ensemplix.shop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ShopItemRegistryTest {

    @Rule
    public final ExpectedException expected = ExpectedException.none();
    private final ShopItemRegistry shopItemRegistry = new ShopItemRegistry();

    @Test
    public void testGetItemById() {
        ShopItem shopItem = createShopItem("minecraft:brick", "КИРПИЧ");
        shopItemRegistry.addItem(shopItem);

        assertEquals(shopItem, shopItemRegistry.getItemById("minecraft:brick"));
    }

    @Test
    public void testGetItemByName() {
        ShopItem shopItem = createShopItem("minecraft:brick", "КИРПИЧ");
        shopItemRegistry.addItem(shopItem);

        assertEquals(shopItem, shopItemRegistry.getItemByName("КИРПИЧ"));
    }

    @Test
    public void testAddItemIdAlreadyRegistered() {
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Item with id minecraft:brick already registered");

        shopItemRegistry.addItem(createShopItem("minecraft:brick", "КИРПИЧ"));
        shopItemRegistry.addItem(createShopItem("minecraft:brick", "БЕЛАЯ_ШЕРСТЬ"));
    }

    @Test
    public void testAddItemNameAlreadyRegistered() {
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Item with name БЕЛАЯ_ШЕРСТЬ already registered");

        shopItemRegistry.addItem(createShopItem("minecraft:brick", "БЕЛАЯ_ШЕРСТЬ"));
        shopItemRegistry.addItem(createShopItem("minecraft:wool", "БЕЛАЯ_ШЕРСТЬ"));
    }

    private ShopItem createShopItem(String id, String name) {
        return new ShopItem(id, name, null, null);
    }

}
