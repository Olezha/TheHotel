package ua.olezha.hotel.util;

public class StringUtils {

    public static String cleanNumbersString(String s) {
        int lastDotPosition = s.lastIndexOf('.'), lastCommaPosition = s.lastIndexOf(',');
        int decimalSeparatorPosition = lastDotPosition > lastCommaPosition ? lastDotPosition : lastCommaPosition;
        StringBuilder cleanNumbersString = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++)
            if (i == decimalSeparatorPosition || Character.isDigit(s.charAt(i)) || (i == 0 && s.charAt(i) == '-'))
                cleanNumbersString.append(s.charAt(i));
        return cleanNumbersString.toString();
    }
}
