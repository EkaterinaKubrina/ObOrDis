package builder;

import base.FindFirstNegativeElem;
import base.FindMaxElem;
import base.FindMinElem;

public class Director {
    private Builder builder;

    public Director(String str) {
        switch (str) {
            case "dress":
                builder = new DressBuilder();
                break;
            case "coat":
                builder = new CoatBuilder();
                break;
            case "jeans":
                builder = new JeansBuilder();
                break;
            case "blouse":
                builder = new BlouseBuilder();
                break;
        }
    }


    public Clothes buildClothes(){
        return builder.buildClothes();
    }
}
