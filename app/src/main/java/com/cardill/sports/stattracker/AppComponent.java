package com.cardill.sports.stattracker;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;


/**https://github.com/vestrel00/android-dagger-butterknife-mvp/blob/master/app/src/main/java/com/vestrel00/daggerbutterknifemvp/ui/main/MainActivityModule.java
 *  Great dagger tutorial
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent extends AndroidInjector<CardillApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<CardillApplication> {
    }

}