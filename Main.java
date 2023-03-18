import java.util.Arrays; 
import java.util.List; 
import java.util.stream.IntStream;

class Main { 
 public static void main(String[] args) {
 int[] nums = new int[]{1,2,3,4,5,6,7,8,9,10};
 int sum = IntStream.range(0, nums.length).filter(index -> index % 2 == 0).map(index -> nums[index]).sum();
 System.out.println(sum); 
 } 
}
