package builder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestBuilder {


    @Test
    public void test() {
        Director director = new Director("coat");
        Clothes clothes = director.buildClothes();
        assertEquals(10, clothes.getButtons());
        assertEquals("black", clothes.getColor());
        assertEquals(0, clothes.getLegs());
    }

    @Test
    public void test1() {
        Director director1 = new Director("dress");
        Clothes clothes1 = director1.buildClothes();
        assertEquals(0, clothes1.getButtons());
        assertEquals("pink", clothes1.getColor());
        assertEquals(2, clothes1.getSleeves());
    }

    @Test
    public void test2(){
        Director director2 = new Director("jeans");
        Clothes clothes2 = director2.buildClothes();
        assertEquals(0, clothes2.getSleeves());
        assertEquals("blue", clothes2.getColor());
        assertNull(clothes2.getCollar());

    }
}


