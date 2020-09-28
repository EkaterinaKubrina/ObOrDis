package singletonAndPrototype;


import builder.Clothes;
import builder.Director;

public class Goods implements IPrototype {
    private int price;
    private String type;
    private Clothes clothes;

    public Goods(String type, int price) {
        this.price = price;
        this.type = type;
        Director director1 = new Director(type);
        this.clothes = director1.buildClothes();
    }

    public Goods(Goods goods)  {
        super();
        this.price = goods.price;
        this.type = goods.type;
        this.clothes = goods.clothes;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public IPrototype clone() {
        return new Goods(this);
    }
}
