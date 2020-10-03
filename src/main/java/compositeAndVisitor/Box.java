package compositeAndVisitor;

public class Box implements Pack{
    private Pack[] packs;
    private double weightBox = 10.0;

    public Box(Pack[] packs) {
        this.packs = packs;
    }

    public double getWeight(){
        double weight = weightBox;
        if(packs.length>0){
            for(Pack pack : packs){
                weight += pack.getWeight();
            }
        }
        return weight;
    }

    @Override
    public void setWeight(double weight) {
        this.weightBox = weight;
    }

    @Override
    public void accept(IVisitor visitor, double times) {
        visitor.visit(this, times);
    }

    public double getWeightBox() {
        return weightBox;
    }

    public Pack[] getPacks() {
        return packs;
    }

    public Pack getPack( int i) {
        return packs[i];
    }
}
