package templateMethodAndObserver;

public class Warehouse implements Subscriber {
    public Warehouse() {
    }

    @Override
    public String doing(String goods) {
        if (goods.length() > 0) {
            return "Склад выдал грузчику " + goods + "\n";
        }
        return "Склад отдыхает\n";
    }
}
