package me.shafi.moderator_plugin.status;

public class MuteStatus {
    private final boolean muted;
    private final String reason;

    public MuteStatus(boolean muted, String reason) {
        this.muted = muted;
        this.reason = reason;
    }

    public boolean isMuted() {
        return muted;
    }

    public String getReason() {
        return reason;
    }
}
