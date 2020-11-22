package strategyAndMediator;

import static org.junit.Assert.assertEquals;

public class TestStrategyAndMediator {
    @org.junit.Test
    public void test(){
        DoMathProblem doMathProblem = new DoMathProblem(); //создаем посредника
        MathProblems mathProblems = new MathProblems(doMathProblem); //создаем классы использующие его интерфейс
        Calculation calculation = new Calculation(doMathProblem);
        Answer answer = new Answer(doMathProblem);
        doMathProblem.setMathProblem(mathProblems); //кладем эти классы в посредника
        doMathProblem.setCalculation(calculation);
        doMathProblem.setAnswer(answer);

        mathProblems.setMathProblems(5, 6, '+'); //запускаем посредника, добавляя математический пример
        assertEquals(11, answer.getAnswer()); //посредник сам обращается к классам Вычисления и Ответа и заполняет их



        calculation.setStrategy(new MultiplicationStrategy()); //можно изменить класс Вычисления самостоятельно,
                                                               // с помощью паттерна Стратегия
        assertEquals(30, answer.getAnswer()); //посредник решит пример и задаст новый ответ в классе Ответа


    }
}
