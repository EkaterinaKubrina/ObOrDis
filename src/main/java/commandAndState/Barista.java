package commandAndState;

public class Barista {

    protected Drink makeCoffee() {
        return new Coffee(new Hot());
    }

    protected Drink makeTea(){
        return new Tea(new Hot());
    }
}
