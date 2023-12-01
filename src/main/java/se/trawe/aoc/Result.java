
package se.trawe.aoc;
public record Result(String day, String resultTaskOne, String resultTaskTwo) {

    public Result(String day) {
        this(day, "no result", "no result");
    }

    public Result(String day, String resultTaskOne) {
        this(day, resultTaskOne, "no result");
    }
}
