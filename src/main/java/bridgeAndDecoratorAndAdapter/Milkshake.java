package bridgeAndDecoratorAndAdapter;

public class Milkshake implements Order {
    private Milk milk;
    private int price;

    public Milkshake(Milk milk) {
        this.milk = milk;
        this.price = 99;
    }

    public Milk getMilk() {
        return milk;
    }

    public void setMilk(Milk milk) {
        this.milk = milk;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price += price;
    }
}
