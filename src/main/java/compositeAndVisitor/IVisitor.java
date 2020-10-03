package compositeAndVisitor;

public interface IVisitor {
    void visit(Box box, double times);
    void visit(PacketOfCandy packetOfCandy, double times);
}
