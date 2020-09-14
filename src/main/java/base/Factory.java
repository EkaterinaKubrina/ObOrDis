package base;

public class Factory {
    public IFindElement getFindElem(String id, int[] array){
        switch (id) {
            case "FirstNegativeElem":
                return new FindFirstNegativeElem(array);
            case "MaxElem":
                return new FindMaxElem(array);
            case "MinElem":
                return new FindMinElem(array);
            default:
                return null;
        }
    }
}
