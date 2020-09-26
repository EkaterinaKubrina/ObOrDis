package builder;

public class Clothes {
    private String cloth, color, collar; //ткань, цвет, воротник
    private int sleeves, legs, buttons, zipper; //рукава, штанины, пуговицы, молния
    private double length; //длина

    public Clothes() {
    }

    public String getCloth() {
        return cloth;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCollar() {
        return collar;
    }

    public void setCollar(String collar) {
        this.collar = collar;
    }

    public int getSleeves() {
        return sleeves;
    }

    public void setSleeves(int sleeves) {
        this.sleeves = sleeves;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public int getButtons() {
        return buttons;
    }

    public void setButtons(int buttons) {
        this.buttons = buttons;
    }

    public int getZipper() {
        return zipper;
    }

    public void setZipper(int zipper) {
        this.zipper = zipper;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
