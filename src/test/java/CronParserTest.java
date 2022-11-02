import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CronParserTest {

    @Test
    void testExample() {
        SortedMap<CronPart, List<Object>> expectedResult = new TreeMap<>();
        expectedResult.put(CronPart.MINUTE, new ArrayList<>(Arrays.asList(0, 15, 30, 45)));
        expectedResult.put(CronPart.HOUR, new ArrayList<>(Arrays.asList(0)));
        expectedResult.put(CronPart.DAY_OF_MONTH, new ArrayList<>(Arrays.asList(1, 15)));
        expectedResult.put(CronPart.MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
        expectedResult.put(CronPart.DAY_OF_WEEK, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
        expectedResult.put(CronPart.COMMAND, new ArrayList<>(Arrays.asList("/usr/bin/find")));
        assertThat(CronParser.parse("*/15 0 1,15 * 1-5 /usr/bin/find"), Matchers.equalTo(expectedResult));
    }

    @Test
    void testWikipediaExample1() {
        SortedMap<CronPart, List<Object>> expectedResult = new TreeMap<>();
        expectedResult.put(CronPart.MINUTE, new ArrayList<>(Arrays.asList(1)));
        expectedResult.put(CronPart.HOUR, new ArrayList<>(Arrays.asList(0)));
        expectedResult.put(CronPart.DAY_OF_MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)));
        expectedResult.put(CronPart.MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
        expectedResult.put(CronPart.DAY_OF_WEEK, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)));
        expectedResult.put(CronPart.COMMAND, new ArrayList<>(Arrays.asList("printf \"\" > /var/log/apache/error_log")));
        assertThat(CronParser.parse("1 0 * * * printf \"\" > /var/log/apache/error_log"),
                Matchers.equalTo(expectedResult));
    }

    @Test
    void testWikipediaExample2() {
        SortedMap<CronPart, List<Object>> expectedResult = new TreeMap<>();
        expectedResult.put(CronPart.MINUTE, new ArrayList<>(Arrays.asList(45)));
        expectedResult.put(CronPart.HOUR, new ArrayList<>(Arrays.asList(23)));
        expectedResult.put(CronPart.DAY_OF_MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)));
        expectedResult.put(CronPart.MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
        expectedResult.put(CronPart.DAY_OF_WEEK, new ArrayList<>(Arrays.asList(6)));
        expectedResult.put(CronPart.COMMAND, new ArrayList<>(Arrays.asList("/home/oracle/scripts/export_dump.sh")));
        assertThat(CronParser.parse("45 23 * * 6 /home/oracle/scripts/export_dump.sh"),
                Matchers.equalTo(expectedResult));
    }

    @Test
    void testWikipediaExample3() {
        SortedMap<CronPart, List<Object>> expectedResult = new TreeMap<>();
        expectedResult.put(CronPart.MINUTE, new ArrayList<>(Arrays.asList(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50,
                55)));
        expectedResult.put(CronPart.HOUR, new ArrayList<>(Arrays.asList(1, 2, 3)));
        expectedResult.put(CronPart.DAY_OF_MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)));
        expectedResult.put(CronPart.MONTH, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
        expectedResult.put(CronPart.DAY_OF_WEEK, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)));
        expectedResult.put(CronPart.COMMAND, new ArrayList<>(Arrays.asList("echo hello world")));
        assertThat(CronParser.parse("*/5 1,2,3 * * * echo hello world"), Matchers.equalTo(expectedResult));
    }

    @Test
    void testInvalidCronExpression() {
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> CronParser.parse("invalid cron expression"));
        assertThat(exception.getMessage(), Matchers.equalTo("Invalid Cron expression: invalid cron expression"));
    }

    @Test
    void testInvalidCronExpressionWithUnrecognisedPartExpression() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CronParser.parse(
                "an invalid 6 part cron expression"));
        assertThat(exception.getMessage(), Matchers.equalTo("Unrecognised part expression for minute: an"));
    }

    @Test
    void testInvalidCronExpressionWithRangeOutsideAllowed() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CronParser.parse(
                "1-60 * * * * command"));
        assertThat(exception.getMessage(), Matchers.equalTo("1-60 outside allowed range for minute"));
    }

    @Test
    void testInvalidCronExpressionWithInvalidRange() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CronParser.parse(
                "* 10-5 * * * command"));
        assertThat(exception.getMessage(), Matchers.equalTo("Invalid range 10-5 for hour"));
    }
}
