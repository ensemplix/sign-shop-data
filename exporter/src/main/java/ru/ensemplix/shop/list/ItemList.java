package ru.ensemplix.shop.list;

import net.minecraft.item.ItemStack;
import java.util.List;

/**
 * Реализация данного интерфейса позволяет использовать разные
 * способы получения списка предметов.
 */
public interface ItemList {

    /**
     * Возвращает список предметов, на основе котором будет производиться экспорт.
     *
     * @return Список предметов.
     */
    List<ItemStack> getItems();

}
