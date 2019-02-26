package ug.karuhanga.lingo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ug.karuhanga.lingo.model.daos.LanguageDao
import ug.karuhanga.lingo.model.daos.PhraseDao
import ug.karuhanga.lingo.model.daos.TranslationDao
import ug.karuhanga.lingo.model.entities.Language
import ug.karuhanga.lingo.model.entities.Phrase
import ug.karuhanga.lingo.model.entities.Translation

@Database(version = 1, entities = [Language::class, Phrase::class, Translation::class])
abstract class Database : RoomDatabase() {
    abstract fun languageDao() : LanguageDao
    abstract fun phraseDao() : PhraseDao
    abstract fun translationDao() : TranslationDao
}

fun database(context: Context) : ug.karuhanga.lingo.model.Database{
    return Room.databaseBuilder(context, ug.karuhanga.lingo.model.Database::class.java, context.packageName).build()
}
