package flyweightAndFacade;

public class Facade {

    public static Road createRoad(int length, String description){ //создаем дорогу по умолчанию
        Road road = new Road(length, description);

        House house = new House(5); //легковес
        road.setDescription(house.drawingHouse("зеленый", "c левой стороны", 1));
        road.setDescription(house.drawingHouse("синий", "c левой стороны", 1));
        road.setDescription(house.drawingHouse("желтый", "c правой стороны", 2));

        RoadSigns roadSigns = new RoadSigns(2); //легковес
        road.setDescription(roadSigns.DrawingSigns("предписывающий", "с правой стороны", 1));
        road.setDescription(roadSigns.DrawingSigns("предупреждающий", "с левой стороны", 2));
        road.setDescription(roadSigns.DrawingSigns("запрещающий движение", "с правой стороны", length));
        return road;
    }
}
