package base;

public class FindMaxElem implements IFindElement {
    public String id = "MaxElem";
    int[] array;

    public FindMaxElem(int[] array) {
        this.array = array;
    }

    public int findElement() {
        if (array.length != 0) {
            int max = Integer.MIN_VALUE;
            for (int i : array) {
                if (i > max) {
                    max = i;
                }
            }
            return max;
        }
        return -1;
    }
}
