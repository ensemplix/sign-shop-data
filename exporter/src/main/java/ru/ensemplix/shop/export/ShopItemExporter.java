package ru.ensemplix.shop.export;

import ru.ensemplix.shop.ShopItem;

import java.nio.file.Path;
import java.util.List;

/**
 * Реализация данного интерфейса позволяет создавать разные
 * типы экспорта списка предметов в файл.
 */
public interface ShopItemExporter {

    /**
     * Экспортирует список предметов в указанный файл.
     *
     * @param items Список предметов, который экспортируем.
     * @param path Файл, в который мы экспортируем список предметов.
     */
    void export(List<ShopItem> items, Path path);

}
