package ru.ensemplix.shop;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.ensemplix.shop.exporter.JsonShopItemExporter;
import ru.ensemplix.shop.exporter.ShopItemExporter;
import ru.ensemplix.shop.list.CreativeTabItemList;
import ru.ensemplix.shop.list.ItemList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mod(modid = "signshopdataexporter", name = "ShopDataExporter", version = "1.1", acceptedMinecraftVersions = "[1.12]", acceptableRemoteVersions = "*")
public class ShopItemExporterMod {
    // Фильтр, по которому мы убираем предметов с одинаковыми именами и свойствами.
    static final Predicate<ItemStack> FILTER_SAME_ITEMSTACKS = new Predicate<ItemStack>() {
        private final List<ItemStack> seen = new ArrayList<>();

        @Override
        public boolean test(ItemStack stack) {
            for(ItemStack other : seen) {
                if(ItemStack.areItemStacksEqual(stack, other)) {
                    return false;
                }
            }

            seen.add(stack);
            return true;
        }
    };

    private ShopItemExporterLogger logger;
    private Path folder;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        folder = event.getModConfigurationDirectory().toPath().resolve("export_data");
        logger = new ShopItemExporterLogger(event.getModLog());

        try {
            Files.createDirectories(folder);
        } catch(IOException e) {
            logger.warn("Failed create export data folder", e);
        }
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        logger.info("Exporting items");

        try {
            exportItems();
        } catch(Throwable t) {
            logger.warn("Failed to export items", t);
        }

        // Сохраняем лог экспорта в файл.
        logger.saveToFile(new File(folder.toFile(), "export-log.txt"));
    }

    private void exportItems() throws IOException {
        // Список всех предметов по модам.
        Map<String, List<ShopItem>> itemsByModId = new HashMap<>();
        // Все имена которые были.
        List<String> names = new ArrayList<>();

        // Получаем список всех игровых предметов.
        ItemList itemList = new CreativeTabItemList();
        List<ItemStack> items = itemList.getItems().stream()
                .filter(FILTER_SAME_ITEMSTACKS)
                .collect(Collectors.toList());

        logger.info("Found " + items.size() + " items");

        for(ItemStack item : items) {
            String displayName = item.getDisplayName();

            // Предмет не имеет перевода.
            if(displayName.contains(".")) {
                logger.info("Item " + displayName + " does not have display name");
                continue;
            }

            // Если в название предмета больше 5 пробелов, то пропускаем его.
            if(displayName.split(" ").length > 5) {
                logger.info("Item " + displayName + " has too many spaces");
                continue;
            }

            String name = ShopItemNameConverter.convert(displayName);

            // Информируем о наличие дубликатов.
            if(names.contains(name)) {
                logger.info("Item " + name + " (" + displayName + ") already exists");
            }

            String id = Item.REGISTRY.getNameForObject(item.getItem()).toString();
            String modId = id.split(":")[0].replaceAll("[^a-zA-Zа-яА-Я0-9]", "");
            int data = item.getMetadata();
            byte[] state = tagToByteArray(item);

            ShopItem shopItem = new ShopItem(name, new ShopItemStack(id, data, state));
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

    private static byte[] tagToByteArray(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();

        if(tagCompound != null) {
            try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                CompressedStreamTools.writeCompressed(tagCompound, out);
                return out.toByteArray();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

}
