package ug.karuhanga.lingo.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton

import kotlinx.android.synthetic.main.activity_gist.*
import kotlinx.android.synthetic.main.content_gist.*
import org.androidannotations.annotations.*
import ug.karuhanga.lingo.R
import ug.karuhanga.lingo.controllers.GistController
import ug.karuhanga.lingo.controllers.Interfaces.GistControllerExternalInterface
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.androidannotations.api.BackgroundExecutor
import ug.karuhanga.lingo.utils.*
import android.view.Gravity
import android.widget.TextView
import com.kyleduo.blurpopupwindow.library.BlurPopupWindow
import ug.karuhanga.lingo.model.entities.EnglishRuruuliTranslation


@EActivity(R.layout.activity_gist)
class Gist : AppCompatActivity(), GistControllerExternalInterface, GistCallbackInterface {
    override fun getCurrentLanguage(): String {
        return controller.getCurrentLanguage()
    }

    override fun showItem(item: EnglishRuruuliTranslation) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.translation_detail, content_gist_parent, false)
        view.findViewById<TextView>(R.id.translation_detail_word_type).text = "${item.getText1(getCurrentLanguage())}(${item.word_type})"
        view.findViewById<TextView>(R.id.translation_detail_english).text = item.english
        view.findViewById<TextView>(R.id.translation_detail_ruruuli).text = item.ruruuli
        view.findViewById<TextView>(R.id.translation_detail_english_usage).text = item.english_example
        view.findViewById<TextView>(R.id.translation_detail_ruruuli_usage).text = item.ruruuli_example

        BlurPopupWindow.Builder<BlurPopupWindow>(this)
            .setContentView(view)
            .setGravity(Gravity.CENTER)
            .setScaleRatio(0.2f)
            .setBlurRadius(0f)
            .setTintColor(0x30000000)
            .build()
            .show()
    }

    override fun requestContext(): Context {
        return this
    }

    @UiThread
    override fun onSearchResultsReady(results: List<EnglishRuruuliTranslation>, page: Int) {
        if (page == 1){
            adapter.clearData()
            viewScrollListener.resetState()
            adapter.appendData(results)
        }
        else{
            adapter.appendData(results)
        }

    }

    lateinit var controller: GistExternalInterface
    lateinit var viewScrollListener: EndlessRecyclerViewScrollListener
    lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @AfterViews
    fun initialize(){
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view_gist.layoutManager = linearLayoutManager

        viewScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                search(edit_text_gist.text.toString(), page + 1)
            }
        }
        recycler_view_gist.addOnScrollListener(viewScrollListener)

        adapter = Adapter(mutableListOf(), this)
        recycler_view_gist.adapter = adapter

        text_view_language.text = getString(R.string.ruruuli)
        controller = GistController(this)
        startFirstSearch()
    }

    private fun startFirstSearch() {
        this.onSearchTextChange()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_gist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @CheckedChange(R.id.switch_gist)
    fun onCheckedChange(button: CompoundButton, status: Boolean){
        if (status){
            switchSearchLanguageTo(RURUULI)
        }
        else{
            switchSearchLanguageTo(ENGLISH)
        }
    }

    @TextChange(R.id.edit_text_gist)
    fun onSearchTextChange(){
        search(edit_text_gist.text.toString(), 1)
    }

    private fun search(query: String, page: Int) {
        loading(true)
        BackgroundExecutor.cancelAll("search", true)
        doSearch(query, page)
    }

    private fun loading(status: Boolean) {
        // TODO
    }

    @Background(id = "search")
    fun doSearch(query: String, page: Int) {
        controller.doSearch(query, page)
    }

    fun switchSearchLanguageTo(language: String) {
        if (language == ENGLISH){
            text_view_language.text = getText(R.string.english)
            text_view_flip.text = getString(R.string.flip_for_runyara)
        }
        else{
            text_view_language.text = getText(R.string.ruruuli)
            text_view_flip.text = getString(R.string.flip_for_english)
        }
        controller.switchLanguage(language)
        startFirstSearch()
    }

}

fun launch(context: Context) {
    context.startActivity(Intent(context, Gist_::class.java))
}

interface GistExternalInterface{
    fun doSearch(query: String, page: Int)
    fun switchLanguage(language: String)
    fun getCurrentLanguage(): String
}

interface GistCallbackInterface{
    fun showItem(item: EnglishRuruuliTranslation)
    fun getCurrentLanguage(): String
}
