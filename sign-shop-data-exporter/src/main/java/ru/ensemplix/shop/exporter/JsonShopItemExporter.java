package ru.ensemplix.shop.exporter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ensemplix.shop.ShopItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Сохраняет список предметов в формате JSON.
 */
public class JsonShopItemExporter implements ShopItemExporter {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void exportToFile(List<ShopItem> items, Path path) {
        try {
            Files.write(path, gson.toJson(items).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
