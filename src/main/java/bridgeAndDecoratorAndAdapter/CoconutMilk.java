package bridgeAndDecoratorAndAdapter;

public class CoconutMilk extends LactoseFreeMilk {
    private boolean warm;
    private int percentLactose = 0;

    public CoconutMilk() {
        this.warm = false;
    }

    @Override
    public String getNameMilk() {
        return "кокосовое молоко";
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
