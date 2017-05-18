package ru.ensemplix.shop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Вспомогательный класс для конвертации имени предмета.
 */
public class ShopItemNameConverter {

    // Паттерн, по которому мы определяем буквы и цифры.
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("[^a-zA-Zа-яА-ЯёЁ0-9]");

    // Паттерн, по которому мы определяем наличие согласных.
    private static final Pattern CONSONANT_PATTERN = Pattern.compile("[^aeiouyаеёиоуыэюя]");

    // Максимальное количество символов на одной строке таблички.
    private static final int MAX_SIGN_LINE_TEXT_WIDTH = 16;

    /**
     * Конвертирует имя предмета для использования на табличке.
     *
     * 1. Заменяет все символы, кроме букв, на пробелы.
     * 2. Убирает лишние пробелы между словами.
     * 3. Вместо пробелов ставит нижнее подчеркивание.
     * 4. Делает все буквы верхним регистром.
     * 5. Сокращает слова, чтобы влезть в ограничение строки таблички.
     *
     * Примеры:
     *  - "Белая шерсть" => "БЕЛАЯ_ШЕРСТЬ"
     *  - "Термальная центрифуга" => "ТЕРМАЛЬН_ЦЕНТРИФ"
     *  - "Изолированный высоковольтный провод" => "ИЗОЛ_ВЫСОК_ПРОВ"
     *  - "Доски из тропического дерева" => "ДОСКИ_ИЗ_ТР_ДЕР"
     *  - "Жёлтая обожжённая глина" => "ЖЁЛТ_ОБОЖЖ_ГЛИНА"
     *
     * @param name Имя предмета, которое мы конвертируем.
     * @return Преобразованное имя предмета.
     */
    public static String convert(String name) {
        name = ALPHANUMERIC_PATTERN.matcher(name)
                .replaceAll(" ")
                .replaceAll("\\s+"," ")
                .replaceAll(" ", "_")
                .toUpperCase();

        // Убираем до новой согласной у самого длинного слова в процентном соотношении.
        while(name.length() > MAX_SIGN_LINE_TEXT_WIDTH) {
            String[] lines = name.split("_");
            String maxLine = lines[0];
            float maxTotal = 0;
            int maxFirst = 0;

            for(String line : lines) {
                // Определяем ценность одной буквы в процентах.
                float percent = line.length() / 100F;
                // Сколько букв с конца до первой согласной.
                int first = firstConsonant(line);
                // Сколько в итоге мы должныы удалить в процентах.
                float total = percent * first;

                // Если ценность этой буквы в процентах ниже, чем у других слов, то выбираем ее на удаление.
                if(total > maxTotal) {
                    maxLine = line;
                    maxTotal = total;
                    maxFirst = first;
                }
            }

            name = name.replaceAll(maxLine, maxLine.substring(0, maxLine.length() - maxFirst));
        }

        return name;
    }

    // Убираем последнию букву, переварачиваем строку и ищем первое совпадение.
    private static int firstConsonant(String line) {
        line = line.substring(0, line.length() - 1).toLowerCase();
        StringBuilder reverse = new StringBuilder(line).reverse();
        Matcher matcher = CONSONANT_PATTERN.matcher(reverse);

        if(matcher.find()) {
            return matcher.start() + 1;
        }

        return 1;
    }

}
