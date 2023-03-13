import java.util.ArrayList;
import java.util.Collections;
public class Main {

    public static void main(String[] args) {

        boolean isSorted = false;
        Geometry cylinder = new Cylinder(2, 5, 80);
        Geometry cylinder1 = new Cylinder(2, 5, 70);
        Geometry cylinder2 = new Cylinder(2, 5, 60);
        Geometry cube = new Cube(5, 250);
        Geometry cube1 = new Cube(5, 200);
        Geometry cube2 = new Cube(5, 150);
        ArrayList<Geometry> geometry = new ArrayList<>();
        geometry.add(cylinder);
        geometry.add(cylinder1);
        geometry.add(cylinder2);
        geometry.add(cube);
        geometry.add(cube1);
        geometry.add(cube2);
        for (Geometry geom : geometry)
            System.out.println(geom.getName() + ": density = " + geom.getDensity());
        System.out.println("Sorting");
        while(!isSorted) { //Внешний цикл
            isSorted = true;
            for (int in = 0; in < geometry.size()-1; in++) {       //Внутренний цикл
                if (geometry.get(in).getDensity() > geometry.get(in + 1).getDensity()) {
                    isSorted = false;
                    Collections.swap(geometry, in, in + 1);
                }
            }
        }
        for (Geometry geom : geometry)
            System.out.println(geom.getName() + ": density = " + geom.getDensity());
    }
}
