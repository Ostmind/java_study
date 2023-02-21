public class Main {
    public static void main(String[] args) {
        Geometry[] geometry = {new Cylinder(2, 5,80),
                new Cube(5,250)};

        for(Geometry geom : geometry)
            System.out.println(geom.getName() + ": density = " + geom.getDensity());
    }
}
