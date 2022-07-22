package project;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Application {
    private static final Set<Integer> values = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
    private static final Shift shift = new Shift();
    private static final Scanner scanner = new Scanner(System.in);
    private static String[] dayOfWeekArr;
    private static int[] dayOfWeekIntArr;
    private static int[] shiftStartTimeArr;
    private static int[] shiftEndTimeArr;
    private static int[] shiftLengthArr;
    private static int shiftNum;

    public static void main(String args[]) {
        Application app = new Application();
        app.run();
    }

    private static void run() {
        try {

            System.out.println("*************************************************************************************");
            System.out.println("*************************************************************************************");
            System.out.println("**************************** Nursing Weekly Work Calendar ***************************");
            System.out.println("*************************************************************************************");
            System.out.println("*************************************************************************************");
            System.out.println();

            System.out.println("How many shifts are you working this week?");

            shift.setNumShiftsPerWeek(Integer.parseInt(scanner.nextLine()));

            shiftNum = shift.getNumShiftsPerWeek();
            dayOfWeekArr = new String [shiftNum];
            dayOfWeekIntArr = new int [shiftNum];
            shiftStartTimeArr = new int [shiftNum];
            shiftEndTimeArr = new int [shiftNum];
            shiftLengthArr = new int [shiftNum];

            if (shift.getNumShiftsPerWeek() == 0) {
                System.out.println("Have a fun, relaxing week!");
                System.exit(0);
            }

            System.out.println();

            int i = 0;

            do {

                System.out.println("What day do you work?\n(1)\tMonday\n(2)\tTuesday\n(3)\tWednesday\n(4)\tThursday\n(5)\tFriday\n(6)\tSaturday\n(7)\tSunday");

                int dayOfWeek = Integer.parseInt(scanner.nextLine());
                dayOfWeekIntArr[i] = dayOfWeek;

                if (values.contains(dayOfWeek)) {
                    dayOfWeekArr[i] = shift.convertDayOfWeek(dayOfWeek); //VS dayOfWeekArr[i] = dayOfWeek
                } else {
                    System.out.println("Invalid input, please try again.");
                    break;
                }
                // dayOfWeek is int, passed to setDayOfWeek and a string is returned


                System.out.println();

                System.out.println("Enter the starting time for your shift using 24 hour format.");
                int startTimeInput = Integer.parseInt(scanner.nextLine());
                // no setter for shiftStart time because setters are void, can't be passed into array
                shiftStartTimeArr[i] = startTimeInput;

                System.out.println();

                System.out.println("What is the length for your shift in hours?");
                int shiftLengthInput = Integer.parseInt(scanner.nextLine());
                shiftLengthArr[i] = shiftLengthInput;

                System.out.println();

                i++;

            }
            while (i <= shift.getNumShiftsPerWeek() - 1);

            // Generate shiftEndTime array
            shift.makeShiftEndTimeArray(shiftEndTimeArr, shiftStartTimeArr, shiftLengthArr);
        } catch (NumberFormatException e) {
            System.out.println("Oops, you entered a non-numerical value.");
        }
    // shift.consolePrint(dayOfWeekIntArr, shiftStartTimeArr, shiftLengthArr, shiftEndTimeArr);
        System.out.println(Arrays.toString(shiftStartTimeArr));
        System.out.println(Arrays.toString(shiftEndTimeArr));

        final Object[][] table = new Integer[2][shiftNum]; // 2 rows , shifts-1 columns

        for (int i = 0; i < shiftNum; i++) {
            table[0][i] = shiftStartTimeArr[i];
        }

        for (int i = 0; i < shiftNum; i++) {
            table[1][i] = shiftEndTimeArr[i]; // row, col
        }

        System.out.println("Monday\t\tTuesday\t\tWednesday\t\tThursday\t\tFriday\t\tSaturday\t\tSunday");
        System.out.println();
        String element = "%d";
        String total;

        for (final Object[] row : table) {
                total = element + " %d";
                System.out.format(total + "\n", row);
         }

    shift.calendarOutput(dayOfWeekIntArr, shiftStartTimeArr, shiftLengthArr, shiftEndTimeArr);


    }
}