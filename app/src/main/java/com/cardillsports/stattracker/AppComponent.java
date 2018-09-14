package com.cardillsports.stattracker;

import com.cardillsports.stattracker.game.di.GameActivityModule;
import com.cardillsports.stattracker.game.ui.GameActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


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