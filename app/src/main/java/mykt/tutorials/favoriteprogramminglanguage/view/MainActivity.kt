package mykt.tutorials.favoriteprogramminglanguage.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import mykt.tutorials.favoriteprogramminglanguage.FavoriteProgrammingLanguageApp
import mykt.tutorials.favoriteprogramminglanguage.R
import mykt.tutorials.favoriteprogramminglanguage.di.component.DaggerActivityComponent
import mykt.tutorials.favoriteprogramminglanguage.di.module.ActivityModule
import mykt.tutorials.favoriteprogramminglanguage.model.LanguagePoll
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


    override fun onCreate(savedInstanceState: Bundle?) {

        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }


    //setup viewmodel and observers
    private fun setupViewModel()
    {
        languagePollViewModel.languagePoll.observe(this, renderPoll)
        languagePollViewModel.isViewLoading.observe(this, isViewLoadingObserver)
        languagePollViewModel.onMessageError.observe(this, onMessageErrorObserver)
    }

    //setup UI
    private fun setupUI()
    {
        pollQuestionTextView = findViewById(R.id.poll_question_textview)
        submitPollButton = findViewById(R.id.submit_button)
        pollChoicesRadioGroup = findViewById(R.id.radiogroup_pollchoice)
        pollResultButton = findViewById(R.id.poll_result_button)
        errorMessageTextView = findViewById(R.id.error_textview)

        submitPollButton.setOnClickListener(submitPollButtonOnClickListener)
        pollResultButton.setOnClickListener(showPollResultOnClickListener)
    }

    //observers
    private val renderPoll = Observer<LanguagePoll> {
        pollQuestionTextView.text = it.question
        for (i in 0 until it.choices.size) {
            val rbn = RadioButton(this).apply {
                id = i + 1000 // set unique id for each radio
                text = it.choices[i]
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setTextAppearance(android.R.style.TextAppearance_Holo_Medium)
                }
                setPadding(0, 50, 0, 50)
            }
            //Attach button to RadioGroup.
            pollChoicesRadioGroup.addView(rbn)
        }
    }

    //TODO: do some thing here
    private val isViewLoadingObserver = Observer<Boolean> {

    }
    // TODO: do something  here
    private val onMessageErrorObserver = Observer<Any> {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Failed to load poll")
            setPositiveButton("OK", null)
            show()
        }
    }

    private val submitPollButtonOnClickListener = View.OnClickListener {
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
    }

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

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as FavoriteProgrammingLanguageApp).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

}

