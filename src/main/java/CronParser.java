import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cron parser
 */
public class CronParser {
    public static SortedMap<CronPart, List<Object>> parse(String cron) {
        SortedMap<CronPart, List<Object>> partToValues = new TreeMap<>();

        Matcher matcher =
                Pattern.compile("^([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+(.*?)\\s*$")
                        .matcher(cron);
        if (matcher.find()) {
            int partIndex = 0;
            for (int groupIndex = 1; groupIndex <= 6; groupIndex++) {
                String part = matcher.group(groupIndex);
                CronPart cronPart = CronPart.values()[partIndex];
                List<Object> values = cronPart.isTextual() ? Arrays.asList(part) : parsePart(part, cronPart);
                partToValues.put(cronPart, values);
                partIndex++;
            }
            return partToValues;
        } else {
            throw new IllegalArgumentException(String.format("Invalid Cron expression: %s", cron));
        }
    }

    private static List<Object> parsePart(String part, CronPart cronPart) {
        List<Object> values = new ArrayList<>();

        // Check for numeric: xy... where xy... are digits
        Matcher matcher = Pattern.compile("^\\d+$").matcher(part);
        if (matcher.find()) {
            addValue(Integer.parseInt(part), values, part, cronPart);
            return values;
        }

        // Check for wildcard: either * or */x... where x is a positive integer
        matcher = Pattern.compile("^\\*(?:\\/([1-9]\\d*))?$").matcher(part);
        if (matcher.find()) {
            String group = matcher.group(1);
            int increment;
            if (group == null) {
                increment = 1;
            } else {
                increment = Integer.parseInt(group);
            }
            for (int value = cronPart.getMinValue(); value <= cronPart.getMaxValue(); value += increment) {
                values.add(value);
            }
            return values;
        }

        // Check for comma-separated: x,y,... where x,y,... are integers
        matcher = Pattern.compile("^(\\d+,)+\\d+$").matcher(part);
        if (matcher.find()) {
            String[] valuesAsStrings = part.split(",");
            Arrays.stream(valuesAsStrings).forEach(value -> {
                addValue(Integer.parseInt(value), values, part, cronPart);
            });
            return values;
        }

        // Check for range: x-y,... where x,y are digits
        matcher = Pattern.compile("^(\\d+)-(\\d+)$").matcher(part);
        if (matcher.find()) {
            int minValue = Integer.parseInt(matcher.group(1));
            int maxValue = Integer.parseInt(matcher.group(2));
            if (minValue > maxValue) {
                throw new IllegalArgumentException(String.format("Invalid range %s for %s", part,
                        cronPart.getName()));
            }
            for (int value = minValue; value <= maxValue; value++) {
                addValue(value, values, part, cronPart);
            }
            return values;
        }

        throw new IllegalArgumentException(String.format("Unrecognised part expression for %s: %s",
                cronPart.getName(), part));
    }

    private static void addValue(int value, List<Object> values, String part, CronPart cronPart) {
        if (value >= cronPart.getMinValue() && value <= cronPart.getMaxValue()) {
            values.add(value);
        } else {
            throw new IllegalArgumentException(String.format("%s outside allowed range for %s", value,
                    cronPart.getName()));
        }
    }
}