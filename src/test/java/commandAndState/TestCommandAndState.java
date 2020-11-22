package commandAndState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCommandAndState {

    @Test
    public void test(){
        Barista barista = new Barista();
        MakeCoffee makeCoffee = new MakeCoffee(barista);
        MakeTea makeTea = new MakeTea(barista);

        Drink drink = SellerCashier.submitAnOrder(makeTea); //подаем кассиру команду сделать чай
        assertEquals("Чай согревает", drink.drinking()); //бариста делает нам горячий чай
        drink.setState(new Cold()); //остужаем чай
        assertEquals("Чай охлаждает", drink.drinking()); //теперь чай холодный и он остужает


        Drink drink1 = SellerCashier.submitAnOrder(makeCoffee); //теперь просим сделать кофе
        assertEquals("Кофе согревает", drink1.drinking()); //бариста готовит горячий кофе
        drink1.setState(new Cold()); //он остывает
        assertEquals("Кофе охлаждает", drink1.drinking()); //кофе холодный
    }

}
