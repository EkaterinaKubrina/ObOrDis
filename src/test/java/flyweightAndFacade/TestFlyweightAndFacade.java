package flyweightAndFacade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFlyweightAndFacade {
    @Test
    public void test() {
        Road road = Facade.createRoad(3, "Новая дорога"); //обращаемся через фасад, чтобы создать дорогу по умолчанию
        assertEquals("Новая дорога," +
                " на 1км. дороги c левой стороны зеленый 5-этажный дом," +
                " на 1км. дороги c левой стороны синий 5-этажный дом," +
                " на 2км. дороги c правой стороны желтый 5-этажный дом," +
                " на 1км. дороги с правой стороны предписывающий 2-метровый дорожный знак," +
                " на 2км. дороги с левой стороны предупреждающий 2-метровый дорожный знак," +
                " на 3км. дороги с правой стороны запрещающий движение 2-метровый дорожный знак",
                road.getDescription());

    }
}
