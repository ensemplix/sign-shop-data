package ru.ensemplix.shop.matcher;

import ru.ensemplix.shop.ShopItemStack;

/**
 * Позволяет создавать различные реализации матчера, по которым будем сраванивать предметы.
 */
public interface ShopItemMatcher {

    /**
     * Проверяет, что эталон товара совпадает с предметом.
     *
     * @param other Предмет, который сраваниваем с эталоном.
     * @return {@code true}, если эталон товара совпадает с предметом.
     */
    boolean match(ShopItemStack other);

}
