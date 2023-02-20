public class Cylinder extends Geometry {
    private static final String NAME = "Cylinder";

    private double radius;
    private double height;
    private double mass;

    public Cylinder(double radius, double height, double mass) {
        this.radius = radius;
        this.height = height;
        this.mass = mass;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double width) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass(double mass) {
        return mass;
    }

    @Override
    public double getDensity() {
        double density = (Math.PI * radius * radius * height) / mass;
        return density;
    }
}