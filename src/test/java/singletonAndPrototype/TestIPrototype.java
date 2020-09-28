package singletonAndPrototype;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestIPrototype {
    @Test
    public void test()  {
        Shop shop = new Shop("Виталий Витальевич");
        Goods goods1 = new Goods("dress", 1000);
        Goods goods2 = new Goods("blouse", 500);
        shop.setGoods(new IPrototype[]{goods1.clone(), goods2.clone()});

        assertEquals(goods1.getPrice(), shop.goods[0].getPrice());
        assertEquals(goods1.getType(), shop.goods[0].getType());
        assertEquals(goods1.getClothes().getColor(), shop.goods[0].getClothes().getColor());

        assertEquals(goods2.getPrice(), shop.goods[1].getPrice());
        assertEquals(goods2.getType(), shop.goods[1].getType());
        assertEquals(goods2.getClothes().getColor(), shop.goods[1].getClothes().getColor());
    }
}
