package flyweightAndFacade;

public class RoadSigns { //легковес
    private int height;

    public RoadSigns(int height) {
        this.height = height;
    }

    public String DrawingSigns(String name, String positionFromTheRoad, int kmRoad){
        return "на " + kmRoad + "км. дороги " + positionFromTheRoad + " " + name + " " + height +"-метровый дорожный знак";
    }
}
