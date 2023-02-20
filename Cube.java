public class Cube extends Geometry {
    private static final String NAME = "Cube";

    private double edge;
    private double mass;

    public Cube(double edge, double mass) {
        this.edge = edge;
        this.mass = mass;
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

    public void setMass(double mass) {
        this.edge = mass;
    }

    public double getMass(double mass) {
        return mass;
    }

    @Override
    public double getDensity() {
        double density = (edge * edge * edge) / mass ;
        return density;
    }
}