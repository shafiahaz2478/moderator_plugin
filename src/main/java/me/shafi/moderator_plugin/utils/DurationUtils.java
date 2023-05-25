package me.shafi.moderator_plugin.utils;

public class DurationUtils {
    public static long parseDuration(String durationString) {
        long duration = 0;
        try {
            char unit = durationString.charAt(durationString.length() - 1);
            int amount = Integer.parseInt(durationString.substring(0, durationString.length() - 1));

            switch (unit) {
                case 's':
                    duration = amount * 1000L;
                    break;
                case 'm':
                    duration = amount * 60000L;
                    break;
                case 'h':
                    duration = amount * 3600000L;
                    break;
                case 'd':
                    duration = amount * 86400000L;
                    break;
            }
        } catch (NumberFormatException e) {
            // Invalid duration format
        }

        return duration;
    }

    public static String formatDuration(long duration) {
        long seconds = duration / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        StringBuilder durationString = new StringBuilder();

        if (days > 0) {
            durationString.append(days).append(" day");
            if (days > 1) {
                durationString.append("s");
            }
            durationString.append(" ");
        }

        if (hours > 0) {
            durationString.append(hours % 24).append(" hour");
            if (hours % 24 > 1) {
                durationString.append("s");
            }
            durationString.append(" ");
        }

        if (minutes > 0) {
            durationString.append(minutes % 60).append(" minute");
            if (minutes % 60 > 1) {
                durationString.append("s");
            }
            durationString.append(" ");
        }

        if (seconds > 0) {
            durationString.append(seconds % 60).append(" second");
            if (seconds % 60 > 1) {
                durationString.append("s");
            }
            durationString.append(" ");
        }

        return durationString.toString().trim();
    }

}
