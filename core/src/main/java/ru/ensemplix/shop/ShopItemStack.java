package ru.ensemplix.shop;

/**
 * Представляет информацию о предмете.
 */
public class ShopItemStack {

    private final String id;
    private final int data;

    public ShopItemStack(String id, int data) {
        this.id = id;
        this.data = data;
    }

    /**
      * Возвращает идентификатор предмета.
      *
      * @return Идентификатор предмета.
      */
     public String getId() {
          return id;
     }

     /**
      * Возвращает дополнительную информация о предмете.
      *
      * Используется в качестве указателя прочности для оружия и брони.
      * Используется в качестве указателя цвета шерсти, глины и стекла.
      *
      * @return Дополнительная информация о предмете.
      */
     public int getData() {
         return data;
     }

}
