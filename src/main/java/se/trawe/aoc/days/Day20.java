package se.trawe.aoc.days;

import se.trawe.aoc.util.MathUtil;

import java.util.*;

public class Day20 extends Task {

    private final static Day20 instance;

    static {
        instance = new Day20();
    }

    private Day20() {
    }

    @SuppressWarnings(value = "unused")
    public static Day20 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day20().run();
    }

    private enum ModuleType {
        BUTTON,
        BROADCASTER,
        FLIP_FLOP,
        CONJUNCTION;
    }

    private enum Pulse {
        HIGH,
        LOW
    }

    private abstract class Module {
        final String name;
        final ModuleType type;
        final Set<String> targets;

        private Module(String name, ModuleType type, Set<String> targets) {
            this.name = name;
            this.type = type;
            this.targets = targets;
        }

        abstract List<Command> process(String from, Pulse pulse);
    }

    private final class Broadcaster extends Module {

        private Broadcaster(String name, Set<String> targets) {
            super(name, ModuleType.BROADCASTER, targets);
        }

        @Override
        List<Command> process(String from, Pulse pulse) {
            List<Command> commands = new ArrayList<>();
            for (String target : targets) {
                commands.add(new Command(name, Pulse.LOW, target));
            }
            return commands;
        }
    }

    private final class Flipflop extends Module {
        boolean on = false;

        public Flipflop(String name, Set<String> targets) {
            super(name, ModuleType.FLIP_FLOP, targets);
        }

        @Override
        List<Command> process(String from, Pulse pulse) {
            List<Command> commands = new ArrayList<>();
            if (pulse.equals(Pulse.LOW)) {
                on = !on;
                Pulse pulseToSend;
                if (on) {
                    pulseToSend = Pulse.HIGH;
                } else {
                    pulseToSend = Pulse.LOW;
                }
                for (String target : targets) {
                    commands.add(new Command(this.name, pulseToSend, target));
                }
            }
            return commands;
        }
    }

    private final class Conjunction extends Module {
        private final HashMap<String, Pulse> rememberedPulses = new HashMap<>();

        private Conjunction(String name, Set<String> targets) {
            super(name, ModuleType.CONJUNCTION, targets);
        }

        @Override
        List<Command> process(String from, Pulse pulse) {
            rememberedPulses.put(from, pulse);
            Pulse pulseToSend;
            if (rememberedPulses.values().stream().allMatch(p -> p.equals(Pulse.HIGH))) {
                pulseToSend = Pulse.LOW;
            } else {
                pulseToSend = Pulse.HIGH;
            }
            List<Command> commands = new ArrayList<>();
            for (String target : targets) {
                commands.add(new Command(this.name, pulseToSend, target));
            }
            return commands;
        }
    }

    private record Command(String from, Pulse pule, String to) {
    }

    private final HashMap<String, Module> moduleHashMap = new HashMap<>();

    private boolean initialized = false;

    private void init(List<String> input) {
        if (!initialized) {
            initialized = true;
            List<String> conjunctionNames = new ArrayList<>();
            for (String line : input) {
                String[] split = line.split(" -> ");
                Set<String> targets = new HashSet<>(List.of(split[1].split(", ")));
                String module = split[0];
                if (module.startsWith("broadcaster")) {
                    moduleHashMap.put("broadcaster", new Broadcaster(module, targets));
                } else if (module.startsWith("%")) {
                    moduleHashMap.put(module.substring(1), new Flipflop(module.substring(1), targets));
                } else if (module.startsWith("&")) {
                    conjunctionNames.add(module.substring(1));
                    moduleHashMap.put(module.substring(1), new Conjunction(module.substring(1), targets));
                }
            }
            moduleHashMap.forEach((k, v) -> {
                for (String conjunctionName : conjunctionNames) {
                    if (v.targets.contains(conjunctionName)) {
                        Conjunction conjunction = (Conjunction) moduleHashMap.get(conjunctionName);
                        conjunction.rememberedPulses.put(k, Pulse.LOW);
                    }
                }
            });
        } else {
            for (Module m : moduleHashMap.values()) {
                if (m instanceof Conjunction c) {
                    c.rememberedPulses.replaceAll((k, v) -> Pulse.LOW);
                } else if (m instanceof Flipflop f) {
                    f.on = false;
                }
            }
        }
    }

    @Override
    protected String runTaskOne(List<String> input) {
        init(input);
        LinkedList<Command> commandStack = new LinkedList<>();
        int countLow = 0;
        int countHigh = 0;
        for (int i = 0; i < 1000; i++) {
            commandStack.add(new Command("button", Pulse.LOW, "broadcaster"));
            while (!commandStack.isEmpty()) {
                Command c = commandStack.removeFirst();
                if (c.pule.equals(Pulse.HIGH)) {
                    countHigh++;
                } else {
                    countLow++;
                }
                Module m = moduleHashMap.get(c.to);
                if (m != null) {
                    //System.out.printf("%s -%s-> %s%n", c.from, c.pule.name().toLowerCase(), c.to);
                    commandStack.addAll(m.process(c.from, c.pule));
                }
            }
        }
        return String.valueOf(countLow * countHigh);
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        init(input);
        LinkedList<Command> commandStack = new LinkedList<>();
        long[] numbers = new long[4];
        int found = 0;
        List<String> lookingForModules = new ArrayList<>();
        lookingForModules.add("ks");
        lookingForModules.add("jf");
        lookingForModules.add("qs");
        lookingForModules.add("zk");
        long buttonPresses = 0L;
        while (!lookingForModules.isEmpty()) {
            buttonPresses++;
            commandStack.add(new Command("button", Pulse.LOW, "broadcaster"));
            while (!commandStack.isEmpty()) {
                Command c = commandStack.removeFirst();
                if (lookingForModules.contains(c.from) && c.pule.equals(Pulse.HIGH)) {
                    lookingForModules.remove(c.from);
                    numbers[found++] = buttonPresses;
                }
                Module m = moduleHashMap.get(c.to);
                if (m != null) {
                    //System.out.printf("%s -%s-> %s%n", c.from, c.pule.name().toLowerCase(), c.to);
                    commandStack.addAll(m.process(c.from, c.pule));
                }
            }
        }
        return String.valueOf(Arrays.stream(numbers).reduce(1, (x, y) -> x * y));
    }


}
