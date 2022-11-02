/**
 * Main entry point
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid command: No Cron string was supplied.");
            System.out.println("Syntax: CronParser \"<Cron string>\"");
        } else {
            System.out.print(getFormattedParsedCron(args[0]));
        }
    }

    public static String getFormattedParsedCron(String cron) {
        return ParsedCronFormatter.format(CronParser.parse(cron));
    }
}
