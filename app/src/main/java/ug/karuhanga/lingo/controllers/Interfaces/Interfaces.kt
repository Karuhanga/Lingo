package ug.karuhanga.lingo.controllers.Interfaces

import android.content.Context
import ug.karuhanga.lingo.utils.Listable

interface GistControllerExternalInterface{
    fun onSearchResultsReady(results: List<Listable>, page: Int)
    fun requestContext(): Context
}