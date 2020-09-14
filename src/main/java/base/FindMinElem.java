package base;

public class FindMinElem implements IFindElement {
    public String id = "MinElem";
    int[] array;

    public FindMinElem(int[] array) {
        this.array = array;
    }

    public int findElement() {
        if (array.length != 0) {
            int min = Integer.MAX_VALUE;
            for (int i : array) {
                if (i < min) {
                    min = i;
                }
            }
            return min;
        }
        return -1;
    }
}
