import java.util.Arrays;
import java.util.Comparator;

public enum CronPart {
    MINUTE("minute", 0, 59),
    HOUR("hour", 0, 23),
    DAY_OF_MONTH("day of month", 1, 31),
    MONTH("month", 1, 12),
    DAY_OF_WEEK("day of week", 1, 7),
    COMMAND("command", null, null);

    private final String name;
    private final Integer minValue;
    private final Integer maxValue;

    CronPart(String name, Integer minValue, Integer maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static int getMaxNameLength() {
        return Arrays.stream(values()).max(Comparator.comparingInt(cronPart -> cronPart.getName().length())).get()
                .getName().length();
    }

    public String getName() {
        return name;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public boolean isTextual() {
        return this == COMMAND;
    }
}
