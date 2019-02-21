package ru.ensemplix.shop;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import org.apache.logging.log4j.LogManager;
import org.dimdev.rift.listener.BootstrapListener;
import org.dimdev.riftloader.RiftLoader;
import org.dimdev.riftloader.listener.InitializationListener;
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

public class ShopItemExporterMod implements BootstrapListener {

    // Фильтр, по которому мы убираем предметов с одинаковыми именами и свойствами.
    static final Predicate<ItemStack> FILTER_SAME_ITEM_STACKS = new Predicate<ItemStack>() {
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


    @Override
    public void afterVanillaBootstrap() {
        ShopItemExporterLogger logger = new ShopItemExporterLogger(LogManager.getLogger());
        Path folder = RiftLoader.instance.configDir.toPath().resolve("exporter");
        ItemList itemList = new CreativeTabItemList();
        ShopItemExporter exporter = new JsonShopItemExporter();

        try {
            Files.createDirectories(folder);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("Exporting items");

        try {
            exportItems(logger, exporter, itemList, folder);
        } catch(Throwable t) {
            logger.warn("Failed to export items", t);
        }

        // Сохраняем лог экспорта в файл.
        logger.saveToFile(new File(folder.toFile(), "export-log.txt"));
    }

    private void exportItems(ShopItemExporterLogger logger, ShopItemExporter exporter, ItemList itemList, Path folder) {
        // Список всех предметов по модам.
        Map<String, List<ShopItem>> itemsByModId = new HashMap<>();
        // Все имена которые были.
        List<String> names = new ArrayList<>();

        // Получаем список всех игровых предметов.
        List<ItemStack> items = itemList.getItems().stream()
                .filter(FILTER_SAME_ITEM_STACKS)
                .collect(Collectors.toList());

        logger.info("Found " + items.size() + " items");

        for(ItemStack item : items) {
            String displayName = item.getDisplayName().getString();

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

            ResourceLocation resource = RegistryNamespaced.ITEM.getKey(item.getItem());

            if(resource == null) {
                logger.info("Item " + displayName + " resource not found");
                continue;
            }

            String id = resource.toString();
            String modId = resource.getNamespace();
            int data = item.getDamage();
            byte[] state = tagToByteArray(item);

            ShopItem shopItem = new ShopItem(name, new ShopItemStack(id, data, state));
            List<ShopItem> shopItems = itemsByModId.computeIfAbsent(modId, k -> new ArrayList<>());
            shopItems.add(shopItem);
            names.add(name);
        }

        for(String modId : itemsByModId.keySet()) {
            exporter.exportToFile(itemsByModId.get(modId), folder.resolve(modId + ".json"));
        }

        logger.info("Successfully finished with " + names.size() + " items");
    }

    private static byte[] tagToByteArray(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTag();

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
