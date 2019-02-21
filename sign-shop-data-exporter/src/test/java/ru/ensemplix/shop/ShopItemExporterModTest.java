package ru.ensemplix.shop;

import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.init.Items.STONE_AXE;
import static org.junit.Assert.assertEquals;

public class ShopItemExporterModTest {

    @Before
    public void setUp() {
        Bootstrap.register();
    }

    @Test
    public void testFilterSameItemStacks() {
        NonNullList<ItemStack> beforeItems = NonNullList.create();
        ItemGroup.BREWING.fill(beforeItems);
        beforeItems.add(new ItemStack(STONE_AXE));
        beforeItems.add(new ItemStack(STONE_AXE));

        List<ItemStack> afterItems = beforeItems.stream()
                .filter(ShopItemExporterMod.FILTER_SAME_ITEM_STACKS)
                .collect(Collectors.toList());

        assertEquals(beforeItems.size() - 1, afterItems.size());
    }

}
