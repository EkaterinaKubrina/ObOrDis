package bridgeAndDecoratorAndAdapter;

public class CowsMilk implements Milk {
    private boolean warm;

    public CowsMilk() {
        this.warm = false;
    }

    @Override
    public int getPercentLactose() { //возращает процент лактозы в молоке
        return 100;
    }

    @Override
    public String getNameMilk() {
        return "коровье молоко";
    }

    @Override
    public void warmingMilk() {
        warm = true;
    }
}
