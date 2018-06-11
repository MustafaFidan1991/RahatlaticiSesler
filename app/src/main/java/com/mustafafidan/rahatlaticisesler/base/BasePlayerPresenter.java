package com.mustafafidan.rahatlaticisesler.base;

import android.net.Uri;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mustafafidan.rahatlaticisesler.model.PlayerItem;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BasePlayerPresenter<V extends BaseView> extends BasePresenter<V> {

    protected Disposable subscription = null;

    protected ArrayList<PlayerItem> playerItems = new ArrayList<>();

    public BasePlayerPresenter(V view) {
        super(view);
    }

    protected void deletePlayer(int soundId){
        for(PlayerItem playerItem : playerItems){
            if(playerItem.getSoundId() == soundId){

                Observable<SimpleExoPlayer> observable = Observable.create(emitter -> {
                    playerItem.getPlayer().setPlayWhenReady(false);
                    playerItem.getPlayer().stop();
                    playerItem.getPlayer().seekTo(0);
                    emitter.onNext(playerItem.getPlayer());
                    emitter.onComplete();
                });
                subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(simpleExoPlayer -> {});
                playerItems.remove(playerItem);
                return;
            }
        }
    }

    public boolean isPlayerNull(int soundId){
        for(PlayerItem playerItem : playerItems){
            if(playerItem.getSoundId() == soundId){
                return false;
            }
        }
        return true;
    }

    public boolean isPlaying(int soundId){
        for(PlayerItem playerItem : playerItems){
            if(playerItem.getSoundId() == soundId){
                return playerItem.getPlayer().getPlayWhenReady();
            }
        }
        return false;
    }

    public void pauseMedia(int soundId,ExoSuccessListener successListener){
        for(PlayerItem playerItem : playerItems){
            if(playerItem.getSoundId() == soundId){
                Observable<SimpleExoPlayer> observable = Observable.create(emitter -> {

                    playerItem.getPlayer().addListener(new Player.EventListener() {
                        @Override
                        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                        }

                        @Override
                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                        }

                        @Override
                        public void onLoadingChanged(boolean isLoading) {

                        }

                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            if(!playWhenReady){

                                emitter.onNext(playerItem.getPlayer());
                                emitter.onComplete();
                                playerItem.getPlayer().removeListener(this);
                            }
                        }

                        @Override
                        public void onRepeatModeChanged(int repeatMode) {

                        }

                        @Override
                        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                        }

                        @Override
                        public void onPlayerError(ExoPlaybackException error) {

                        }

                        @Override
                        public void onPositionDiscontinuity(int reason) {

                        }

                        @Override
                        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                        }

                        @Override
                        public void onSeekProcessed() {

                        }
                    });


                    playerItem.getPlayer().setPlayWhenReady(false);
                });

                subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(simpleExoPlayer -> {
                    successListener.onSuccess();
                });
            }
        }
    }

    public void resumeMedia(int soundId,ExoSuccessListener successListener){
        for(PlayerItem playerItem : playerItems){
            if(playerItem.getSoundId() == soundId){
                Observable<SimpleExoPlayer> observable = Observable.create(emitter -> {

                    playerItem.getPlayer().addListener(new Player.EventListener() {
                        @Override
                        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                        }

                        @Override
                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                        }

                        @Override
                        public void onLoadingChanged(boolean isLoading) {

                        }

                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            if(playWhenReady){

                                emitter.onNext(playerItem.getPlayer());
                                emitter.onComplete();
                                playerItem.getPlayer().removeListener(this);
                            }
                        }

                        @Override
                        public void onRepeatModeChanged(int repeatMode) {

                        }

                        @Override
                        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                        }

                        @Override
                        public void onPlayerError(ExoPlaybackException error) {

                        }

                        @Override
                        public void onPositionDiscontinuity(int reason) {

                        }

                        @Override
                        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                        }

                        @Override
                        public void onSeekProcessed() {

                        }
                    });


                    playerItem.getPlayer().setPlayWhenReady(true);
                });

                subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(simpleExoPlayer -> {
                    successListener.onSuccess();

                });
            }
        }
    }

    public void setVolume(int soundId,float volume){
        for(PlayerItem playerItem : playerItems){
            if(soundId == playerItem.getSoundId()){

                Observable<SimpleExoPlayer> observable = Observable.create(emitter -> {
                    playerItem.getPlayer().setVolume(volume);
                    emitter.onNext(playerItem.getPlayer());
                    emitter.onComplete();
                });
                subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(simpleExoPlayer -> {});
            }
        }
    }

    public void playMedia(String url,int soundId,ExoSuccessListener successListener){
        Observable<SimpleExoPlayer> observable = Observable.create(emitter -> {
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(
                    view.getContext(),
                    null,
                    DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF
            );
            TrackSelector trackSelector = new DefaultTrackSelector();
            SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    renderersFactory,
                    trackSelector
            );
            String userAgent = Util.getUserAgent(view.getContext(), "Play Audio");
            ExtractorMediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(url),
                    new DefaultDataSourceFactory(view.getContext(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null
            );
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);


            simpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if(playWhenReady){

                        emitter.onNext(simpleExoPlayer);
                        emitter.onComplete();
                        simpleExoPlayer.removeListener(this);
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });



        });
        subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(simpleExoPlayer -> {
            playerItems.add(new PlayerItem(){{
                setPlayer(simpleExoPlayer);
                setSoundId(soundId);

            }});
            successListener.onSuccess();
        });
    }

    public void clearAllMediaPlayer(){
        for(PlayerItem playerItem : playerItems){
            Observable<SimpleExoPlayer> observable = Observable.create(emitter -> {
                playerItem.getPlayer().setPlayWhenReady(false);
                playerItem.getPlayer().stop();
                playerItem.getPlayer().seekTo(0);
                emitter.onNext(playerItem.getPlayer());
                emitter.onComplete();
            });
            subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(simpleExoPlayer -> {});
        }


        playerItems.clear();
    }

    public interface ExoSuccessListener{
        void onSuccess();
    }
}
