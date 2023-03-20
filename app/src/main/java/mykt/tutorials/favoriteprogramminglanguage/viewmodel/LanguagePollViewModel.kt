package mykt.tutorials.favoriteprogramminglanguage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mykt.tutorials.favoriteprogramminglanguage.data.OperationCallback
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollRepository




class LanguagePollViewModel(private val repository: LanguagePollRepository, private var languagePollResult: LanguagePollResult) : ViewModel() {

    private val _languagePoll = MutableLiveData<LanguagePoll>()
    val languagePoll: LiveData<LanguagePoll> = _languagePoll

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    // init call
    init {
        loadPoll()
    }

    // call repository to load the data
    private fun loadPoll()
    {
        _isViewLoading.value = true
        repository.getPoll(object : OperationCallback<LanguagePoll>
        {
            override fun onSuccess(data: LanguagePoll?) {
                _isViewLoading.value = false
                if (data != null) {
                    populatePollResult(data) // load the poll choices with all initial value 0
                    _languagePoll.value = data
                }
            }

            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }
        })
    }

    private fun populatePollResult(data: LanguagePoll)
    {
        // fetch the  previous stored result
        languagePollResult = repository.fetchPollResult()
        // if not then populate the map with default value
        if (languagePollResult.pollResult.isEmpty()) {
            for (choice in data.choices)
            {
                languagePollResult.pollResult.getOrPut(choice) { 0 }
            }
        }
    }

    //update the poll result collection
    fun updatePollResult(selectedChoice: String)
    {
        languagePollResult.pollResult[selectedChoice] =
            languagePollResult.pollResult[selectedChoice]?.plus(1) ?: 1
        repository.savePollResponse(languagePollResult)
    }

    // fetch poll result and format the result string
   fun getPollResult(): String {
        return (languagePollResult.pollResult.map { "${it.key}   :   ${it.value}  " }.joinToString(" <br><br> "))
    }


}
