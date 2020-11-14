package templateMethodAndObserver;

import java.util.ArrayList;

public class PurchaseOnline extends Purchase {

    public PurchaseOnline(String goods, double paymentSum, String payMethod) {
        this.goods = goods;
        this.paymentSum = paymentSum;
        this.payMethod = payMethod;
        subscriberList = new ArrayList<>();
    }

    @Override
    public void orderRegistration(String goods, double paymentSum, String payMethod) {
    }

    @Override
    public void applyingDiscounts(boolean discountCard) {
    }


    @Override
    public String deliveryOfGoods() {
        return "Заказ, оформленный на сайте, оплачен " + payMethod + " и выдан покупателю";
    }
}
