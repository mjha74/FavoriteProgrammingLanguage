package mykt.tutorials.favoriteprogramminglanguage.repository

import mykt.tutorials.favoriteprogramminglanguage.data.OperationCallback
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult

class LanguagePollRepository (private val languagePollDataSource: LanguagePollDataSource) {

    fun getPoll(callback: OperationCallback<LanguagePoll>) {
        languagePollDataSource.getPoll(callback)
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