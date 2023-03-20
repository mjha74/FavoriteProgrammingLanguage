package mykt.tutorials.favoriteprogramminglanguage.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollDataSource
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


class LanguagePollRemoteDataSource @Inject constructor(val context: Context): LanguagePollDataSource {

    override fun getPoll(callback: OperationCallback<LanguagePoll>) {
        lateinit var jsonString: String
        try {
            jsonString = this::class.java.classLoader?.getResource("poll.json")?.readText() ?: ""
        } catch (ioException: IOException) {
            callback.onError(ioException.message)
        }
        val languagePoll: LanguagePoll = Gson().fromJson(jsonString, LanguagePoll::class.java)
       callback.onSuccess(languagePoll)
    }

    override fun fetchPollResult() : LanguagePollResult {

        val pSharedPref: SharedPreferences = context.getSharedPreferences("POLL", MODE_PRIVATE)
        try {
            //get from shared prefs
            val storedHashMapString: String? = pSharedPref.getString("pollresult", JSONObject().toString())
            return Gson().fromJson(storedHashMapString, LanguagePollResult::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return LanguagePollResult()

    }

    override fun savePollResponse(languagePollResult: LanguagePollResult) {
        val jsonString: String  = Gson().toJson(languagePollResult)
        val sharedPref = context.getSharedPreferences("POLL", MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("pollresult", jsonString)
            apply()
        }
    }


}