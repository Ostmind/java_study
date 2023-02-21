public class Cylinder extends Geometry {

    private static final String NAME = "Cylinder";

    private double radius;
    
    private double height;

    public Cylinder(double radius, double height, double mass) {
        super(mass);
        this.radius = radius;
        this.height = height;
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

    @Override
    public double getArea(){
        return Math.PI * radius * radius * height;
    }
    
}
