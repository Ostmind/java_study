public abstract class Geometry {
    
    private double mass;

    public Geometry (double mass){
        this.mass = mass;
    }
    
    public abstract String getName();

    public double getDensity(){
        return mass / getArea();
    };
    
    public abstract double getArea ();
}
