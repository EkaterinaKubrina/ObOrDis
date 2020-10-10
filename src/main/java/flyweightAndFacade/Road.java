package flyweightAndFacade;

public class Road {
    private int length;
    private String description;

    public Road(int length, String description) {
        this.length = length;
        this.description = description;
    }

    public void setDescription(String descriptionSet){
        description += ", " + descriptionSet;
    }

    public String getDescription() {
        return description;
    }
}
