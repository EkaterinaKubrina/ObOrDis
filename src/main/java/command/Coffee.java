package command;

public class Coffee implements Drink{
    State state;

    public Coffee(State state) {
        this.state = state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    public String drinking(){
        return "Кофе " + state.drink();
    }
}
