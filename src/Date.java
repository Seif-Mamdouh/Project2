import java.util.Calendar;


/**
 * Represents a particular date containing a day, month and year.
 *
 * @author Seifeldeen Mohamed
 */

public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int MONTHS_IN_YEAR = 12;

    public static final int DAYS_IN_FEBUARY_IN_LEAP_YEAR = 29;

    public static final int FEBUARY_MONTH_NUMBER = 2;


    private int year; //the year componenent in date
    private int month; //the month componenent in date
    private int day; //the day componenent in date

    /**
     * Default Constructor that sets year month date
     *
     * @param year  year of date
     * @param month month of date
     * @param day   day of date
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Get year in which event will occur
     *
     * @return year of event
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Get month in which event will occur
     *
     * @return month of event
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Get day in which event will occur
     *
     * @return day of event
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Parse a date string in the format "month/day/year" and return a Date
     * object.
     *
     * @param dateStr the date string to parse
     * @return a Date object representing the parsed date
     * @throws IllegalArgumentException if the input is not in a valid format
     */
    public static Date parseDate(String dateStr) {
        String[] dateSplit = dateStr.split("/");
        int dateExpectedComponents = 3;
        if (dateSplit.length != dateExpectedComponents) {
            throw new IllegalArgumentException(
                    "Invalid date format: " + dateStr);
        }
        ;

        int month, day, year;

        try {
            int monthIndex = 0, dayIndex = 1, yearIndex = 2;
            month = Integer.parseInt(dateSplit[monthIndex]);
            day = Integer.parseInt(dateSplit[dayIndex]);
            year = Integer.parseInt(dateSplit[yearIndex]);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid date format: " + dateStr);
        }

        return new Date(year, month, day);
    }


    private static final int[] dayInMonthConstant =
            {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    private static int min_month = 1;
    private static int min_year = 1900;

    /**
     * Check if the date is in a valid format (mm/dd/yyyy).
     *
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        if (this.month < min_month || this.month > MONTHS_IN_YEAR || this.year < min_year) {
            return false;
        }

        int daysInTheCurrentMonth = dayInMonthConstant[this.month];

        if (isLeapYear(year) && this.month == FEBUARY_MONTH_NUMBER) {
            daysInTheCurrentMonth = DAYS_IN_FEBUARY_IN_LEAP_YEAR;
        }

        return this.day >= 1 && this.day <= daysInTheCurrentMonth;
    }

    /**
     * Check if the date is more than 6 months away from the current date.
     *
     * @return true if the date is more than 6 months away, false otherwise
     */
    public boolean isMoreThanSixMonthsAway() {
        Calendar todayDate = Calendar.getInstance();
        Calendar targetDate = Calendar.getInstance();
        targetDate.set(year, month - 1, day); //Calendar month is 0-indexed

        // Calculate the difference in months
        long monthsDifference = this.getMonthDifference(todayDate, targetDate);

        int mostMonthDifferenceBeforeReturningTrue = 6;
        return monthsDifference >= mostMonthDifferenceBeforeReturningTrue;
    }


    /**
     * Check if the date is a future date (not equal to or before the current
     * date).
     *
     * @return true if the date is a future date, false otherwise
     */
    public boolean isFutureDate() {
        Calendar todayDate = Calendar.getInstance();
        Calendar targetDate = Calendar.getInstance();
        targetDate.set(year, month - 1, day);

        return !targetDate.before(todayDate);
    }

    /**
     * Check if year is in leap year
     *
     * @param year the year to check
     * @return True if leap year, false otherwise
     */
    private boolean isLeapYear(int year) {
        // Check if the year is a leap year (divisible by 4, not divisible by
        // 100, or divisible by 400)
        return (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) ||
                (year % QUATERCENTENNIAL == 0);
    }

    /**
     * Check how many months apart two dates are
     *
     * @param givenDate first date
     * @param endDate   second date
     * @return how many months the second date is ahead of the first date
     */
    private long getMonthDifference(Calendar givenDate, Calendar endDate) {
        int startYear = givenDate.get(Calendar.YEAR);
        int startMonth = givenDate.get(Calendar.MONTH);
        int endYear = endDate.get(Calendar.YEAR);
        int endMonth = endDate.get(Calendar.MONTH);

        return (endYear - startYear) * MONTHS_IN_YEAR + (endMonth - startMonth);
    }



    /**
     * Compare dates by their year then month then day
     *
     * @param otherDate the date to be compared.
     * @return int smaller than 0 if this date is before otherDate, greater
     * than 0 if this date is after otherDate and 0 if they are equal
     * after otherDate, and 0 if they are the same date
     */
    public int compareTo(Date otherDate) {

        if (this.year != otherDate.getYear()) {
            return Integer.compare(this.year, otherDate.getYear());
        }

        if (this.month != otherDate.getMonth()) {
            return Integer.compare(this.month, otherDate.getMonth());
        }

        return Integer.compare(this.day, otherDate.getDay());
    }


    /**
     * Represent date in month/day/year format
     *
     * @return formatted date
     */
    @Override
    public String toString() {
        return String.format("%s/%s/%s", this.month, this.day, this.year);
    }

    /**
     * Unit tests for the date's isValid() and compareTo() method
     *
     * @param args not used
     */
    public static void main(String[] args) {
        // Test cases for your Date class
        Date date1 = new Date(800, 9, 12);
        Date date2 = new Date(2023, 9, 11);
        Date date3 = new Date(2024, 4, 29);
        Date date4 = new Date(2024, 2, 29);
        Date date5 = new Date(2023, 13, 12);
        Date date6 = new Date(2023, 0, 12);
        Date date7 = new Date(2024, 4, 22);
        Date date8 = new Date(-2023, 12, 2);

        // Test the isValid method
        assert  date1.isValid();
        assert date2.isValid();
        assert date3.isValid();
        assert date4.isValid();
        assert date5.isValid();
        assert date6.isValid();
        assert date7.isValid();
        assert date8.isValid();
    }
};