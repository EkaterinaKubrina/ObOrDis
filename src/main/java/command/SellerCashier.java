package command;

public class SellerCashier {

    public static Drink submitAnOrder(Command command){
        return command.execute();
    }
}
