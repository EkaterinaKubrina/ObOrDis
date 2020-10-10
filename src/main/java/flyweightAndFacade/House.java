package flyweightAndFacade;

public class House { //легковес
    private int floors;

    public House(int floors) {
        this.floors = floors;
    }

    public String drawingHouse(String color, String positionFromTheRoad, int kmRoad ){
        return "на " + kmRoad + "км. дороги " + positionFromTheRoad + " " + color + " " +floors+ "-этажный дом";
    }
}
