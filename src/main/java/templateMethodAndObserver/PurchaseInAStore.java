package templateMethodAndObserver;

import java.util.ArrayList;

public class PurchaseInAStore extends Purchase{

    public PurchaseInAStore() {
        subscriberList = new ArrayList<>();
    }

    @Override
    public void orderRegistration(String goods, double paymentSum, String payMethod) {
        this.goods = goods;
        this.paymentSum = paymentSum;
        this.payMethod = payMethod;
    }

    @Override
    public void applyingDiscounts(boolean discountCard) {
        if(discountCard){
            paymentSum = paymentSum*0.95;
        }
    }


    @Override
    public String check() {
        return "Оплачено " + paymentSum + " руб.";

    }

    @Override
    public String deliveryOfGoods() {
        return "Заказ был оплачен " + payMethod + " и выдан покупателю";
    }
}
