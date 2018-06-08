package com.mustafafidan.rahatlaticisesler.injection.module;


import android.app.Application;
import android.content.Context;

import com.mustafafidan.rahatlaticisesler.base.BaseView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {


    @Provides
    Context provideContext(BaseView baseView) {
        return baseView.getContext();
    }


    @Provides
    Application provideApplication(Context context){
        return (Application) context.getApplicationContext();
    }
}
