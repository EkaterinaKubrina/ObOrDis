package bridgeAndDecoratorAndAdapter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBridgeAndDecoratorAndAdapter {

    private CowsMilk cowsMilk;

    @Test
    public void testBridge() {
        CowsMilk cowsMilk = new CowsMilk();
        Milkshake milkshake = new Milkshake(cowsMilk);
        assertEquals("коровье молоко", milkshake.getMilk().getNameMilk());

        AlmondMilk almondMilk = new AlmondMilk();
        Milkshake milkshake1 = new Milkshake(almondMilk);
        assertEquals("миндальное молоко", milkshake1.getMilk().getNameMilk());
    }

    @Test
    public void testAdapter() {
        CowsMilk cowsMilk = new CowsMilk();

        try{
            Milkshake milkshake = new Milkshake(cowsMilk);
            ClientWithALactoseAllergy clientWithALactoseAllergy = new ClientWithALactoseAllergy(milkshake);}
        catch (IllegalArgumentException ex){
            assertEquals("Аллергия!", ex.getMessage());
        } //исключение, потому что молочный коктейль содержит лактозу


        AdapterMilk adapterMilk = new AdapterMilk(cowsMilk); //адаптируем молоко
        Milkshake milkshake = new Milkshake(adapterMilk);
        ClientWithALactoseAllergy clientWithALactoseAllergy = new ClientWithALactoseAllergy(milkshake); //нет исключения
        assertEquals("коровье молоко без лактозы", clientWithALactoseAllergy.getMilkshake().getMilk().getNameMilk());

        CoconutMilk coconutMilk = new CoconutMilk(); //молоко без лактозы
        Milkshake milkshake1 = new Milkshake(coconutMilk);
        ClientWithALactoseAllergy clientWithALactoseAllergy1 = new ClientWithALactoseAllergy(milkshake1); //нет исключения
        assertEquals("кокосовое молоко", clientWithALactoseAllergy1.getMilkshake().getMilk().getNameMilk());
    }

    @Test
    public void testDecorator() {
        CoconutMilk coconutMilk = new CoconutMilk();
        Order milkshake = new Milkshake(coconutMilk);
        assertEquals(99, milkshake.getPrice()); //цена молочного коктейля

        milkshake = new Tubule(milkshake); //добавляем трубочку
        milkshake = new DrawingOnTheCup(milkshake, "Солнышко"); //добавляем рисунок на стаканчике
        assertEquals(109, milkshake.getPrice()); //цена с трубочкой и рисунком
    }


}
