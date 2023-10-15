import java.util.Calendar;

/**
 * Represents a particular date containing a day, month and year.
 *
 * @author Seifeldeen Mohamed
 */

public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUADRICENTENNIAL = 400;
    public static final int MONTHS_IN_YEAR = 12;
    public static final int DAYS_IN_FEBRUARY_IN_LEAP_YEAR = 29;
    public static final int FEBRUARY_MONTH_NUMBER = 2;


    private final int year; //the year component in date
    private final int month; //the month component in date
    private final int day; //the day component in date

    /**
     * Default Constructor that sets year month date
     *
     * @param year  year of date
     * @param month month of date
     * @param day   day of date
     */
    public Date(int day, int month, int year) {
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

        return new Date(day, month, year);
    }


    private static final int[] dayInMonthConstant =
            {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    private final static int min_month = 1;
    private final static int min_year = 1900;

    /**
     * Check if the date is in a valid format (mm/dd/yyyy).
     *
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        if (this.month < min_month || this.month > MONTHS_IN_YEAR ||
            this.year < min_year) {
            return false;
        }

        int daysInTheCurrentMonth = dayInMonthConstant[this.month];

        if (isLeapYear(year) && this.month == FEBRUARY_MONTH_NUMBER) {
            daysInTheCurrentMonth = DAYS_IN_FEBRUARY_IN_LEAP_YEAR;
        }

        return this.day >= 1 && this.day <= daysInTheCurrentMonth;
    }

    /**
     * Gets age of person if they were born on this date
     *
     * @return age of person in years
     */
    public int getAge() {
        Calendar todayDate = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(year, month - 1, day); //Calendar month is 0-indexed

        // Calculate the difference in months
        long monthsDifference = this.getYearDifference(birthDate, todayDate);
        return (int) monthsDifference;

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
               (year % QUADRICENTENNIAL == 0);
    }

    /**
     * Returns difference in years from two dates
     *
     * @param givenDate start date
     * @param endDate   end date
     * @return difference in years rounded down to the smallest year
     * (how age works)
     */
    private long getYearDifference(Calendar givenDate, Calendar endDate) {
        int startYear = givenDate.get(Calendar.YEAR);
        int startMonth = givenDate.get(Calendar.MONTH);
        int startDay = givenDate.get(Calendar.DATE);

        int endYear = endDate.get(Calendar.YEAR);
        int endMonth = endDate.get(Calendar.MONTH);
        int endDay = endDate.get(Calendar.DATE);


        long yearDifference = endYear - startYear;
        if (endMonth < startMonth) {
            yearDifference--;
        }
        else if (endMonth == startMonth && endDay < startDay) {
            yearDifference--;
        }

        return yearDifference;
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
     * Compare two dates' equality
     *
     * @param other other date to compare to this one
     * @return true if year, month, and day are same, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Date otherDate)) {
            return false;
        }
        return this.compareTo(otherDate) == 0;
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
     * Unit tests for the date's isValid()
     *
     * @param args not used
     */
    public static void main(String[] args) {
        // Test cases for your Date class
        Date date1 = new Date(29, 2, 2024);

        // Test the isValid method
        boolean d = date1.isValid();


        System.out.println(d);
    }
}