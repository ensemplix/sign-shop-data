package ru.ensemplix.shop;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ru.ensemplix.shop.list.CreativeTabItemList;
import ru.ensemplix.shop.list.ItemList;


@Mod(modid = "signshopdataexporter", name = "ShopDataExporter", version = "1.0", acceptedMinecraftVersions = "[1.7.10]", acceptableRemoteVersions = "*")
public class ShopItemExporterMod {

    private Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        logger.info("Exporting items");

        // Получаем список всех игровых предметов.
        ItemList itemList = new CreativeTabItemList();
    }

}
