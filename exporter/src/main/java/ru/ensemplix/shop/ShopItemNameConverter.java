package ru.ensemplix.shop;

public class ShopItemNameConverter {

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
     *  - "Термальная центрифуга" => "ТЕРМАЛЬ_ЦЕНТРИФУ"
     *  - "Изолированный высоковольтный провод" => "ИЗОЛ_ВЫСОК_ПРОВО"
     *
     * @param name Имя предмета, которое мы конвертируем.
     * @return Преобразованное имя предмета.
     */
    public static String convert(String name) {
        name = name
                .replaceAll("[^a-zA-Zа-яА-Я0-9]", " ")
                .replaceAll("\\s+"," ")
                .replaceAll(" ", "_")
                .toUpperCase();

        // Убирает по букве у самого длинного слова в процентном соотношении.
        while(name.length() > MAX_SIGN_LINE_TEXT_WIDTH) {
            String[] lines = name.split("_");
            String maxLine = lines[0];
            float maxPercent = 0;

            for(String line : lines) {
                float percent = line.length() / 100F;

                if(percent > maxPercent) {
                    maxLine = line;
                    maxPercent = percent;
                }
            }

            name = name.replaceAll(maxLine, maxLine.substring(0, maxLine.length() - 1));
        }


        return name;
    }

}
