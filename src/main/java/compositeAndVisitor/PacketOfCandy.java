package compositeAndVisitor;

public class PacketOfCandy implements Pack{
    private String name;
    private double weight;

    public PacketOfCandy(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void accept(IVisitor visitor, double times) {
        visitor.visit(this, times);
    }
}

