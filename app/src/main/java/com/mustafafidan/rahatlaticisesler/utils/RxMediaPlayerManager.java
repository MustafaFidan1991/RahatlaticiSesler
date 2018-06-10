package com.mustafafidan.rahatlaticisesler.utils;


import android.media.AudioManager;
import android.media.MediaPlayer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxMediaPlayerManager extends MediaPlayer {


    /*
    * Tamamen reactive x 'le uyumlu ve asenkron çalışabilen bir mediaplayer
    * bir media playerda kullanılabilecek durdur,oynat,devam et, ses aç kapa gibi tüm özellikler vardır
    *
    *
    * Hata istenilmemesine rağmen her saniyede bir arayüz güncellemede sağlanabilir.
    *
    * */


    boolean isFirstPlay = true;
    boolean isPrepareSuccess = false;
    int lastPosition = 0;
    boolean isPause = false;

    MediaPlayerListener mediaPlayerListener;



    private final CompositeDisposable disposables = new CompositeDisposable();


    public interface SuccessListener{
        void onSuccess();
    }

    public interface MediaPlayerListener{
        void onPrepareSuccess(long audioDuration);
        void onComplete(RxMediaPlayerManager mediaPlayer);

    }



    /**
     *  ses dosyasını çalıştırmak için
     * @param  mediaPlayerListener en başta media player'ı oluşturmak için
     */
    public static @NotNull
    RxMediaPlayerManager create(MediaPlayerListener mediaPlayerListener) {
        final RxMediaPlayerManager player = new RxMediaPlayerManager();
        player.setMediaPlayerListener(mediaPlayerListener);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return player;
    }

    /**
     *  ses dosyasını çalıştırmak için
     * @param  url istenilen url'den ses dosyasını çalıştırmak için
     */
    public void play(String url) {
        try {
            this.setDataSource(url);
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ilk sefer çalındığında çağrılır
     */
    public void play() {
        preapareAudio(() -> { stream(this::ticks); });
    }


    /**
     * durmuş veya bitmiş olan ses dosyasını devam ettirmek için
     * @param position istenilen pozisyon da tekrardan çalıştırmak için
     */
    public void resume(int position){
        clear();
        resume(position,()->{stream(this::ticks);});
    }

    /**
     * çalınmakta olan ses dosyasını durdurmak için
     * @param successListener asenkron olarak çalıştırıldıktan sonra successListener.onSuccess(); tetiklenir
     */
    public void pause(SuccessListener successListener){
        Observable<RxMediaPlayerManager> observable = Observable.create(emitter -> {
            lastPosition = getCurrentPosition();
            RxMediaPlayerManager.this.pause();
            isPause = true;
            emitter.onNext(RxMediaPlayerManager.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {
            successListener.onSuccess();
        }));
    }


    /**
     * durdurulmuş ses dosyasını tekrardan istenilen pozisyondan çağırır
     * @param maxVolume mediaplayerın max volume değeri
     * @param  currVolume atanmak istenen volume değeri
     */
    public void setVolume(int maxVolume,int currVolume){

        float volume=1-(float)(Math.log(maxVolume-currVolume)/Math.log(maxVolume));
        Observable<RxMediaPlayerManager> observable = Observable.create(emitter -> {
            RxMediaPlayerManager.this.setVolume(volume,volume);
            emitter.onNext(RxMediaPlayerManager.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {}));
    }


    /**
     * durdurulmuş ses dosyasını tekrardan istenilen pozisyondan çağırır
     * @param position istenilen pozisyon pass edilir
     * @param  successListener asenkron olarak çalıştırıldıktan sonra successListener.onSuccess(); tetiklenir
     */
    void resume(int position,SuccessListener successListener){
        Observable<RxMediaPlayerManager> observable = Observable.create(emitter -> {
            RxMediaPlayerManager.this.seekTo(position);
            isPause = false;
            emitter.onNext(RxMediaPlayerManager.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {
            successListener.onSuccess();
        }));
    }





    /**
     * İlgili source'tan prepare yapmak için bu fonksiyon çağrılır
     * @param successListener asenkron olarak çalıştırıldıktan sonra successListener.onSuccess(); tetiklenir
     */
    private void preapareAudio(SuccessListener successListener) {
        Observable<RxMediaPlayerManager> observable = Observable.create(emitter -> {
            try {
                RxMediaPlayerManager.this.prepare();
                emitter.onNext(RxMediaPlayerManager.this);
                emitter.onComplete();
            } catch (IOException e) {
                RxMediaPlayerManager.this.reset();
                RxMediaPlayerManager.this.release();
                emitter.onError(e);
            }
        });

        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(
                mediaPlayer -> {
                    isPrepareSuccess = true;
                    mediaPlayerListener.onPrepareSuccess(RxMediaPlayerManager.this.getDuration()/1000);
                    successListener.onSuccess();
                }
        ));
    }


    /**
     * İlgili source'tan stream yapmak için bu fonksiyon çağrılır
     * @param successListener asenkron olarak çalıştırıldıktan sonra successListener.onSuccess(); tetiklenir
     */
    private void stream(SuccessListener successListener) {
        Observable<RxMediaPlayerManager> observable = Observable.create(emitter -> {
            RxMediaPlayerManager.this.start();
            emitter.onNext(RxMediaPlayerManager.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {
            successListener.onSuccess();
        }));
    }


    /**
     * arayüzü güncellemek için saniyede bir tetiklenir
     */
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
                        mediaPlayerListener.onComplete(RxMediaPlayerManager.this);
                    }
                }));
    }


    public boolean isFirstPlay() {
        return isFirstPlay;
    }

    public void setFirstPlay(boolean firstPlay) {
        isFirstPlay = firstPlay;
    }

    public boolean isPrepareSuccess() {
        return isPrepareSuccess;
    }

    public void setPrepareSuccess(boolean prepareSuccess) {
        isPrepareSuccess = prepareSuccess;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public CompositeDisposable getDisposables() {
        return disposables;
    }
    public MediaPlayerListener getMediaPlayerListener() {
        return mediaPlayerListener;
    }

    public void setMediaPlayerListener(MediaPlayerListener mediaPlayerListener) {
        this.mediaPlayerListener = mediaPlayerListener;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    //Memory hatalarına engel olmak için
    public void clear(){
        Observable<RxMediaPlayerManager> observable = Observable.create(emitter -> {

            if (RxMediaPlayerManager.this.isPlaying()) {
                RxMediaPlayerManager.this.stop();
            }
            RxMediaPlayerManager.this.reset();
            RxMediaPlayerManager.this.release();

            disposables.clear();

            emitter.onNext(RxMediaPlayerManager.this);
            emitter.onComplete();
        });
        disposables.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(rxMediaPlayer -> {}));


    }




}