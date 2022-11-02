import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

public class ParsedCronFormatter {
    public static String format(SortedMap<CronPart, List<Object>> partToValues) {
        StringBuilder sb = new StringBuilder();
        int partNameLength = CronPart.getMaxNameLength() + 2;
        partToValues.forEach((key, value1) -> {
            sb.append(String.format("%-" + partNameLength + "s", key.getName()));

            Iterator<Object> iterator = value1.iterator();
            while (iterator.hasNext()) {
                Object value = iterator.next();
                sb.append(value);
                if (iterator.hasNext()) {
                    sb.append(" ");
                } else {
                    sb.append("\n");
                }
            }
        });
        return sb.toString();
    }
}
