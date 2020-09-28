package abstractFactory;

import abstractFactory.IFindMin;

public class FindMinElementIndex implements IFindMin { //находит ИНДЕКС минимального элемента
    public String id = "MinIndex";
    int[] array;

    public FindMinElementIndex(int[] array) {
        this.array = array;
    }

    public int findElement() {
        if (array.length != 0) {
            int min = Integer.MAX_VALUE;
            int minI = 0;
            for (int i = 0; i<array.length; i++) {
                if (array[i] < min) {
                    min = array[i];
                    minI = i;
                }
            }
            return minI;
        }
        return -1;
    }
}
