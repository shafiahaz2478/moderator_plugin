package me.shafi.moderator_plugin.status;

public class BanStatus {

    private final boolean banned;
    private final String reason;
    private final long duration;

    public BanStatus(boolean banned, String reason , long duration) {
        this.banned = banned;
        this.reason = reason;
        this.duration = duration;
    }

    public boolean isBanned() {
        return banned;
    }

    public String getReason() {
        return reason;
    }

    public long getDuration(){
        return duration;
    }
}
