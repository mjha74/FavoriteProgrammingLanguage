package mykt.tutorials.favoriteprogramminglanguage.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult

class LanguagePollRepository (private val languagePollDataSource: LanguagePollDataSource) {

    fun getPoll() : Flow<LanguagePoll> {
        return flow {
            emit(languagePollDataSource.getPoll())
        }.map { it }
    }

    fun savePollResponse(languagePollResult: LanguagePollResult)
    {
        languagePollDataSource.savePollResponse(languagePollResult)
    }

    fun fetchPollResult() : LanguagePollResult
    {
        return languagePollDataSource.fetchPollResult()
    }

}