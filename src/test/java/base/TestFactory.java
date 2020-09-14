package base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFactory {

    @Test
    public void testFindMax() {
        Factory factory = new Factory();

        IFindElement findMax = factory.getFindElem("MaxElem", new int[]{1, 2, 3, 4, 5});
        assertEquals(5, findMax.findElement());

        IFindElement findMax1 = factory.getFindElem("MaxElem", new int[]{7, 4, 7, 5, 1});
        assertEquals(7, findMax1.findElement());

        IFindElement findMax2 = factory.getFindElem("MaxElem", new int[]{});
        assertEquals(-1, findMax2.findElement());

    }

    @Test
    public void testFindMin() {
        Factory factory = new Factory();

        IFindElement findMin = factory.getFindElem("MinElem", new int[]{1, 2, 3, 4, 5});
        assertEquals(1, findMin.findElement());

        IFindElement findMin1 = factory.getFindElem("MinElem", new int[]{1, 5, 0, 9, 0});
        assertEquals(0, findMin1.findElement());

        IFindElement findMin2 = factory.getFindElem("MinElem", new int[]{});
        assertEquals(-1, findMin2.findElement());
    }

    @Test
    public void testFindNegative() {
        Factory factory = new Factory();

        IFindElement findNegative = factory.getFindElem("FirstNegativeElem", new int[]{1, 2, -3, 4, 5});
        assertEquals(-3, findNegative.findElement());

        IFindElement findNegative1 = factory.getFindElem("FirstNegativeElem", new int[]{-1, 2, -3, 4, -5});
        assertEquals(-1, findNegative1.findElement());

        IFindElement findNegative2 = factory.getFindElem("FirstNegativeElem", new int[]{});
        assertEquals(0, findNegative2.findElement());

        IFindElement findNegative3 = factory.getFindElem("FirstNegativeElem", new int[]{1, 2, 3, 4, 5});
        assertEquals(0, findNegative3.findElement());

    }


}
