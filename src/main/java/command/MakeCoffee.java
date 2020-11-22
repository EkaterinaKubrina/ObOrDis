package command;

public class MakeCoffee implements Command{
    protected Barista barista;

    public MakeCoffee(Barista barista) {
        this.barista = barista;
    }

    @Override
    public Drink execute() {
        return barista.makeCoffee();
    }
}
