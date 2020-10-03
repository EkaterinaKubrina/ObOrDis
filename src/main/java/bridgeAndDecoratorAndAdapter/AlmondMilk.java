package bridgeAndDecoratorAndAdapter;

public class AlmondMilk extends LactoseFreeMilk  {
    private boolean warm;
    private int percentLactose = 0;

    public AlmondMilk() {
        this.warm = false;
    }

    @Override
    public String getNameMilk() {
        return "миндальное молоко";
    }

    @Override
    public void warmingMilk() {
        warm = true;
    }

    @Override
    public int getPercentLactose() {
        return percentLactose;
    }
}
