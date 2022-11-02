import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class ParsedCronFormatterTest {
    @Test
    void testExample() {
        SortedMap<CronPart, List<Object>> parsedCron = new TreeMap<>();
        parsedCron.put(CronPart.MINUTE, new ArrayList<>(Arrays.asList(0, 15, 30, 45)));
        parsedCron.put(CronPart.HOUR, new ArrayList<>(Arrays.asList(0)));
        parsedCron.put(CronPart.DAY_OF_MONTH, new ArrayList<>(Arrays.asList(1, 15)));
        parsedCron.put(CronPart.MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
        parsedCron.put(CronPart.DAY_OF_WEEK, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
        parsedCron.put(CronPart.COMMAND, new ArrayList<>(Arrays.asList("/usr/bin/find")));

        StringBuilder sb = new StringBuilder();
        sb.append("minute        0 15 30 45\n");
        sb.append("hour          0\n");
        sb.append("day of month  1 15\n");
        sb.append("month         1 2 3 4 5 6 7 8 9 10 11 12\n");
        sb.append("day of week   1 2 3 4 5\n");
        sb.append("command       /usr/bin/find\n");
        String expectedResult = sb.toString();

        assertThat(ParsedCronFormatter.format(parsedCron), Matchers.equalTo(expectedResult));
    }
}
