package singletonAndPrototype;

public class Shop {
    private ShopOwner shopOwner; //одиночка
    private Seller seller;
    public IPrototype[] goods;

    public Shop(String name) {
        shopOwner = ShopOwner.getInstance(name);
    }

    public void setGoods(IPrototype[] goods) {
        this.goods = goods;
    }

    public String getShopOwnerName() {
        return shopOwner.getName();
    }

    public void dismissASeller1(){
        seller = null;
    }

    public void hireASeller1(String name){
        seller = new Seller(name);
    }


}
