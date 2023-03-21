package mykt.tutorials.favoriteprogramminglanguage.repository

import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult

interface LanguagePollDataSource {

    fun getPoll(): LanguagePoll
    fun fetchPollResult(): LanguagePollResult
    fun savePollResponse(languagePollResult: LanguagePollResult)

}