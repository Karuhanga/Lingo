package ug.karuhanga.lingo.controllers

import ug.karuhanga.lingo.utils.RURUULI
import ug.karuhanga.lingo.controllers.Interfaces.GistControllerExternalInterface
import ug.karuhanga.lingo.model.database
import ug.karuhanga.lingo.model.entities.Translation
import ug.karuhanga.lingo.utils.ENGLISH
import ug.karuhanga.lingo.utils.Listable
import ug.karuhanga.lingo.views.GistExternalInterface

class GistController(var dashboard: GistControllerExternalInterface): GistExternalInterface {
    override fun switchLanguage(language: String) {
        this.language = language
    }

    val count = 15
    override fun doSearch(query: String, page: Int) {
        val translations: List<Translation> = if (language == RURUULI){
            database(dashboard.requestContext()).translationDao().loadTranslationsByRuruuliPhrase(query, count, page)
        } else{
            database(dashboard.requestContext()).translationDao().loadTranslationsByEnglishPhrase(query, count, page)
        }

        val results = mutableListOf<Listable>()

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

    var language = RURUULI
}