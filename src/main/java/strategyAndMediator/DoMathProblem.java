package strategyAndMediator;

public class DoMathProblem implements Mediator {
    private MathProblems mathProblem;
    private Calculation calculation;
    private Answer answer;
    private int a;
    private int b;

    public DoMathProblem() {
    }

    public void setMathProblem(MathProblems mathProblem) {
        this.mathProblem = mathProblem;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public void doAnswer(Sender sender) {
        if(sender.getClass()==MathProblems.class){
            a = ((MathProblems) sender).getA();
            b = ((MathProblems) sender).getB();
            if(((MathProblems) sender).getC()=='+'){
                calculation.setStrategy(new AddStrategy());
            }
            else{
                calculation.setStrategy(new MultiplicationStrategy());
            }
        }
        else if(sender.getClass()==Calculation.class){
            answer.setAnswer(calculation.calc(a, b));
        }
        else if(sender.getClass()==Answer.class){
            System.out.println("Проблема решена!");
        }
    }
}
