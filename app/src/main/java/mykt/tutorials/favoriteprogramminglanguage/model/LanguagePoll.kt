package mykt.tutorials.favoriteprogramminglanguage.model

import com.google.gson.annotations.SerializedName

//data container
data class LanguagePoll(
    @SerializedName("question")
    val question: String = "",
    @SerializedName("choices")
    val choices: List<String> = ArrayList(),

)
