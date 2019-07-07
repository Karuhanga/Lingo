package ug.karuhanga.lingo.controllers

import ug.karuhanga.lingo.utils.RURUULI
import ug.karuhanga.lingo.controllers.Interfaces.GistControllerExternalInterface
import ug.karuhanga.lingo.model.database
import ug.karuhanga.lingo.model.entities.EnglishRuruuliTranslation
import ug.karuhanga.lingo.views.GistExternalInterface

class GistController(var dashboard: GistControllerExternalInterface): GistExternalInterface {
    var language = RURUULI
    val count = 15

    override fun getCurrentLanguage(): String {
        return language
    }

    override fun switchLanguage(language: String) {
        this.language = language
    }

    override fun doSearch(query: String, page: Int) {
        val translations: List<EnglishRuruuliTranslation>? = if (language == RURUULI){
            database(dashboard.requestContext())?.englishRuruuliTranslationDao()?.loadTranslationsByRuruuliPhrase(query, count, page)
        } else{
            database(dashboard.requestContext())?.englishRuruuliTranslationDao()?.loadTranslationsByEnglishPhrase(query, count, page)
        }

        if (translations == null){
            dashboard.onSearchResultsReady(mutableListOf(), page)
            return
        }

        dashboard.onSearchResultsReady(translations, page)
    }
}
