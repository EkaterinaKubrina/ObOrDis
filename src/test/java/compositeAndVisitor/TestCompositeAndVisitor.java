package compositeAndVisitor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCompositeAndVisitor {

    @Test
    public void testComposite(){
        Pack chocolateCandy = new PacketOfCandy("Шоколадные конфеты Аленка", 200.0);
        Pack marmaladeCandy = new PacketOfCandy("Мармеладные конфеты", 150.0);
        Pack miniChocolate= new PacketOfMiniChocolate("Маленькие шоколадки", 100.0);

        Pack box1 = new Box(new Pack[]{marmaladeCandy});
        // коробка 10 + 150 = 160 гр

        Pack box2 = new Box(new Pack[]{chocolateCandy, miniChocolate});
        // коробка 10 + 200 + 100 = 310 гр

        Pack bigBox = new Box(new Pack[] {box1, box2, miniChocolate});
        //коробка 10 + коробки (310 + 160) + 100 = 580

        assertEquals(580.0, bigBox.getWeight(), 1E-6 );
        //не обходим все коробки чтобы узнать общий вес, а берем вес только самой большой
    }

    @Test
    public void testVisitor(){
        Pack chocolateCandy = new PacketOfCandy("Шоколадные конфеты Аленка", 200.0);
        Pack miniChocolate= new PacketOfMiniChocolate("Маленькие шоколадки", 100.0);

        Pack box = new Box(new Pack[]{ chocolateCandy,  miniChocolate});
        // 10 + 200 + 100 = 310
        assertEquals(310.0, box.getWeight(), 1E-6 );


        IVisitor visitor = new IncreaseVisitor();


        Pack[] packs = {box, chocolateCandy, miniChocolate};

        for(Pack p : packs){
            p.accept(visitor, 2);
        }
        //увеличивает вес коробки и её содержимого в два раза, и еще отдельно вес каждого элемента
        //(10 + (200 + 100) *2 ) * 2 = 1220
        assertEquals(1220.0, box.getWeight(), 1E-6 );

    }
}
