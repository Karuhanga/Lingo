package ug.karuhanga.lingo.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ug.karuhanga.lingo.model.entities.Language
import ug.karuhanga.lingo.model.entities.Phrase
import ug.karuhanga.lingo.model.database
import ug.karuhanga.lingo.model.entities.Translation
import java.io.IOException


val ENGLISH = "English"
val RURUULI = "Ruruuli"


fun loadPhrases(context: Context, publishProgress: (step: Int) -> Unit){
    val phraseListType = object : TypeToken<List<Phrase>>() {}.type

    var json = ""
    try {
        val file = context.assets.open("data/phrases.json")
        val size = file.available()
        val buffer = ByteArray(size)
        file.read(buffer)
        file.close()
        json = String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
    }


    var phrases : List<Phrase> = Gson().fromJson(json, phraseListType)

    var i = 0

    for (phrase : Phrase in phrases){
        database(context)?.phraseDao()?.insertPhrase(phrase)
        publishProgress(2 + i++)

    }
}

fun loadLanguages(context: Context, publishProgress: (step: Int) -> Unit){
    database(context)?.languageDao()?.insertLanguage(Language(1, "English"))
    publishProgress(1)
    database(context)?.languageDao()?.insertLanguage(Language(2, "Ruruuli/Lunyara"))
    publishProgress(2)
}

fun loadTranslations(context: Context, publishProgress: (step: Int) -> Unit){
    val translationListType = object : TypeToken<List<Translation>>() {}.type

    var json = ""
    try {
        val file = context.assets.open("data/translations.json")
        val size = file.available()
        val buffer = ByteArray(size)
        file.read(buffer)
        file.close()
        json = String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
    }


    var translations : List<Translation> = Gson().fromJson(json, translationListType)
    var i = 0

    for (translation: Translation in translations){
        database(context)?.translationDao()?.insertTranslation(translation)
        publishProgress(5005 + i++)
    }
}
