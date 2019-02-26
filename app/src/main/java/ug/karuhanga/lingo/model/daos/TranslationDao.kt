package ug.karuhanga.lingo.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ug.karuhanga.lingo.model.entities.Translation

@Dao
interface TranslationDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTranslation(translation: Translation)

    @Query("SELECT * FROM translation LIMIT :limit")
    fun loadTranslations(limit: Int): List<Translation>

    @Query("SELECT * FROM translation WHERE english LIKE '%' || :englishPhrase || '%' LIMIT :count OFFSET (:count*(:page-1))")
    fun loadTranslationsByEnglishPhrase(englishPhrase: String, count: Int, page: Int) : List<Translation>

    @Query("SELECT * FROM translation WHERE ruruuli LIKE '%' || :ruruuliPhrase || '%' LIMIT :count OFFSET (:count*(:page-1))")
    fun loadTranslationsByRuruuliPhrase(ruruuliPhrase: String, count: Int, page: Int) : List<Translation>
}
