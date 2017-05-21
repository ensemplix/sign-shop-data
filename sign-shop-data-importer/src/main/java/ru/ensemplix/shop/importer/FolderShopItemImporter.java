package ru.ensemplix.shop.importer;

import ru.ensemplix.shop.ShopItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Загружает все файлы из папки для указанного импортера.
 */
public class FolderShopItemImporter implements ShopItemImporter {

    private final ShopItemImporter importer;

    private FolderShopItemImporter(ShopItemImporter importer) {
        this.importer = importer;
    }

    @Override
    public List<ShopItem> importFromFile(Path folder) {
        List<ShopItem> items = new ArrayList<>();

        try {
            for(Path path : Files.newDirectoryStream(folder)) {
                items.addAll(importer.importFromFile(path));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return items;
    }

}
