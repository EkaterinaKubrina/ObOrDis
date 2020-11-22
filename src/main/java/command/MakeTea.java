package command;

public class MakeTea implements Command {
    protected Barista barista;

    public MakeTea(Barista barista) {
        this.barista = barista;
    }

    @Override
    public Drink execute() {
        return barista.makeTea();
    }
}
