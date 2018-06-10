package com.mustafafidan.rahatlaticisesler.utils;


import android.media.AudioManager;
import android.media.MediaPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxMediaPlayer extends MediaPlayer {

    MediaPlayerListener mediaPlayerListener;

    public MediaPlayerListener getMediaPlayerListener() {
        return mediaPlayerListener;
    }

    public void setMediaPlayerListener(MediaPlayerListener mediaPlayerListener) {
        this.mediaPlayerListener = mediaPlayerListener;
    }

    private final CompositeDisposable disposables = new CompositeDisposable();


    public interface SuccessListener{
        void onSuccess();
    }

    public interface MediaPlayerListener{
        void onPrepareSuccess(long audioDuration);

    }

    public static @NotNull RxMediaPlayer create(MediaPlayerListener mediaPlayerListener) {
        final RxMediaPlayer player = new RxMediaPlayer();
        player.setMediaPlayerListener(mediaPlayerListener);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return player;
    }

    public void play(String url) {
        try {
            this.setDataSource(url);
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        preapareAudio(() -> { stream(this::ticks); });
    }


    public void resume(int position){
        clear();
        resume(position,()->{stream(this::ticks);});
    }


    public void pause(SuccessListener successListener){
        Observable<RxMediaPlayer> observable = Observable.create(emitter -> {
            RxMediaPlayer.this.pause();
            emitter.onNext(RxMediaPlayer.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {
            successListener.onSuccess();
        }));
    }


    public void setVolume(int maxVolume,int currVolume){

        float volume=1-(float)(Math.log(maxVolume-currVolume)/Math.log(maxVolume));
        Observable<RxMediaPlayer> observable = Observable.create(emitter -> {
            RxMediaPlayer.this.setVolume(volume,volume);
            emitter.onNext(RxMediaPlayer.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {}));
    }


    void resume(int position,SuccessListener successListener){
        Observable<RxMediaPlayer> observable = Observable.create(emitter -> {
            RxMediaPlayer.this.seekTo(position);
            emitter.onNext(RxMediaPlayer.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {
            successListener.onSuccess();
        }));
    }






    private void preapareAudio(SuccessListener successListener) {
        Observable<RxMediaPlayer> observable = Observable.create(emitter -> {
            try {
                RxMediaPlayer.this.prepare();
                emitter.onNext(RxMediaPlayer.this);
                emitter.onComplete();
            } catch (IOException e) {
                RxMediaPlayer.this.reset();
                RxMediaPlayer.this.release();
                emitter.onError(e);
            }
        });

        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(
                mediaPlayer -> {
                    mediaPlayerListener.onPrepareSuccess(RxMediaPlayer.this.getDuration()/1000);
                    successListener.onSuccess();
                }
        ));
    }

    private void stream(SuccessListener successListener) {
        Observable<RxMediaPlayer> observable = Observable.create(emitter -> {
            RxMediaPlayer.this.start();
            emitter.onNext(RxMediaPlayer.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {
            successListener.onSuccess();
        }));
    }



    // arayüzü güncellemek için saniyede bir tetiklenir
    private void ticks() {
        disposables.add(Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .takeUntil(aLong -> !isPlaying()) // çalmadığında arayüzü güncellemek için uyarır
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }








    //Memory hatalarına engel olmak için
    private void clear(){
        disposables.clear();
    }
}