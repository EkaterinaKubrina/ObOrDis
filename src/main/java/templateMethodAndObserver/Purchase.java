package templateMethodAndObserver;

import java.util.List;

public abstract class Purchase {
    protected String goods;
    protected double paymentSum;
    protected String payMethod;
    protected List<Subscriber> subscriberList;
    protected String historyOperations;

    public void addSubscriber(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        subscriberList.remove(subscriber);
    }


    public abstract void orderRegistration(String goods, double paymentSum, String payMethod);

    public abstract void applyingDiscounts(boolean discountCard);

    public String invoiceIssued() {
        return "Товары: " + goods + "\nСумма заказа: " + paymentSum;
    }

    public boolean paymentByCard() {
        if (payMethod.equals("картой")) {
            return true;
        }
        return false;
    }

    public boolean cashPayment() {
        if (payMethod.equals("наличными")) {
            return true;
        }
        return false;
    }

    public boolean onlinePayment() {
        if (payMethod.equals("онлайн")) {
            return true;
        }
        return false;
    }

    public String check() {
        return "Оплачено " + paymentSum + " руб.";
    }

    public abstract String deliveryOfGoods();

    public String cashier(String goods, double paymentSum, boolean discountCard, String payMethod) {
        historyOperations = "";
        orderRegistration(goods, paymentSum, payMethod);
        for (Subscriber s : subscriberList) {
            historyOperations += s.doing(goods);
        }
        if (goods.length()==0) {
            return historyOperations + "Заказ не был собран";
        } else {
            applyingDiscounts(discountCard);
            historyOperations += invoiceIssued() + "\n";
            if (paymentByCard() || cashPayment()) {
                historyOperations += check() + "\n";
                return historyOperations + deliveryOfGoods();
            } else if (onlinePayment()) {
                return historyOperations + deliveryOfGoods();
            } else {
                return historyOperations + "Оплата не прошла, заказ не выдали";
            }
        }
    }
}