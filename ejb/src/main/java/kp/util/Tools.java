package kp.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The tools.
 */
public interface Tools {
    /**
     * Formats the hash code.
     *
     * @param object the object
     * @return the formatted hash code
     */
    static String hashCodeFormatted(Object object) {

        String msg = "%08X".formatted(object.hashCode());
        if (msg.length() == 8) {
            msg = "%s %s %s %s".formatted(msg.substring(0, 2), msg.substring(2, 4),
                    msg.substring(4, 6), msg.substring(6, 8));
        }
        return msg;
    }

    /**
     * Formats the number.
     *
     * @param number the number
     * @return the formatted text
     */
    static String formatNumber(long number) {

        final NumberFormat numberFormat = NumberFormat.getInstance();
        if (!(numberFormat instanceof DecimalFormat decimalFormat)) {
            // failed
            return "?";
        }
        final DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('\'');
        decimalFormat.setDecimalFormatSymbols(dfs);
        return decimalFormat.format(number);
    }

    /**
     * Gets the text list.
     *
     * @return the textList
     */
    static List<String> getTextList() {
        return IntStream.concat(
                        IntStream.rangeClosed("A".codePointAt(0), "Z".codePointAt(0)),
                        IntStream.rangeClosed("a".codePointAt(0), "z".codePointAt(0)))
                .mapToObj(num -> Character.toString((char) num)).toList();
    }

}