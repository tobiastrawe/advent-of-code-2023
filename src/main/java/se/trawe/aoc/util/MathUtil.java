package se.trawe.aoc.util;

public class MathUtil {

    public static long lcm(long x, long y, boolean withGCD) {
        if (x == 0 || y == 0) {
            return 0;
        }
        x = Math.abs(x);
        y = Math.abs(y);
        if (withGCD) {
            return Math.abs(x) / gcd(x, y) * Math.abs(y);
        }
        long max = Math.max(x, y);
        long min = Math.min(x, y);
        long lcm = max;
        while (lcm % min != 0) {
            lcm += max;
        }
        return lcm;
    }

    public static long gcd(long x, long y) {
        if (x == y)
            return x;
        if (x == 0)
            return y;
        if (y == 0)
            return x;
        if (x % 2L == 0) {
            if (y % 2L != 0)
                return gcd(x >> 1, y);
            else
                return gcd(x >> 1, y >> 1) << 1;
        }

        if (y % 2L == 0)
            return gcd(x, y >> 1);
        if (x > y)
            return gcd((x - y) >> 1, y);

        return gcd((y - x) >> 1, x);
    }
}
