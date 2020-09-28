package abstractFactory;

public class FactoryIndex implements AbstractFactory {

    @Override
    public IFindMin findMin(int[] array) {
        return new FindMinElementIndex(array);
    }

    @Override
    public IFindMax findMax(int[] array) {
        return new FindMaxElementIndex(array);
    }

}
