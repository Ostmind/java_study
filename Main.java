public class Main {
    public static void main(String[] args) {
        Geometry[] geometry = {new Cylinder(2, 5,4),
                               new Cube(5,5)};

        for(Geometry geom : geometry)
            System.out.println(geom.getName() + ": density = " + geom.getDensity());
    }
}