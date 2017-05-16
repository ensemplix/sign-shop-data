package ru.ensemplix.shop.list;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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

        for(Object obj : Item.itemRegistry) {
            Item item = (Item) obj;

            for(CreativeTabs creativeTab : item.getCreativeTabs()) {
                item.getSubItems(item, creativeTab, items);
            }
        }

        for(Object obj : Block.blockRegistry) {
            Block block = (Block) obj;
            Item item = Item.getItemFromBlock(block);

            // Не все блоки имеют предмет.
            if(item == null) {
                continue;
            }

            for(CreativeTabs creativeTab : item.getCreativeTabs()) {
                block.getSubBlocks(item, creativeTab, items);
            }
        }

        return items;
    }

}
