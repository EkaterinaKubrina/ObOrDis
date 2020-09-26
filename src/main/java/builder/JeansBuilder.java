package builder;

public class JeansBuilder extends Builder {

    public JeansBuilder() {
        clothes = new Clothes();
    }

    @Override
    public Clothes buildClothes() {
        clothes.setCloth("denim");
        clothes.setColor("blue");
        clothes.setCollar(null);
        clothes.setButtons(1);
        clothes.setLegs(2);
        clothes.setSleeves(0);
        clothes.setZipper(1);
        clothes.setLength(0.8);
        return clothes;
    }
}
