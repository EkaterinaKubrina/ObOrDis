package base;

public class FindFirstNegativeElem implements IFindElement {
    public String id = "FirstNegativeElem";
    int[] array;

    public FindFirstNegativeElem(int[] array) {
        this.array = array;
    }

    public int findElement() {
        if (array.length != 0) {
            for (int i : array) {
                if (i < 0) {
                    return i;
                }
            }
        }
        return 0;
    }
}
