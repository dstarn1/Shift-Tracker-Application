package project;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Shift {
    private int numShiftsPerWeek;
    private int shiftEndTime;     // Derived value
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
    String monthYear = sdf.format(new Date());

    public int getNumShiftsPerWeek() {
        return numShiftsPerWeek;
    }

    public void setNumShiftsPerWeek(int numShiftsPerWeek) {
        this.numShiftsPerWeek = numShiftsPerWeek;
    }

    public String convertDayOfWeek(int dayOfWeek) {
        String day = "";

        if (dayOfWeek == 1) {
            day = "Monday";
        } else if (dayOfWeek == 2) {
            day = "Tuesday";
        } else if (dayOfWeek == 3) {
            day = "Wednesday";
        } else if (dayOfWeek == 4) {
            day = "Thursday";
        } else if (dayOfWeek == 5) {
            day = "Friday";
        } else if (dayOfWeek == 6) {
            day = "Saturday";
        } else if (dayOfWeek == 7) {
            day = "Sunday";
        }
        return day;
    }

    public int getShiftEndTime(int shiftStartTime, int shiftLength) {
        shiftLength = shiftLength * 100;
        if ((shiftStartTime + shiftLength) > 2400) {
            shiftEndTime = (shiftStartTime + shiftLength) - 2400;

        } else if ((shiftStartTime + shiftLength) < 2400) {
            shiftEndTime = shiftStartTime + shiftLength;
        }
        return shiftEndTime;
    }

    public int[] makeShiftEndTimeArray(int[] shiftEndTimeArr, int[] shiftStartTimeArr, int[] shiftLengthArr) {

        for (int i = 0; i <= getNumShiftsPerWeek() - 1; i++) {
            shiftEndTimeArr[i] = getShiftEndTime(shiftStartTimeArr[i], shiftLengthArr[i]);
        }
        return shiftEndTimeArr;
    }

    public String shiftOutput(int[] dayOfWeekIntArr, int[] shiftStartTimeArr, int[] shiftLengthArr, int[] shiftEndTimeArr) {
        String lineA = "";
        String lineB;

        for (int i = 0; i < getNumShiftsPerWeek(); i++) {
            lineB = shiftStartTimeArr[i] + "-" + shiftEndTimeArr[i] + "\t\t\t";
            lineA = lineA + lineB;
        }
        return lineA;
    }
    // Need to break up this by Day and space out to fit the outline of the table.

    public String consolePrint(int[] dayOfWeekIntArr, int[] shiftStartTimeArr, int[] shiftLengthArr, int[] shiftEndTimeArr) {
        System.out.println("************************************************************************************************************************");
        System.out.println("****************************************** This Week's Work Schedule ***************************************************");
        System.out.println("************************************************************************************************************************");
        System.out.println(monthYear);
        System.out.println("Monday\t\t\t\tTuesday\t\t\t\tWednesday\t\t\tThursday\t\t\tFriday\t\t\t\tSaturday\t\t\tSunday");
        System.out.println();
        System.out.println(shiftOutput(dayOfWeekIntArr, shiftStartTimeArr, shiftLengthArr, shiftEndTimeArr) + " ");
        return "";
    }

    public String fileOutput(int[] dayOfWeekIntArr, int[] shiftStartTimeArr, int[] shiftLengthArr, int[] shiftEndTimeArr) {
        Scanner scanner = new Scanner(System.in);

        String filePath;

        String out = "";

        System.out.println();
        System.out.println("Would you like to save your work to a file?\n(1) Yes\n(2) No");
        int saveWork = Integer.parseInt(scanner.nextLine());

        // must have log file created first

        if (saveWork == 1) {
            System.out.println("Please enter the path where you would like to save your file:");
            filePath = scanner.nextLine();
            File file = new File(filePath);

            try (PrintWriter dataOutput = new PrintWriter(file)) {

                PrintStream originalStream = System.out; // Bind original output stream, console, to variable so we can reassign

                System.setOut(new PrintStream(file));

                dataOutput.println(consolePrint(dayOfWeekIntArr, shiftStartTimeArr, shiftLengthArr, shiftEndTimeArr));

                System.setOut(originalStream); // Rebind to console for output to user.

                System.out.println("Weekly shift table has been written to designated path!");

            } catch (FileNotFoundException e) {
                System.err.println("File does not exist.");
            }
        } else {
            // System.setOut(System.out);
            System.out.println("Thanks for using the program, exiting now..");
            System.exit(1);
        }

        return out;
    }
}
