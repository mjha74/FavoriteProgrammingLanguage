package mykt.tutorials.favoriteprogramminglanguage.repository

import android.content.Context
import mykt.tutorials.favoriteprogramminglanguage.data.OperationCallback
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult

interface LanguagePollDataSource {

    fun getPoll(callback: OperationCallback<LanguagePoll>)
    fun fetchPollResult(): LanguagePollResult
    fun savePollResponse(languagePollResult: LanguagePollResult)

}