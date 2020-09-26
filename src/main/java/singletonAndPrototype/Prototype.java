package singletonAndPrototype;

import builder.Clothes;

public interface Prototype {
    Prototype clone();
    int getPrice();
    String getType();
    Clothes getClothes();
}
