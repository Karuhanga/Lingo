package ug.karuhanga.lingo.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ug.karuhanga.lingo.model.database
import ug.karuhanga.lingo.model.entities.EnglishRuruuliTranslation
import java.io.IOException


val ENGLISH = "English"
val RURUULI = "Ruruuli"

fun loadEnglishRuruuliTranslations(context: Context, publishProgress: (step: Int) -> Unit){
    val translationListType = object : TypeToken<List<EnglishRuruuliTranslation>>() {}.type

    var json = ""
    try {
        val file = context.assets.open("data/english_ruruuli_translations.json")
        val size = file.available()
        val buffer = ByteArray(size)
        file.read(buffer)
        file.close()
        json = String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return
    }


    val translations : List<EnglishRuruuliTranslation> = Gson().fromJson(json, translationListType)

    for ((i, translation: EnglishRuruuliTranslation) in translations.withIndex()){
        database(context)?.englishRuruuliTranslationDao()?.insertEnglishRuruuliTranslation(translation)
        publishProgress(i)
    }
}

fun removeBracketed(text: String): String{
    val openingBracket = text.indexOf('(')
    val closingBracket = text.indexOf(')')
    if (openingBracket > -1 && closingBracket > -1) {
        return text.substringBefore('(') + text.substringAfter(')')
    }
    return text
}
