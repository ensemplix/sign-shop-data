package ru.ensemplix.shop.importer;

import org.junit.Test;
import ru.ensemplix.shop.ShopItem;
import ru.ensemplix.shop.ShopItemStack;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonShopItemImporterTest {

    private final ShopItemImporter importer = new JsonShopItemImporter();

    @Test
    public void testImportFromFile() throws URISyntaxException {
        Path path = Paths.get(getClass().getResource("/minecraft.json").toURI());
        List<ShopItem> items = importer.importFromFile(path);

        assertEquals(5, items.size());
        assertItem(items.get(0), "КАМЕНЬ", "minecraft:stone");
        assertItem(items.get(1), "БЛОК_ТРАВЫ", "minecraft:grass");
        assertItem(items.get(2), "ЗЕМЛЯ", "minecraft:dirt");
        assertItem(items.get(3), "ПОДЗОЛ", "minecraft:dirt", 2);
        assertItem(items.get(4), "БУЛЫЖНИК", "minecraft:cobblestone");
    }

    private void assertItem(ShopItem item, String name, String id) {
        assertItem(item, name, id, 0);
    }

    private void assertItem(ShopItem item, String name, String id, int data) {
        ShopItemStack itemStack = item.getItemStack();

        assertEquals(id, itemStack.getId());
        assertEquals(data, itemStack.getData());
        assertEquals(name, item.getName());
    }

}
