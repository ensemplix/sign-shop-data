package ru.ensemplix.shop.list;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Возвращает список предметов на основе креативного меню.
 */
public class CreativeTabItemList implements ItemList {

    @Override
    public List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();

        for(CreativeTabs creativeTab : CreativeTabs.creativeTabArray) {
            creativeTab.displayAllReleventItems(items);
        }

        return items;
    }

}
