package ru.ensemplix.shop;

import java.util.HashMap;
import java.util.Map;

/**
 * Предоставляет информацию, о всех возможных предметов в торговле.
 */
public class ShopItemRegistry {

    // Список товаров, где в качестве ключа выступает название товара.
    private final Map<String, ShopItem> itemsByName = new HashMap<>();
    // Список товаров, где в качестве ключа выступает уникальный идентификатор.
    private final Map<String, ShopItem> itemsById = new HashMap<>();

    /**
     * Добавляет переданный товар в перечень.
     *
     * @param item Товар, который добавляем в перечень.
     */
    public void addItem(ShopItem item) {
        String id = item.getId();
        String name = item.getName();

        if(itemsById.containsKey(id)) {
            throw new IllegalArgumentException("Item with id " + id + " already registered");
        }

        if(itemsByName.containsKey(name)) {
            throw new IllegalArgumentException("Item with name " + name + " already registered");
        }

        itemsByName.put(name, item);
        itemsById.put(id, item);
    }

    /**
     * Возвращает товар по его уникальному идентификатору. Например "minecraft:brick".
     *
     * @param id Уникальный идентификатор товара.
     * @return Товар, который получил по переданому идентификатору.
     */
    public ShopItem getItemById(String id) {
        return itemsById.get(id);
    }

    /**
     * Возвращает товар по его названию. Например "БЕЛАЯ_ШЕРСТЬ".
     *
     * @param name Название товара.
     * @return Товар, который получил по его названию.
     */
    public ShopItem getItemByName(String name) {
        return itemsByName.get(name);
    }

}
