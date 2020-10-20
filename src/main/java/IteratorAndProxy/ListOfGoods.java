package IteratorAndProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListOfGoods implements IterableList, IProxy{
    private List<String> list;

    public ListOfGoods() {
        this.list = new ArrayList<>();
    }


    public void addGood(String s){
        list.add(s);
    }

    public void deleteGood(int i){
        list.remove(i);
    }

    @Override
    public Iterator createIterator() {
        return new IteratorListOfGoods(this);
    }

    public String getGood(int i){
        return list.get(i);
    }

    public void setGood(String s, int i ){
        list.set(i, s);
    }

    public int getSize(){
        return list.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListOfGoods)) return false;
        ListOfGoods that = (ListOfGoods) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
