package IteratorAndProxy;


import flyweightAndFacade.Facade;
import flyweightAndFacade.Road;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIteratorAndProxy {


    @Test
    public void test() {
        ListOfGoods listOfGoods = new ListOfGoods();
        IProxy proxyList = new ProxyListOfGoods(listOfGoods);

        proxyList.addGood("Карандаш простой   ");  //через заместителя добавляем товары
        assertEquals("карандаш простой", proxyList.getGood(0));  //через заместителя смотрим товары


        proxyList.addGood("   ручка синяя гелевая   ");
        assertEquals("ручка синяя гелевая", proxyList.getGood(1));


        proxyList.addGood("   ТеТраДЬ в ЛИНЕЙКУ");
        assertEquals("тетрадь в линейку", proxyList.getGood(2));


        proxyList.setGood("тетрадь в клетку", 2);   //через заместителя заменяем товары
        assertEquals("тетрадь в клетку", proxyList.getGood(2));

        Iterator iterator = listOfGoods.createIterator(); //создаем итератор
        assertEquals("1. карандаш простой", iterator.getGood());  //когда смотрим товары не через заместителя, они выглядят иначе
        iterator.next();
        assertEquals("2. ручка синяя гелевая", iterator.getGood());


        Iterator iterator1 = listOfGoods.createIterator(); //создаем другой итератор
        while (iterator1.hasNext()){
            System.out.println(iterator1.getGood());  //видно что он пробегает все элементы сначала, независимо от другого итератора
            iterator1.next();
        }
    }


}
