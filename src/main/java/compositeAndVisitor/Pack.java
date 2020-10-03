package compositeAndVisitor;

public interface Pack {
    double getWeight();
    void setWeight(double weight);
    void accept(IVisitor visitor, double times);
}
