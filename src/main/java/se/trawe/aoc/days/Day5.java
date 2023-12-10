package se.trawe.aoc.days;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 extends Task {

    private final static Day5 instance;

    static {
        instance = new Day5();
    }

    private Day5() {
    }

    @SuppressWarnings(value = "unused")
    public static Day5 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day5().run();
    }

    private record RangeMap(long source, long destination, long range) {
    }

    private record Range(long from, long to) {}

    private static final Set<Range> seeds = new HashSet<>();
    private static final Set<RangeMap> seedToSoilRanges = new HashSet<>();
    private static final Set<RangeMap> soilToFertilizerRanges = new HashSet<>();
    private static final Set<RangeMap> fertilizerToWaterRanges = new HashSet<>();
    private static final Set<RangeMap> waterToLightRanges = new HashSet<>();
    private static final Set<RangeMap> lightToTemperatureRanges = new HashSet<>();
    private static final Set<RangeMap> temperatureToHumidityRanges = new HashSet<>();
    private static final Set<RangeMap> humidityToLocationRanges = new HashSet<>();

    @Override
    protected String runTaskOne(List<String> input) {
        Set<RangeMap> activeList = null;
        for (String line : input) {
            if (line.startsWith("seeds")) {
                Pattern pattern = Pattern.compile("(\\d+)\\s(\\d+)");
                Matcher m = pattern.matcher(line);
                while (m.find()) {
                    long from = Long.parseLong(m.group(1));
                    long to = from + Long.parseLong(m.group(2));
                    seeds.add(new Range(from, to));
                }
            } else if (line.contains("seed-to-soil")) {
                activeList = seedToSoilRanges;
            } else if (line.contains("soil-to-fertilizer")) {
                activeList = soilToFertilizerRanges;
            } else if (line.contains("fertilizer-to-water")) {
                activeList = fertilizerToWaterRanges;
            } else if (line.contains("water-to-light")) {
                activeList = waterToLightRanges;
            } else if (line.contains("light-to-temperature")) {
                activeList = lightToTemperatureRanges;
            } else if (line.contains("temperature-to-humidity")) {
                activeList = temperatureToHumidityRanges;
            } else if (line.contains("humidity-to-location")) {
                activeList = humidityToLocationRanges;
            } else {
                Pattern numbersPattern = Pattern.compile("\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)");
                Matcher m = numbersPattern.matcher(line);
                if (m.matches()) {
                    long destination = Long.parseLong(m.group(1));
                    long source = Long.parseLong(m.group(2));
                    long range = Long.parseLong(m.group(3));
                    assert activeList != null;
                    activeList.add(new RangeMap(source, destination, range));
                }
            }
        }
        long lowestLocation;
        for (int location = 0;; location++) {
            long humidity = findInRangesReverse(location, humidityToLocationRanges);
            long temperature = findInRangesReverse(humidity, temperatureToHumidityRanges);
            long light = findInRangesReverse(temperature, lightToTemperatureRanges);
            long water = findInRangesReverse(light, waterToLightRanges);
            long fertilizer = findInRangesReverse(water, fertilizerToWaterRanges);
            long soil = findInRangesReverse(fertilizer, soilToFertilizerRanges);
            long seed = findInRangesReverse(soil, seedToSoilRanges);
            if (seeds.stream().anyMatch(s -> seed >= s.from && seed <= s.to)) {
                lowestLocation = location;
                break;
            }
        }
        return String.valueOf(lowestLocation);
    }

    private Set<Range> findIntersectInRange(Range fromRange, Set<RangeMap> rangeMapSet) {

        for(RangeMap rangeMap : rangeMapSet) {
            Range toRange = new Range(rangeMap.source, rangeMap.source + rangeMap.range);
            if (fromRange.from >= toRange.from && fromRange.from <= toRange.to) {
                System.out.println("range " + fromRange + " is inside " + toRange);

            } else {
                System.out.println("range " + fromRange + " is not within " + toRange);
            }
        }
        return null;
    }

    private long findInRanges(long source, Set<RangeMap> ranges) {
        for (RangeMap range : ranges) {
            if (source >= range.destination && source <= range.destination + range.range) {
                return (source - range.destination) + range.source;
            }
        }
      return source;
    }

    private long findInRangesReverse(long source, Set<RangeMap> ranges) {
        for (RangeMap range : ranges) {
            if (source >= range.destination && source <= range.destination + range.range) {
                return (source - range.destination) + range.source;
            }
        }
      return source;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return runTaskOne(input);
    }


}
