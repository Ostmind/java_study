
public class Cube extends Geometry {
    
    private static final String NAME = "Cube";

    private double edge;

    public Cube(double edge, double mass) {
        super(mass);
        this.edge = edge;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public void setEdge(double edge) {
        this.edge = edge;
    }

    public double getEdge(double edge) {
        return edge;
    }
    @Override
    public double getArea(){
        return edge * edge * edge;
    }

}
