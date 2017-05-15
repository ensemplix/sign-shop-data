package ru.ensemplix.shop.matcher;

import ru.ensemplix.shop.ShopItemStack;

import static ru.ensemplix.shop.matcher.SimpleShopItemMatcher.Capability.*;

/**
 * Простой матчер, которого должно хватить в большинстве случаев.
 */
public class SimpleShopItemMatcher implements ShopItemMatcher {

    private final ShopItemStack itemStack;
    private final Capability[] capabilities;

    public SimpleShopItemMatcher(ShopItemStack itemStack, Capability... capabilities) {
        this.itemStack = itemStack;
        this.capabilities = capabilities;
    }

    @Override
    public boolean match(ShopItemStack other) {
        if(!itemStack.getId().equals(other.getId())) {
            return false;
        }

        if(!hasCapability(IGNORE_DATA) && itemStack.getData() != other.getData()) {
            return false;
        }

        return true;
    }

    private boolean hasCapability(Capability other) {
        for(Capability capability : capabilities) {
            if(capability == other) {
                return true;
            }
        }

        return false;
    }

    /**
     * Представляет специальные возможности для игнорирования части проверок.
     */
    public enum Capability {
        // Не проверяет дополнитльную информацию предмета.
        IGNORE_DATA,
        // Не проверяет состояние предмета.
        IGNORE_STATE
    }

}
