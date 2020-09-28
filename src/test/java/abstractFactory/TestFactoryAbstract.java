package abstractFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFactoryAbstract {

    @Test
    public void test() {
        int[] array = {1, 2, 0, 4, 15, 5};

        FactoryIndex factoryIndex = new FactoryIndex(); //фабрика индексов элементов
        assertEquals(4, DoAbstractFactory.doFactory("maxIndex", array, factoryIndex) ); //находит индекс наибольшего элемента
        assertEquals(2, DoAbstractFactory.doFactory("minIndex", array, factoryIndex) ); //и наименьшего

        FactoryElement factoryElement = new FactoryElement(); //фабрика элементов
        assertEquals(15,DoAbstractFactory.doFactory("maxElement", array, factoryElement) ); //находит наибольший элемент
        assertEquals(0,DoAbstractFactory.doFactory("minElement", array, factoryElement) ); //и наименьший

    }

}
