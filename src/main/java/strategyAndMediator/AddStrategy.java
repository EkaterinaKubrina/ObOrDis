package strategyAndMediator;

public class AddStrategy implements Strategy {
    @Override
    public int function(int a, int b) {
        return a+b;
    }
}
