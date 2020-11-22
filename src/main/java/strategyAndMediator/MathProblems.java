package strategyAndMediator;

public class MathProblems implements Sender{
    private int a;
    private int b;
    private char c;
    private Mediator mediator;

    public MathProblems(Mediator mediator) {
        this.mediator = mediator;
    }

    public void setMathProblems(int a, int b, char c) {
        this.a = a;
        this.b = b;
        this.c = c;
        mediator.doAnswer(this);
    }


    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public char getC() {
        return c;
    }
}
