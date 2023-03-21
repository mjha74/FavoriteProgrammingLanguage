package mykt.tutorials.favoriteprogramminglanguage.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import mykt.tutorials.favoriteprogramminglanguage.FavoriteProgrammingLanguageApp
import mykt.tutorials.favoriteprogramminglanguage.R
import mykt.tutorials.favoriteprogramminglanguage.di.component.DaggerActivityComponent
import mykt.tutorials.favoriteprogramminglanguage.di.module.ActivityModule
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
import mykt.tutorials.favoriteprogramminglanguage.view.base.UiState
import mykt.tutorials.favoriteprogramminglanguage.viewmodel.LanguagePollViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var languagePollViewModel: LanguagePollViewModel

    private lateinit var pollQuestionTextView: TextView
    private lateinit var pollChoicesRadioGroup: RadioGroup
    private lateinit var submitPollButton: Button
    private lateinit var pollResultButton: Button
    private lateinit var errorMessageTextView: TextView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {

        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupObserver()
    }

    //setup UI
    private fun setupUI()
    {
        pollQuestionTextView = findViewById(R.id.poll_question_textview)
        submitPollButton = findViewById(R.id.submit_button)
        pollChoicesRadioGroup = findViewById(R.id.radiogroup_pollchoice)
        pollResultButton = findViewById(R.id.poll_result_button)
        errorMessageTextView = findViewById(R.id.error_textview)
        progressBar =  findViewById(R.id.progressBar)

        submitPollButton.setOnClickListener(submitPollButtonOnClickListener)
        pollResultButton.setOnClickListener(showPollResultOnClickListener)
    }

    //observer
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languagePollViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            renderPoll(it.data)
                            progressBar.visibility = View.GONE
                        }
                        is UiState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Error -> {
                            progressBar.visibility = View.GONE
                            showErrorMessage()
                        }
                    }
                }
            }
        }
    }

    // display poll question and choices
    private fun renderPoll(languagePol: LanguagePoll) {
        pollQuestionTextView.text = languagePol.question
        for (i in 0 until languagePol.choices.size) {
            val rbn = RadioButton(this).apply {
                id = i + 1000 // set unique id for each radio
                text = languagePol.choices[i]
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setTextAppearance(android.R.style.TextAppearance_Holo_Medium)
                }
                setPadding(0, 50, 0, 50)
            }
            //Attach button to RadioGroup.
            pollChoicesRadioGroup.addView(rbn)
        }
    }

    private fun showErrorMessage() {
        progressBar.visibility = View.GONE
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Failed to load poll")
            setPositiveButton("OK", null)
            show()
        }
    }

    // submit poll  click listener
    private val submitPollButtonOnClickListener = View.OnClickListener {
        progressBar.visibility = View.VISIBLE
        errorMessageTextView.visibility = View.GONE
        val id: Int = pollChoicesRadioGroup.checkedRadioButtonId
        if (id > -1) {
            val selectedRadioButton: RadioButton = findViewById(id)
            languagePollViewModel.updatePollResult(selectedRadioButton.text.toString())
            pollChoicesRadioGroup.clearCheck()
        } else {
            errorMessageTextView.text = getString(R.string.error_msg)
            errorMessageTextView.visibility = View.VISIBLE
        }
        progressBar.visibility = View.GONE
    }

    // show poll result click listener
    @Suppress("DEPRECATION")
   private val  showPollResultOnClickListener = View.OnClickListener {
       val builder = AlertDialog.Builder(this)
       with(builder)
       {
           setTitle("Favorite Language Poll Result")
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               setMessage(Html.fromHtml("<b>" + languagePollViewModel.getPollResult() + "</b>", 0))
           }
           else {
               setMessage(Html.fromHtml("<b>" + languagePollViewModel.getPollResult() + "</b>"))
           }
           setPositiveButton("OK", null)
           show()
       }
   }

    //Activity Component
    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as FavoriteProgrammingLanguageApp).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

}

