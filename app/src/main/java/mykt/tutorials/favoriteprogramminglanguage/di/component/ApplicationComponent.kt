package mykt.tutorials.favoriteprogramminglanguage.di.component

import android.content.Context
import dagger.Component
import mykt.tutorials.favoriteprogramminglanguage.FavoriteProgrammingLanguageApp
import mykt.tutorials.favoriteprogramminglanguage.di.module.ApplicationModule
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollDataSource
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollRepository
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: FavoriteProgrammingLanguageApp)

    fun getContext(): Context

    fun getLanguagePollDataSource(): LanguagePollDataSource

    fun getLanguagePollRepository(): LanguagePollRepository

    fun getLanguagePollResult(): LanguagePollResult


}