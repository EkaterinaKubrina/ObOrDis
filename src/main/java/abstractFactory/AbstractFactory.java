package abstractFactory;

public interface AbstractFactory { //абстрактная фабрика фабрик
    IFindMin findMin(int[] array);
    IFindMax findMax(int[] array);
}
