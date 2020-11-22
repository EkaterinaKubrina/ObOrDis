package strategyAndMediator;

public class MultiplicationStrategy implements Strategy {
    @Override
    public int function(int a, int b) {
        return a*b;
    }
}
