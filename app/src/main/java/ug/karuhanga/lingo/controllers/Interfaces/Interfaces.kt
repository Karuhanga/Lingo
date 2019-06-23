package ug.karuhanga.lingo.controllers.Interfaces

import android.content.Context
import ug.karuhanga.lingo.model.entities.EnglishRuruuliTranslation

interface GistControllerExternalInterface{
    fun onSearchResultsReady(results: List<EnglishRuruuliTranslation>, page: Int)
    fun requestContext(): Context
}