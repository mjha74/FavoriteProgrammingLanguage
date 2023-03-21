package mykt.tutorials.favoriteprogramminglanguage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePollResult
import mykt.tutorials.favoriteprogramminglanguage.repository.LanguagePollRepository
import mykt.tutorials.favoriteprogramminglanguage.view.base.UiState


class LanguagePollViewModel(private val repository: LanguagePollRepository, private var languagePollResult: LanguagePollResult) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<LanguagePoll>>(UiState.Loading)

    val uiState: StateFlow<UiState<LanguagePoll>> = _uiState

    // init call
    init {
        loadPoll()
    }

    // call repository to load the data
    private fun loadPoll()
    {
        viewModelScope.launch {
            repository.getPoll()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    populatePollResult(it) // load the poll choices with all initial value 0
                    _uiState.value = UiState.Success(it)
                }
        }
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
