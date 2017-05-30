package ru.ensemplix.shop.importer;

import ru.ensemplix.shop.ShopItem;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipShopItemImporter implements ShopItemImporter {

    private final ShopItemImporter importer;
    private final String subFolder;

    public ZipShopItemImporter(ShopItemImporter importer) {
        this(importer, null);
    }

    public ZipShopItemImporter(ShopItemImporter importer, String subFolder) {
        this.importer = importer;
        this.subFolder = subFolder;
    }

    @Override
    public List<ShopItem> importFromFile(Path path) {
        List<ShopItem> items = new ArrayList<>();

        try(ZipFile zip = new ZipFile(path.toFile())) {
            Enumeration<? extends ZipEntry> entries = zip.entries();

            while(entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if(entry.isDirectory()) {
                    continue;
                }

                if(subFolder != null && !entry.getName().startsWith(subFolder)) {
                    continue;
                }

                URI uri = new URL("jar:" + path.toUri() + "!/" + entry.getName()).toURI();

                try(FileSystem fs = createFileSystem(uri)) {
                    items.addAll(importer.importFromFile(Paths.get(uri)));
                }
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        return items;
    }

    private FileSystem createFileSystem(URI uri) throws IOException {
        try {
            return FileSystems.getFileSystem(uri);
        } catch(FileSystemNotFoundException e) {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");

            return FileSystems.newFileSystem(uri, env);
        }
    }

}
