package com.mustafafidan.rahatlaticisesler.model;

import com.google.android.exoplayer2.SimpleExoPlayer;

public class PlayerItem {

    int soundId;

    SimpleExoPlayer player;

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public void setPlayer(SimpleExoPlayer player) {
        this.player = player;
    }
}
