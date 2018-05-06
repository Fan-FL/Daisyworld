import java.util.HashSet;

public class Util {
    public static int getRandom(int min, int max, HashSet<Integer> set) {
        int num = (int) (Math.random() * (max - min)) + min;
        while (set.contains(num)) {
            num = (int) (Math.random() * (max - min)) + min;
        }
        set.add(num);
        return num;
    }
}
