package strategyAndMediator;

public class Answer implements Sender{
    private int answer;
    private Mediator mediator;

    public Answer(Mediator mediator) {
        this.mediator = mediator;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
        mediator.doAnswer(this);
    }
}
