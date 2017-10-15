package ru.ensemplix.shop;

import ru.ensemplix.shop.matcher.SimpleShopItemMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Предоставляет информацию, о всех возможных предметов в торговле.
 */
public class ShopItemRegistry {

    // Список товаров, где в качестве ключа выступает название товара.
    private final Map<String, ShopItem> itemsByName = new HashMap<>();
    // Список товаров, где в качестве ключа выступает идентификатор.
    private final Map<String, List<ShopItem>> itemsById = new HashMap<>();

    /**
     * Добавляет переданный товар в перечень.
     *
     * @param item Товар, который добавляем в перечень.
     */
    public void addItem(ShopItem item) {
        ShopItemStack stack = item.getItemStack();
        String id = stack.getId();
        String name = item.getName();

        if(itemsByName.containsKey(name)) {
            throw new IllegalArgumentException("Item with name " + name + " already registered");
        }

        if(item.getMatcher() == null) {
            item.setMatcher(new SimpleShopItemMatcher(stack));
        }

        List<ShopItem> items = itemsById.computeIfAbsent(id, k -> new ArrayList<>());

        itemsByName.put(name, item);
        items.add(item);
    }

    /**
     * Возвращает список товаров по указанному идентификатору. Например "minecraft:brick".
     *
     * @param id Идентификатор товара.
     * @return Список товаров, который получили по переданому идентификатору.
     */
    public List<ShopItem> getItemsById(String id) {
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

    /**
     * Возвращает товар по предмету.
     * @param stack Предмет, по которому ищем товар.
     * @return Товар, который получили по предмету.
     */
    public ShopItem getItemByStack(ShopItemStack stack) {
        List<ShopItem> items = getItemsById(stack.getId());

        if(items == null) {
            return null;
        }

        for(ShopItem possibleItem : items) {
            if(possibleItem.getMatcher().match(stack)) {
                return possibleItem;
            }
        }

        return null;
    }

}
