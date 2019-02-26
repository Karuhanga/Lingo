package ug.karuhanga.lingo.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.EditText

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


@EActivity(R.layout.activity_gist)
class Gist : AppCompatActivity(), GistControllerExternalInterface {
    override fun requestContext(): Context {
        return this
    }

    @UiThread
    override fun onSearchResultsReady(results: List<Listable>, page: Int) {
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

        adapter = Adapter(mutableListOf())
        recycler_view_gist.adapter = adapter

        text_view_language.text = getString(R.string.ruruuli)
        controller = GistController(this)
        startFirstSearch()
    }

    private fun startFirstSearch() {
        search("", 1)
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
        }
        else{
            text_view_language.text = getText(R.string.ruruuli)
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
}
