package abstractFactory;

import abstractFactory.IFindMax;

public class FindMaxElementIndex implements IFindMax { //находит ИНДЕКС максимального элемента
    public static String id = "MaxIndex";
    int[] array;

    public FindMaxElementIndex(int[] array) {
        this.array = array;
    }

    public int findElement() {
        if (array.length != 0) {
            int max = Integer.MIN_VALUE;
            int maxI = 0;
            for (int i = 0; i<array.length; i++) {
                if (array[i] > max) {
                    max = array[i];
                    maxI = i;
                }
            }
            return maxI;
        }
        return -1;
    }
}
