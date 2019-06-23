package ug.karuhanga.lingo.views

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ug.karuhanga.lingo.R
import kotlinx.android.synthetic.main.activity_starter.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Background
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.UiThread
import ug.karuhanga.lingo.utils.loadEnglishRuruuliTranslations

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@EActivity(R.layout.activity_starter)
class Starter : AppCompatActivity() {

    val firstTimeKey = "v2beenHereBefore";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @AfterViews
    fun init(){
        resolvePath()
    }

    private fun resolvePath() {
        if (getSharedPreferences(packageName, Context.MODE_PRIVATE).getBoolean(firstTimeKey, false)){
            launchGist()
        }
        else{
            populateDatabase()
        }
    }

    @Background
    fun populateDatabase() {
        loadEnglishRuruuliTranslations(this, this::publishProgress)
        setBeenHereBefore()

        launchGist()
    }

    @SuppressLint("ApplySharedPref")
    private fun setBeenHereBefore() {
        getSharedPreferences(packageName, Context.MODE_PRIVATE).edit().putBoolean(firstTimeKey, true).commit()
    }

    @UiThread
    fun launchGist() {
        launch(this)
        finish()
    }

    @UiThread
    fun publishProgress(step: Int){
        progress.progress = step
    }
}
