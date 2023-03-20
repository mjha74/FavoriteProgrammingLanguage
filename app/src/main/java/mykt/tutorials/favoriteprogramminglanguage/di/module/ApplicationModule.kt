package mykt.tutorials.favoriteprogramminglanguage.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import mykt.tutorials.favoriteprogramminglanguage.FavoriteProgrammingLanguageApp
import mykt.tutorials.favoriteprogramminglanguage.data.LanguagePollRemoteDataSource
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollDataSource
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollRepository

@Module
class ApplicationModule( private val application: FavoriteProgrammingLanguageApp) {

    private var languagePollDataSource: LanguagePollDataSource? = null
    private var languagePollRepository: LanguagePollRepository? = null
    private var languagePollResult: LanguagePollResult? = null

    @Provides
    fun provideContext(): Context {
        return application
    }


    private fun createLanguagePollDataSource(): LanguagePollDataSource {
        val dataSource = LanguagePollRemoteDataSource(application)
        languagePollDataSource = dataSource
        return dataSource
    }

    private fun createLanguagePollRepository(): LanguagePollRepository
    {
        val repository = LanguagePollRepository(provideLanguagePollDataSource())
        languagePollRepository = repository
        return repository
    }

    private fun createLanguagePollResult(): LanguagePollResult
    {
        val result = LanguagePollResult()
        languagePollResult = result
        return result
    }


    @Provides
    fun provideLanguagePollDataSource() = languagePollDataSource ?: createLanguagePollDataSource()

    @Provides
    fun provideLanguagePollRepository() = languagePollRepository ?: createLanguagePollRepository()

    @Provides
    fun provideLanguagePollResult() = languagePollResult ?: createLanguagePollResult()



}
