package bridgeAndDecoratorAndAdapter;

public class Cup implements Order{
    protected Order milkshake;

    public Cup(Order milkshake) {
        this.milkshake = milkshake;
    }

    @Override
    public int getPrice() {
        return milkshake.getPrice();
    }


}
