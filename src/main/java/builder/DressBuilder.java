package builder;

public class DressBuilder extends Builder {

    public DressBuilder() {
        clothes = new Clothes();
    }

    @Override
    public Clothes buildClothes() {
        clothes.setCloth("cotton");
        clothes.setColor("pink");
        clothes.setCollar("crew");
        clothes.setButtons(0);
        clothes.setLegs(0);
        clothes.setSleeves(2);
        clothes.setZipper(1);
        clothes.setLength(0.79);
        return clothes;
    }
}
