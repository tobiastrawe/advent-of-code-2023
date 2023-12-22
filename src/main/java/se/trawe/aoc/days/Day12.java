package se.trawe.aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Task {

    private final static Day12 instance;

    private List<String> tempCombinations = new ArrayList<>();

    static {
        instance = new Day12();
    }

    private Day12() {
    }

    @SuppressWarnings(value = "unused")
    public static Day12 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day12().run();
    }

    protected record Combination(String regex, List<String> potentialMatches) {}

    protected record TripleKey(int i, int j, int k) {}

    @Override
    protected String runTaskOne(List<String> input) {
        List<Combination> combinationsList = new ArrayList<>();
        for (var line : input) {
            var split = line.split(" ");
            var puzzle = split[0];
            var totalCogs = 0;
            StringBuilder regex = new StringBuilder("^\\.*");
            for (var number : split[1].split(",")) {
                totalCogs++;
                regex.append("#{").append(number).append("}\\.+");
            }
            regex = new StringBuilder(regex.toString().replaceFirst("[+]$", "*\\$"));
            List<String> combinations = new ArrayList<>();
            generateCombinations(combinations, puzzle, totalCogs);
            combinationsList.add(new Combination(regex.toString(), combinations));
        }
        var value = 0L;
        for (Combination c : combinationsList) {
            Pattern p = Pattern.compile(c.regex);
            for (String s : c.potentialMatches) {
                if (p.matcher(s).matches()) {
                    value++;
                }
            }
        }
        return String.valueOf(value);
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        record Data(String puzzle, ArrayList<Integer> numbers) {}

        List<Data> dataList = new ArrayList<>();
        for (var line : input) {
            var split = line.split(" ");
            String puzzle = String.join("?", Arrays.asList(split[0], split[0], split[0], split[0], split[0]));
            var numberList = new ArrayList<Integer>();
            for (int i = 0; i < 5; i++) {
                for (var number : split[1].split(",")) {
                    numberList.add(Integer.parseInt(number));
                }
            }
            dataList.add(new Data(puzzle, numberList));
        }
        return String.valueOf(dataList.stream().mapToLong(c -> countArrangements(new HashMap<>(), c.puzzle, c.numbers.stream().mapToInt(n -> n).toArray(), 0, 0, 0)).sum());
    }

    private void generateCombinations(List<String> list, String puzzle, int limit) {
        if (puzzle.chars().filter(ch -> ch == 'e').count() > limit) {
            return;
        }
        if(puzzle.contains("?")) {
            generateCombinations(list, replace(puzzle, '.'), limit);
            generateCombinations(list, replace(puzzle, '#'), limit);
        } else {
            list.add(puzzle);
        }
    }

    public static String replace(String s, char replacement){
        char[] charSeq = s.toCharArray();
        int i;
        for(i=0; charSeq[i]!='?'; i++);
        charSeq[i]=replacement;
        return new String(charSeq);
    }

    private long countArrangements(HashMap<TripleKey, Long> blockMap, String map, int[] amounts, int i, int j, int cur) {
        var key = new TripleKey(i, j, cur);
        /*if (blockMap.containsKey(key)) {
            return blockMap.get(key);
        }*/
        if (i == map.length()) {
            return (j == amounts.length && cur == 0) || (j == amounts.length - 1 && amounts[j] == cur) ? 1 : 0;
        }
        long total = 0;
        char c = map.charAt(i);
        if ((c == '.' || c == '?') && cur == 0) {
            total += countArrangements(blockMap, map, amounts, i + 1, j, 0);
        } else if ((c == '.' || c == '?') && cur > 0 && j < amounts.length && amounts[j] == cur) {
            total += countArrangements(blockMap, map, amounts, i + 1, j + 1, 0);
        }
        if (c == '#' || c == '?') {
            total += countArrangements(blockMap, map, amounts, i + 1, j, cur + 1);
        }
        blockMap.put(key, total);
        return total;
    }
}
