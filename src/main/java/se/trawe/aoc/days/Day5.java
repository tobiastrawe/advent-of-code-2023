package se.trawe.aoc.days;

import se.trawe.aoc.Task;

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

    private static class Seed {

        private long seed, soil, fertilizer, water, light, temperature, humidity, location;

        public Seed(long seed) {
            this.seed = seed;
        }

        public long getSeed() {
            return seed;
        }

        public long getSoil() {
            return soil;
        }

        public long getFertilizer() {
            return fertilizer;
        }

        public long getWater() {
            return water;
        }

        public long getLight() {
            return light;
        }

        public long getTemperature() {
            return temperature;
        }

        public long getHumidity() {
            return humidity;
        }

        public long getLocation() {
            return location;
        }

        public void setSoil(long soil) {
            this.soil = soil;
        }

        public void setFertilizer(long fertilizer) {
            this.fertilizer = fertilizer;
        }

        public void setWater(long water) {
            this.water = water;
        }

        public void setLight(long light) {
            this.light = light;
        }

        public void setTemperature(long temperature) {
            this.temperature = temperature;
        }

        public void setHumidity(long humidity) {
            this.humidity = humidity;
        }

        public void setLocation(long location) {
            this.location = location;
        }
    }

    Set<Long> seeds = new HashSet<>();
    List<Long> seedToSoilMap = new HashMap<>();
    List<Long> soilToFertilizerMap = new HashMap<>();
    List<Long> fertilizerToWaterMap = new HashMap<>();
    List<Long> waterToLightMap = new HashMap<>();
    List<Long> lightToTemperatureMap = new HashMap<>();
    List<Long> temperatureToHumidityMap = new HashMap<>();
    List<Long> humidityToLocationMap = new HashMap<>();

    @Override
    public String runTaskOne(List<String> input) {
        List<Long, Long> activeMap = null;
        for (String line : input) {
            if (line.startsWith("seeds")) {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher m = pattern.matcher(line);
                while (m.find()) {
                    seeds.add(Long.parseLong(m.group()));
                }
            } else if (line.contains("seed-to-soil")) {
                activeMap = seedToSoilMap;
            } else if (line.contains("soil-to-fertilizer")) {
                activeMap = soilToFertilizerMap;
            } else if (line.contains("fertilizer-to-water")) {
                activeMap = fertilizerToWaterMap;
            } else if (line.contains("water-to-light")) {
                activeMap = waterToLightMap;
            } else if (line.contains("light-to-temperature")) {
                activeMap = lightToTemperatureMap;
            } else if (line.contains("temperature-to-humidity")) {
                activeMap = temperatureToHumidityMap;
            } else if (line.contains("humidity-to-location")) {
                activeMap = humidityToLocationMap;
            } else {
                Pattern numbersPattern = Pattern.compile("\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)");
                Matcher m = numbersPattern.matcher(line);
                if (m.matches()) {
                    long destination = Long.parseLong(m.group(1));
                    long source = Long.parseLong(m.group(2));
                    long range = Long.parseLong(m.group(3));
                    for (long i = 0; i < range; i++) {
                        if (activeMap != null) {
                            activeMap.put(source + i, destination + i);
                        } else {
                            throw new NullPointerException("map not initialized");
                        }
                    }
                }
            }
        }
        long lowestLocation = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long soil = seedToSoilMap.getOrDefault(seed, seed);
            long fertilizer = soilToFertilizerMap.getOrDefault(soil, soil);
            long water = fertilizerToWaterMap.getOrDefault(fertilizer, fertilizer);
            long light = waterToLightMap.getOrDefault(water, water);
            long temperature = lightToTemperatureMap.getOrDefault(light, light);
            long humidity = temperatureToHumidityMap.getOrDefault(temperature, temperature);
            long location = humidityToLocationMap.getOrDefault(humidity, humidity);
            if (location < lowestLocation) {
                lowestLocation = location;
            }
        }
        return String.valueOf(lowestLocation);
    }

    @Override
    public String runTaskTwo(List<String> input) {
        return "no result";
    }


}
