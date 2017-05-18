package ru.ensemplix.shop;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import org.apache.logging.log4j.Logger;
import ru.ensemplix.shop.exporter.JsonShopItemExporter;
import ru.ensemplix.shop.exporter.ShopItemExporter;
import ru.ensemplix.shop.list.CreativeTabItemList;
import ru.ensemplix.shop.list.ItemList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        folder = event.getModConfigurationDirectory().toPath().resolve("export_data");
        logger = event.getModLog();

        try {
            Files.createDirectories(folder);
        } catch (IOException e) {
            logger.warn("Failed create export data folder");
        }
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        logger.info("Exporting items");

        try {
            exportItems();
        } catch(Exception e) {
            logger.warn("Failed to export items", e);
        }
    }

    private void exportItems() throws IOException {
        // Список всех предметов по модам.
        Map<String, List<ShopItem>> itemsByModId = new HashMap<>();
        // Все имена которые были.
        List<String> names = new ArrayList<>();

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

            // Если в название предмета больше 5 пробелов, то пропускаем его.
            if(displayName.split(" ").length >= 5) {
                logger.info("Item " + displayName + " has too many spaces");
                continue;
            }

            String name = ShopItemNameConverter.convert(displayName);

            // Информируем о наличие дубликатов.
            if(names.contains(name)) {
                logger.info("Item " + name + " (" + displayName + ") already exists");
            }

            String id = Item.itemRegistry.getNameForObject(item.getItem());
            String modId = id.split(":")[0].replaceAll("[^a-zA-Zа-яА-Я0-9]", "");
            int data = item.getMetadata();
            byte[] state = null;

            if(item.hasTagCompound()) {
                state = CompressedStreamTools.compress(item.getTagCompound());
            }

            ShopItem shopItem = new ShopItem(name, new ShopItemStack(id, data, state), null);
            List<ShopItem> shopItems = itemsByModId.computeIfAbsent(modId, k -> new ArrayList<>());
            shopItems.add(shopItem);
            names.add(name);
        }

        // Экспортируем предметы в файлы.
        ShopItemExporter exporter = new JsonShopItemExporter();

        for(String modId : itemsByModId.keySet()) {
            exporter.exportToFile(itemsByModId.get(modId), folder.resolve(modId + ".json"));
        }

        logger.info("Successfully finished with " + names.size() + " items");
    }

}
