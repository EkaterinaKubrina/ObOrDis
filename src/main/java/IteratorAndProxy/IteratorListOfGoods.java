package IteratorAndProxy;

public class IteratorListOfGoods implements Iterator{
    private IterableList iterableList;
    private int position;

    public IteratorListOfGoods(IterableList iterableList) {
        this.iterableList = iterableList;
        this.position = 0;
    }

    public String getGood(){
        return iterableList.getGood(position);
    }

    public boolean hasNext(){
        return iterableList.getSize()>=position+1;
    }

    @Override
    public void next() {
        if(hasNext()){
            position+=1;
        }
    }
}
