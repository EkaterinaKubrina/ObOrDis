package abstractFactory;

public class DoAbstractFactory {
    public static int doFactory (String id, int[] array, AbstractFactory abstractFactory) { //метод использует только интерфейсы фабрик и функций
        if(abstractFactory instanceof FactoryElement){
            switch (id){
                case ("maxElement"): return abstractFactory.findMax(array).findElement();
                case("minElement"): return abstractFactory.findMin(array).findElement();
            }
        }
        else if(abstractFactory instanceof FactoryIndex){
            switch (id){
                case ("maxIndex"): return abstractFactory.findMax(array).findElement();
                case("minIndex"): return abstractFactory.findMin(array).findElement();
            }
        }
        return -1;
    }
}
