package ru.ensemplix.shop.list;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

/**
 * Возвращает список предметов на основе креативного меню.
 */
public class CreativeTabItemList implements ItemList {

    @Override
    public List<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.create();

        for(CreativeTabs creativeTab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            creativeTab.displayAllRelevantItems(items);
        }

        return items;
    }

}
