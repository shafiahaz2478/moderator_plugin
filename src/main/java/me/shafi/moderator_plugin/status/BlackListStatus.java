package me.shafi.moderator_plugin.status;

public class BlackListStatus {
    private final boolean banned;
    private final String reason;

    public BlackListStatus(boolean banned, String reason) {
        this.banned = banned;
        this.reason = reason;
    }

    public boolean isBanned() {
        return banned;
    }

    public String getReason() {
        return reason;
    }

}
