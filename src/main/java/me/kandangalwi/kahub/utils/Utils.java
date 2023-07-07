package me.kandangalwi.kahub.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class Utils {

    /**
     * Colorizes string
     *
     * @param color the string to colorize
     * @return colorized string
     */
    public String colorize(String color) {
        return ChatColor.translateAlternateColorCodes('&', color);
    }
}
