package builder;

public class BlouseBuilder extends Builder {

    public BlouseBuilder() {
        clothes = new Clothes();
    }

    @Override
    public Clothes buildClothes() {
        clothes.setCloth("silk");
        clothes.setColor("white");
        clothes.setCollar("cascade");
        clothes.setButtons(6);
        clothes.setLegs(0);
        clothes.setSleeves(2);
        clothes.setZipper(0);
        clothes.setLength(0.49);
        return clothes;
    }
}
