package com.example.android_app

import android.app.Application
import com.example.android_app.di.AppModule
import com.example.android_app.ui.BlankFragment
import com.example.android_app.ui.SingInFragment
import com.example.android_app.ui.SingUpFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(fragment: BlankFragment)

    fun inject(activity: MainActivity)

    fun inject(fragment: SingUpFragment)
    fun inject(fragment: SingInFragment)


}

// appComponent lives in the Application class to share its lifecycle
class MyApplication: Application() {
    // Reference to the application graph that is used across the whole app
    val appComponent = DaggerApplicationComponent.create()
}