package bridgeAndDecoratorAndAdapter;

public class Tubule extends Cup{
    private static int priceTubule = 5;

    public Tubule(Order milkshake) {
        super(milkshake);
    }

    public int getPrice() {
        return super.getPrice()+priceTubule;
    }

}
