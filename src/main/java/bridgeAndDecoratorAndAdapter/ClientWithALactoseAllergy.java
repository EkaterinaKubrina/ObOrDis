package bridgeAndDecoratorAndAdapter;

public class ClientWithALactoseAllergy {
    private Milkshake milkshake;

    public ClientWithALactoseAllergy(Milkshake milkshake) {
        if(milkshake.getMilk().getPercentLactose() < 2){
        this.milkshake = milkshake;}
        else {throw new IllegalArgumentException("Аллергия!");}
    }

    public Milkshake getMilkshake() {
        return milkshake;
    }

    public void setMilkshake(Milkshake milkshake) {
        this.milkshake = milkshake;
    }
}
