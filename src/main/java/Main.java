/**
 * Main entry point
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("ERROR: Invalid command.");
            System.out.println();
            printSyntax();
        } else {
            try {
                System.out.print(getFormattedParsedCron(args[0]));
            } catch (IllegalArgumentException iae) {
                System.out.println("ERROR parsing Cron expression.");
                System.out.println(iae.getMessage());
                System.out.println();
                printSyntax();
            }
        }
    }

    public static String getFormattedParsedCron(String cron) {
        return ParsedCronFormatter.format(CronParser.parse(cron));
    }

    public static void printSyntax() {
        System.out.println("Syntax: java -jar <jar file path> \"<Cron string>\"");
        System.out.println("Example: java -jar target/CronParser-1.0-SNAPSHOT.jar \"*/15 0 1,15 * 1-5 /usr/bin/find\"");
    }
}
