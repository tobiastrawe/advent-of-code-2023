package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 extends Task {

    private final static Day13 instance;

    static {
        instance = new Day13();
    }

    private Day13() {
    }

    @SuppressWarnings(value = "unused")
    public static Day13 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day13().run();
    }

    public record Reflection(List<String> pattern, boolean rotated, int a, int b) {
    }

    private List<Reflection> reflectionListWithoutSmudge = new ArrayList<>();
    private List<Reflection> reflectionListWithSmudge = new ArrayList<>();

    @Override
    protected String runTaskOne(List<String> input) {
        List<String> pattern = new ArrayList<>();
        List<List<String>> patternList = new ArrayList<>();
        for (String line : input) {
            if (line.length() > 0) {
                pattern.add(line);
            } else {
                patternList.add(pattern);
                pattern = new ArrayList<>();
            }
        }
        patternList.add(pattern);
        for (List<String> p : patternList) {
            var screen = ArrayUtil.convertListOfStringsToCharArray(p);
            if (!findMirror(p, screen, false, reflectionListWithoutSmudge, false)) {
                findMirror(p, ArrayUtil.rotateCW(screen), true, reflectionListWithoutSmudge, false);
            }
        }

        return String.valueOf(reflectionListWithoutSmudge.stream().mapToLong(r -> r.rotated ? r.a + 1 : (r.a + 1) * 100L).sum());
    }

    private static boolean findMirror(List<String> p, char[][] screen, boolean rotated, List<Reflection> reflectionList, boolean withSmudge) {
        char[] previous = {};
        for (int i = 0; i < screen.length; i++) {
            var smudgeFixed = false;
            var current = screen[i];
            var equals = false;
            if (withSmudge) {
                var smudgeEquals = compareWithSmudge(current, previous);
                if (smudgeEquals.equals(SmudgeFixer.EQUALS_WITH_SMUDGE)) {
                    smudgeFixed = true;
                    equals = true;
                } else if (smudgeEquals.equals(SmudgeFixer.EQUALS_WITHOUT_SMUDGE)) {
                    equals = true;
                }
            } else {
                equals = Arrays.equals(current, previous);
            }
            if (equals) {
                int y = i - 2;
                for (int x = i + 1; x <= screen.length; x++) {
                    try {
                        var down = screen[x];
                        var up = screen[y];
                        var rowsEquals = false;
                        if (withSmudge) {
                            var smudgeEquals = compareWithSmudge(up, down);
                            if (smudgeEquals.equals(SmudgeFixer.EQUALS_WITH_SMUDGE)) {
                                if (smudgeFixed) {
                                    break;
                                }
                                smudgeFixed = true;
                                rowsEquals = true;
                            } else if (smudgeEquals.equals(SmudgeFixer.EQUALS_WITHOUT_SMUDGE)) {
                                rowsEquals = true;
                            }
                        } else {
                            rowsEquals = Arrays.equals(up, down);
                        }
                        if (rowsEquals) {
                            y--;
                        } else {
                            break;
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                        if (!withSmudge && !smudgeFixed) {
                            reflectionList.add(new Reflection(p, rotated, i - 1, i));
                            return true;
                        } else if (withSmudge && smudgeFixed) {
                            reflectionList.add(new Reflection(p, rotated, i - 1, i));
                            return true;
                        }
                    }
                    if (x == screen.length && !withSmudge && !smudgeFixed) {
                        reflectionList.add(new Reflection(p, rotated, i - 1, i));
                        return true;
                    } else if (x == screen.length && withSmudge && smudgeFixed) {
                        reflectionList.add(new Reflection(p, rotated, i - 1, i));
                        return true;
                    }
                }
            }
            previous = current;
        }
        return false;
    }

    private enum SmudgeFixer {
        EQUALS_WITHOUT_SMUDGE,
        EQUALS_WITH_SMUDGE,
        NOT_EQUALS
    }

    private static SmudgeFixer compareWithSmudge(char[] first, char[] second) {
        if (first.length != second.length) {
            return SmudgeFixer.NOT_EQUALS;
        }
        boolean smudge = false;
        for (int i = 0; i < first.length; i++) {
            if (!(first[i] == second[i])) {
                if (!smudge) {
                    smudge = true;
                } else {
                    return SmudgeFixer.NOT_EQUALS;
                }
            }
        }
        return smudge ? SmudgeFixer.EQUALS_WITH_SMUDGE : SmudgeFixer.EQUALS_WITHOUT_SMUDGE;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        List<String> pattern = new ArrayList<>();
        List<List<String>> patternList = new ArrayList<>();
        for (String line : input) {
            if (line.length() > 0) {
                pattern.add(line);
            } else {
                patternList.add(pattern);
                pattern = new ArrayList<>();
            }
        }
        patternList.add(pattern);
        for (List<String> p : patternList) {
            var screen = ArrayUtil.convertListOfStringsToCharArray(p);
            if (!findMirror(p, screen, false, reflectionListWithSmudge, true)) {
                findMirror(p, ArrayUtil.rotateCW(screen), true, reflectionListWithSmudge, true);
            }
        }

        return String.valueOf(reflectionListWithSmudge.stream().mapToLong(r -> r.rotated ? r.a + 1 : (r.a + 1) * 100L).sum());
    }


}
