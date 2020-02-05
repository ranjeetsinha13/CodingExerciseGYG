package com.rs.codingexercisegyg.di

import android.app.Application
import com.rs.codingexercisegyg.base.GYGApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        FragmentBuildersModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun inject(application: GYGApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}