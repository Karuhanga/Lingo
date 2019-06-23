package ug.karuhanga.lingo.controllers

import ug.karuhanga.lingo.utils.RURUULI
import ug.karuhanga.lingo.controllers.Interfaces.GistControllerExternalInterface
import ug.karuhanga.lingo.model.database
import ug.karuhanga.lingo.model.entities.EnglishRuruuliTranslation
import ug.karuhanga.lingo.utils.ENGLISH
import ug.karuhanga.lingo.utils.Listable
import ug.karuhanga.lingo.views.GistExternalInterface

class GistController(var dashboard: GistControllerExternalInterface): GistExternalInterface {
    var language = RURUULI
    val count = 15

    override fun switchLanguage(language: String) {
        this.language = language
    }

    override fun doSearch(query: String, page: Int) {
        val translations: List<EnglishRuruuliTranslation>? = if (language == RURUULI){
            database(dashboard.requestContext())?.englishRuruuliTranslationDao()?.loadTranslationsByRuruuliPhrase(query, count, page)
        } else{
            database(dashboard.requestContext())?.englishRuruuliTranslationDao()?.loadTranslationsByEnglishPhrase(query, count, page)
        }

        val results = mutableListOf<Listable>()

        if (translations == null){
            dashboard.onSearchResultsReady(results, page)
            return
        }

        for (translation in translations){
            results.add(object : Listable{
                override fun getText1(): String {
                    return if (language == RURUULI){
                        translation.ruruuli
                    } else{
                        translation.english
                    }
                }

                override fun getText2(): String {
                    return if (language == ENGLISH){
                        translation.ruruuli
                    } else{
                        translation.english
                    }
                }

            })
        }

        dashboard.onSearchResultsReady(results, page)
    }
}