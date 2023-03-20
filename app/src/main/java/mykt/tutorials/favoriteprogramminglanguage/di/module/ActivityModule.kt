package mykt.tutorials.favoriteprogramminglanguage.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollRepository
import mykt.tutorials.favoriteprogramminglanguage.viewmodel.LanguagePollViewModel
import mykt.tutorials.favoriteprogramminglanguage.viewmodel.ViewModelProviderFactory


@Module
class ActivityModule (private val activity : AppCompatActivity){

    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideLanguagePollViewModel(languagePollRepository: LanguagePollRepository, languagePollResult: LanguagePollResult): LanguagePollViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(LanguagePollViewModel::class) {
            LanguagePollViewModel(languagePollRepository, languagePollResult)
        })[LanguagePollViewModel::class.java]
    }

}