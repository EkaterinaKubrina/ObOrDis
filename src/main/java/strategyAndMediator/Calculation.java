package strategyAndMediator;

public class Calculation implements Sender{
    private Strategy strategy;
    private Mediator mediator;

    public Calculation(Mediator mediator) {
        this.mediator = mediator;
    }

    public Calculation(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
        mediator.doAnswer(this);
    }

    public int calc(int a, int b){
        return strategy.function(a, b);
    }
}
