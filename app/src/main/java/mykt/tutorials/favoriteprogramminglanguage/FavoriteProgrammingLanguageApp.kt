package mykt.tutorials.favoriteprogramminglanguage

import android.app.Application
import mykt.tutorials.favoriteprogramminglanguage.di.component.ApplicationComponent
import mykt.tutorials.favoriteprogramminglanguage.di.component.DaggerApplicationComponent
import mykt.tutorials.favoriteprogramminglanguage.di.module.ApplicationModule

class FavoriteProgrammingLanguageApp: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }


}