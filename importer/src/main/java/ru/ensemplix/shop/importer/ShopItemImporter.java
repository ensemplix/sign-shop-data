package ru.ensemplix.shop.importer;

import ru.ensemplix.shop.ShopItem;

import java.nio.file.Path;
import java.util.List;

/**
 * Реализация данного интерфейса позволяет создавать разные
 * типы импорта списка предметов из файла.
 */
public interface ShopItemImporter {

    /**
     * Импортирует список предметов из указанного файла.
     *
     * @param path Файл, из которого мы импортируем список предметов.
     */
    List<ShopItem> importFromFile(Path path);

}
