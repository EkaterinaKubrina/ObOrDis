package singletonAndPrototype;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestSingleton {
    @Test
    public void test(){
        Shop shop = new Shop("Виталий Витальевич");
        assertEquals("Виталий Витальевич", shop.getShopOwnerName());
        ShopOwner.getInstance("Евгений Евгеньевич"); //меняем владельца
        assertNotEquals("Евгений Евгеньевич", shop.getShopOwnerName());
        assertEquals("Виталий Витальевич", shop.getShopOwnerName()); //владелец не изменился, хоть мы и пытались нанять другого
    }
}
