import java.util.HashSet;

/*
    Some util functions in this class
 */
public class Util {
    /*
        Return a int number that is between min and max (not included)
        and not in @param set
     */
    public static int getRandom(int min, int max, HashSet<Integer> set) {
        int num = (int) (Math.random() * (max - min)) + min;
        while (set.contains(num)) {
            num = (int) (Math.random() * (max - min)) + min;
        }
        set.add(num);
        return num;
    }
}
