package ru.ensemplix.shop.importer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.ensemplix.shop.ShopItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Загружает список предметов из формата JSON.
 */
public class JsonShopItemImporter implements ShopItemImporter {

    private final Gson gson = new Gson();

    @Override
    public List<ShopItem> importFromFile(Path path) {
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            return gson.fromJson(reader, new TypeToken<List<ShopItem>>(){}.getType());
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
