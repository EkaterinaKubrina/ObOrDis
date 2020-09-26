package singletonAndPrototype;

public class ShopOwner { //одиночка
    private static ShopOwner instance;
    public String name;

    private ShopOwner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static ShopOwner getInstance(String name) {
        if (instance == null) {
            instance = new ShopOwner(name);
        }
        return instance;
    }

    public int getSalary(){
        return 20000;
    }


}
