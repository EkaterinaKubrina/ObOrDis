package IteratorAndProxy;

public class ProxyListOfGoods implements IProxy{
    private ListOfGoods realListOfGoods;

    public ProxyListOfGoods(ListOfGoods realListOfGoods) {
        this.realListOfGoods = realListOfGoods;
    }

    @Override
    public void addGood(String s) {
        String sCorrected = (realListOfGoods.getSize()+1) + ". " + s.trim().toLowerCase();
        realListOfGoods.addGood(sCorrected);
    }

    @Override
    public void deleteGood(int i) {
    setGood(getGood(realListOfGoods.getSize()), i);
    realListOfGoods.deleteGood(realListOfGoods.getSize());
    }

    @Override
    public void setGood(String s, int i) {
        String sCorrected = (i+1) + ". " + s.trim().toLowerCase();
        realListOfGoods.setGood(sCorrected, i);
    }

    @Override
    public String getGood(int i) {
        return realListOfGoods.getGood(i).replaceAll((i+1)+". ", "");
    }
}
