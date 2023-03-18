import java.util.Arrays; 
import java.util.List; 
 
class Main { 
 
 public static void main(String[] args) {
 int[] nums = new int[]{1,2,3,4,5,6,7,8,9,10};
 int sum = Arrays.stream(nums).filter(i -> i % 2 == 0).sum(); 
 System.out.println(sum); 
 } 
}
