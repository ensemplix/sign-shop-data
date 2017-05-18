package ru.ensemplix.shop;

import ru.ensemplix.shop.matcher.ShopItemMatcher;

/**
 * Представляет информацию о товаре, которым игроки могут торговать в своих магазинах.
 */
public class ShopItem {

    private final String name;
    private final ShopItemStack itemStack;
    private final ShopItemMatcher matcher;

    public ShopItem(String name, ShopItemStack itemStack, ShopItemMatcher matcher) {
        if(name.length() > 16) {
            throw new IllegalArgumentException("Item name can't be longer than 16 characters");
        }

        this.name = name;
        this.itemStack = itemStack;
        this.matcher = matcher;
    }

    /**
     * Возвращает название товара длиной не более 16 символов. Например "БЕЛАЯ_ШЕРСТЬ".
     *
     * @return Название товара.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает эталон товара, по которому мы проверяем, что игрок для покупки
     * или продажи предоставил верный предмет.
     *
     * @return Эталон товара.
     */
    public ShopItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Возвращает матчер, с помощью которого можно проверить эталон товара и предмет,
     * который хочет купить или продать игрок.
     *
     * @return Матчер, с помощью которого проверяем.
     */
    public ShopItemMatcher getMatcher() {
        return matcher;
    }

}
