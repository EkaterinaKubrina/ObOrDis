package singletonAndPrototype;

import builder.Clothes;

public interface IPrototype {
    IPrototype clone();
    int getPrice();
    String getType();
    Clothes getClothes();
}
