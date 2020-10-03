package compositeAndVisitor;

public class IncreaseVisitor implements IVisitor{

    @Override
    public void visit(Box box, double times) { //посетитель коробок увеличивает не только вес коробки, но и всего содержимого
        box.setWeight(box.getWeightBox()*times);
        for(Pack p : box.getPacks()){
            p.setWeight(p.getWeight()*times);
        }
    }

    @Override
    public void visit(PacketOfCandy packetOfCandy, double times) { //увеличивает только вес данной упаковки конфет
        packetOfCandy.setWeight(packetOfCandy.getWeight()*times);
    }
}
