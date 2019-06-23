package ug.karuhanga.lingo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ug.karuhanga.lingo.model.daos.EnglishRuruuliTranslationDao
import ug.karuhanga.lingo.model.entities.EnglishRuruuliTranslation

@Database(version = 3, entities = [EnglishRuruuliTranslation::class], exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun englishRuruuliTranslationDao() : EnglishRuruuliTranslationDao

    companion object {
        private var INSTANCE: ug.karuhanga.lingo.model.Database? = null

        fun getInstance(context: Context): ug.karuhanga.lingo.model.Database? {
            if (INSTANCE == null) {
                synchronized(ug.karuhanga.lingo.model.Database::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ug.karuhanga.lingo.model.Database::class.java, "lingo_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

fun database(context: Context) : ug.karuhanga.lingo.model.Database?{
    return ug.karuhanga.lingo.model.Database.getInstance(context)
}
