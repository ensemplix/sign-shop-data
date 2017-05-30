package ru.ensemplix.shop.importer;

import org.junit.Test;
import ru.ensemplix.shop.ShopItem;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ZipShopItemImporterTest {

    private final ShopItemImporter jsonImporter = new JsonShopItemImporter();
    private final ShopItemImporter zipImporter = new ZipShopItemImporter(jsonImporter);

    @Test
    public void testImportFromFile() throws URISyntaxException {
        Path path = Paths.get(getClass().getResource("/data.zip").toURI());
        List<ShopItem> items = zipImporter.importFromFile(path);

        assertEquals(5, items.size());
    }

}
