package templateMethodAndObserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTemplateMethodAndObserver {

    @Test
    public void test(){
        PurchaseInAStore purchaseInAStore = new PurchaseInAStore();
        String actual = purchaseInAStore.cashier("три морковки, четыре лука",
                44.0,
                true,
                "наличными");
        //Это покупка, совершаемая в магазине, поэтому кассир получает все данные о цене и товарах
        assertEquals("Товары: три морковки, четыре лука\n" +
                        "Сумма заказа: 41.8\n" +
                        "Оплачено 41.8 руб.\n" +
                        "Заказ был оплачен наличными и выдан покупателю",
                actual);

        PurchaseOnline purchaseOnline = new PurchaseOnline("вишневый сок, чипсы",
                100,
                "онлайн");
        String actual1 = purchaseOnline.cashier("",
                0,
                false,
                "");
        //Это покупка, совершенная онлайн, поэтому кассир не получает никаких данных о товаре и цене
        assertEquals("Товары: вишневый сок, чипсы\n" +
                        "Сумма заказа: 100.0\n" +
                        "Заказ, оформленный на сайте, оплачен онлайн и выдан покупателю",
                actual1);
    }


    @Test
    public void testObserver() {
        PurchaseInAStore purchaseInAStore = new PurchaseInAStore();
        purchaseInAStore.addSubscriber(new Warehouse());
        purchaseInAStore.addSubscriber(new Loader());   //подписчики (склад и грузчик) работают только после оформления заказа
        // и если список продуктов не пуст
        String actual = purchaseInAStore.cashier("три морковки, четыре лука",
                44.0,
                true,
                "наличными");
        assertEquals("Склад выдал грузчику три морковки, четыре лука\n" +
                        "Грузчик погрузил и доставил к стойке выдачи заказов три морковки, четыре лука\n" +
                        "Товары: три морковки, четыре лука\n" +
                        "Сумма заказа: 41.8\n" +
                        "Оплачено 41.8 руб.\n" +
                        "Заказ был оплачен наличными и выдан покупателю",
                actual);

        String actual1 = purchaseInAStore.cashier("",
                0,
                false,
                "");
        assertEquals("Склад отдыхает\n" +
                        "Грузчик отдыхает\n" +
                        "Заказ не был собран",
                actual1);
    }
}
