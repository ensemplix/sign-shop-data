package ru.ensemplix.shop;

import com.google.common.base.Throwables;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Специальный логер для сохранения результатов экспорта в отдельный файл.
 */
public class ShopItemExporterLogger {
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final List<String> exportLog = new ArrayList<>();
    private final Logger logger;

    ShopItemExporterLogger(Logger logger) {
        this.logger = logger;
    }

    void info(String message) {
        String currentTime = dateFormat.format(new Date());
        exportLog.add("["+ currentTime + "][INFO] " + message);
        logger.info(message);
    }

    void warn(String message, Throwable t) {
        String currentTime = dateFormat.format(new Date());
        exportLog.add("["+ currentTime + "][WARN] " + message);
        exportLog.add(Throwables.getStackTraceAsString(t));
        logger.warn(message, t);
    }

    void saveToFile(File file) {
        if(file.exists()) {
            file.delete();
        }

        try(FileWriter writer = new FileWriter(file)) {
            for(String log : exportLog) {
                writer.write(log + "\n");
            }
        } catch(IOException e) {
            logger.warn("Failed to save export log", e);
        }
    }
}
