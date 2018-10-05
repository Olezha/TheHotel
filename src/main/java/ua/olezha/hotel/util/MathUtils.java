package ua.olezha.hotel.util;

/**
 * @author Oleh Shklyar
 */

public class MathUtils {

    /**
     * Convention:
     * - if there is one or more dot or comma,
     * only the last will be interpreted as the separator for the decimal places;
     * - all characters that doesn't represent digit or additional information for number
     * will be skipped.
     */
    public static String cleanNumber(String number) {
        if (number == null || number.isEmpty())
            return number;

        int lastDotPosition = number.lastIndexOf('.');
        int lastCommaPosition = number.lastIndexOf(',');
        int decimalSeparatorPosition =
                lastDotPosition > lastCommaPosition ? lastDotPosition : lastCommaPosition;

        StringBuilder cleanNumber = new StringBuilder(number.length());

        int i = number.charAt(0) == '-' ? 1 : 0;
        if (i == 1)
            cleanNumber.append('-');

        boolean containsDigit = false;

        for (; i < number.length(); i++)
            if (i == decimalSeparatorPosition)
                cleanNumber.append('.');
            else if (Character.isDigit(number.charAt(i))) {
                containsDigit = true;
                cleanNumber.append(number.charAt(i));
            }

        String cleanNumberString = cleanNumber.toString();
        if (containsDigit)
            return cleanNumberString;

        return "";
    }
}
