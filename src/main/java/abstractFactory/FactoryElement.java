package abstractFactory;

import base.FindMaxElem;
import base.FindMinElem;

public class FactoryElement implements AbstractFactory {

    @Override
    public IFindMin findMin(int[] array) {
        return new FindMinElem(array);
    }

    @Override
    public IFindMax findMax(int[] array) {
        return new FindMaxElem(array);
    }

}
