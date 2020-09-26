package builder;

public class CoatBuilder extends Builder{

    public CoatBuilder() {
        clothes = new Clothes();
    }

    @Override
    public Clothes buildClothes() {
        clothes.setCloth("raincoat");
        clothes.setColor("black");
        clothes.setCollar("notched");
        clothes.setButtons(10);
        clothes.setLegs(0);
        clothes.setSleeves(2);
        clothes.setZipper(0);
        clothes.setLength(1.08);
        return clothes;
    }
}
