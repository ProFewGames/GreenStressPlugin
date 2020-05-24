package xyz.ufactions.libs;

import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class F {

    public static String main(String module, String body) {
        return C.mHead + module + ">> " + C.mBody + body;
    }

    public static String help(String help, String desc) {
        return C.mHead + help + " " + C.mBody + desc;
    }

    public static String error(String module, String body) {
        return main(module, C.cRed + body);
    }

    public static String list(String string) {
        return "  " + C.mBody + "➥ " + C.cYellow + string;
    }

    public static String noPermission() {
        return C.cRed + "No Permission.";
    }

    public static String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String elem(String elem) {
        return C.mElem + elem + ChatColor.RESET + C.mBody;
    }

    public static String oo(boolean val) {
        return (val ? C.cGreen + "on" : C.cRed + "off") + C.mBody;
    }

    public static String ed(boolean val) {
        return (val ? C.cGreen + "enabled" : C.cRed + "disabled") + C.mBody;
    }

    public static String matchCase(Set<String> set, String starter) {
        for (String string : set) {
            if (string.equalsIgnoreCase(starter)) {
                return string;
            }
        }
        return starter;
    }

    public static String concatenate(String splitter, String[] array) {
        return concatenate(0, splitter, array);
    }

    public static String concatenate(int index, String splitter, String[] array) {
        String string = "";
        for (int i = index; i < array.length; i++) {
            if (string.isEmpty())
                string = array[i];
            else
                string += splitter + array[i];
        }
        return string;
    }

    public static String format(double paramDouble) {
        NumberFormat localNumberFormat = NumberFormat.getInstance(Locale.ENGLISH);

        localNumberFormat.setMaximumFractionDigits(2);

        localNumberFormat.setMinimumFractionDigits(0);

        return localNumberFormat.format(paramDouble);
    }

    public static String formatMoney(double paramDouble) {
        if (paramDouble < 1000.0D) {
            return format(paramDouble);
        }
        if (paramDouble < 1000000.0D) {
            return NumberFormat.getInstance().format(paramDouble);
        }
        if (paramDouble < 1.0E9D) {
            return format(paramDouble / 1000000.0D) + " M";
        }
        if (paramDouble < 1.0E12D) {
            return format(paramDouble / 1.0E9D) + " B";
        }
        if (paramDouble < 1.0E15D) {
            return format(paramDouble / 1.0E12D) + " T";
        }
        if (paramDouble < 1.0E18D) {
            return format(paramDouble / 1.0E15D) + " Q";
        }
        long l = (long) paramDouble;
        return String.valueOf(l);
    }
}
