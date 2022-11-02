import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class MainIT {
    @Test
    void testExample() {
        StringBuilder sb = new StringBuilder();
        sb.append("minute        0 15 30 45\n");
        sb.append("hour          0\n");
        sb.append("day of month  1 15\n");
        sb.append("month         1 2 3 4 5 6 7 8 9 10 11 12\n");
        sb.append("day of week   1 2 3 4 5\n");
        sb.append("command       /usr/bin/find\n");
        String expectedResult = sb.toString();
        String actualResult = Main.getFormattedParsedCron("*/15 0 1,15 * 1-5 /usr/bin/find");
        assertThat(actualResult, Matchers.equalTo(expectedResult));
    }
}
