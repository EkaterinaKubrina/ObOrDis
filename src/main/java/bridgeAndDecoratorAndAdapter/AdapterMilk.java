package bridgeAndDecoratorAndAdapter;

public class AdapterMilk extends LactoseFreeMilk {
    private CowsMilk cowsMilk;
    private int percentLactose; //процент лактозы в молоке

    public AdapterMilk(CowsMilk cowsMilk) {
        this.cowsMilk = cowsMilk;
        this.percentLactose = cowsMilk.getPercentLactose();
        enzymaticBreakdownOfLactose(); //ферментированное расщепление лактозы
    }

    public void enzymaticBreakdownOfLactose(){
        percentLactose = 1;
    } //процент лактозы становится допустимым для аллергиков

    @Override
    public String getNameMilk() {
        return "коровье молоко без лактозы";
    }

    @Override
    public void warmingMilk() {
    cowsMilk.warmingMilk();
    }

    @Override
    public int getPercentLactose() {
        return percentLactose;
    }
}
