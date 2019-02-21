package ru.ensemplix.shop.list;

import net.minecraft.item.ItemGroup;
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

        for(ItemGroup group : ItemGroup.GROUPS) {
            if(group == ItemGroup.HOTBAR) {
                continue;
            }

            group.fill(items);
        }

        return items;
    }

}
