package ru.ensemplix.shop;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;
import ru.ensemplix.shop.export.JsonShopItemExporter;
import ru.ensemplix.shop.export.ShopItemExporter;
import ru.ensemplix.shop.list.CreativeTabItemList;
import ru.ensemplix.shop.list.ItemList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mod(modid = "signshopdataexporter", name = "ShopDataExporter", version = "1.0", acceptedMinecraftVersions = "[1.7.10]", acceptableRemoteVersions = "*")
public class ShopItemExporterMod {

    private Logger logger;
    private Path folder;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        folder = event.getModConfigurationDirectory().toPath();
        logger = event.getModLog();
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        logger.info("Exporting items");

        // Список всех предметов по модам.
        Map<String, List<ShopItem>> itemsByModId = new HashMap<>();

        // Получаем список всех игровых предметов.
        ItemList itemList = new CreativeTabItemList();
        List<ItemStack> items = itemList.getItems();

        for(ItemStack item : items) {
            String displayName = item.getDisplayName();

            // Предмет не имеет перевода.
            if(displayName.contains(".")) {
                logger.info("Item " + displayName + " does not have display name");
                continue;
            }

            String name = ShopItemNameConverter.convert(displayName);
            String id = Item.itemRegistry.getNameForObject(item);
            String modId = id.split(":")[0];
            int data = item.getMetadata();

            ShopItem shopItem = new ShopItem(name, new ShopItemStack(id, data), null);
            List<ShopItem> shopItems = itemsByModId.computeIfAbsent(modId, k -> new ArrayList<>());
            shopItems.add(shopItem);
        }

        // Экспортируем предметы в файлы.
        ShopItemExporter exporter = new JsonShopItemExporter();

        for(String modId : itemsByModId.keySet()) {
            exporter.export(itemsByModId.get(modId), Paths.get(modId + ".json"));
        }
    }

}
