package ru.ensemplix.shop;

public class ShopItemNameConverter {

    /**
     * Конвертирует имя предмета для использования на табличке.
     *
     * Примеры:
     *  - "Белая шерсть" => "БЕЛАЯ_ШЕРСТЬ"
     *  - "Термальная центрифуга" => "ТЕРМАЛЬ_ЦЕНТРИФУ"
     *
     * @param name Имя предмета, которое мы конвертируем.
     * @return Преобразованное имя предмета.
     */
    public static String convert(String name) {
        // Заменяет все символы, кроме букв, на пробелы.
        name = name.replaceAll("[^a-zA-Zа-яА-Я0-9]", " ");
        // Убирает лишние пробелы между словами.
        name = name.replaceAll("\\s+"," ");
        // Вместо пробелов ставит нижнее подчеркивание.
        name = name.replaceAll(" ", "_");
        // Делает все буквы верхним регистром.
        name = name.toUpperCase();
        // Сокращает слова, чтобы влезть в ограничение 16 символов.
        // TODO: Добавить сокрашение слов.
        return name;
    }

}