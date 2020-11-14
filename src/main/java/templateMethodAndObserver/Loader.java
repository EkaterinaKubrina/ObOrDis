package templateMethodAndObserver;

public class Loader implements Subscriber {
    public Loader() {
    }

    @Override
    public String doing(String goods) {
        if (goods.length() > 0) {
            return "Грузчик погрузил и доставил к стойке выдачи заказов " + goods + "\n";
        }
        return "Грузчик отдыхает\n";
    }
}
