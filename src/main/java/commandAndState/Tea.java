package commandAndState;

public class Tea implements Drink{
    State state;

    public Tea(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String drinking(){
        return "Чай " + state.drink();
    }
}
