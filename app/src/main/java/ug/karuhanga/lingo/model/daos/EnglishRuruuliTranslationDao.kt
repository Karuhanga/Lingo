package ug.karuhanga.lingo.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ug.karuhanga.lingo.model.entities.EnglishRuruuliTranslation

@Dao
interface EnglishRuruuliTranslationDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEnglishRuruuliTranslation(translation: EnglishRuruuliTranslation)

    @Query("SELECT * FROM englishruruulitranslation LIMIT :limit")
    fun loadTranslations(limit: Int): List<EnglishRuruuliTranslation>

    @Query("SELECT * FROM englishruruulitranslation WHERE english LIKE '%' || :englishPhrase || '%' LIMIT :count OFFSET (:count*(:page-1))")
    fun loadTranslationsByEnglishPhrase(englishPhrase: String, count: Int, page: Int) : List<EnglishRuruuliTranslation>

    @Query("SELECT * FROM englishruruulitranslation WHERE ruruuli LIKE '%' || :ruruuliPhrase || '%' LIMIT :count OFFSET (:count*(:page-1))")
    fun loadTranslationsByRuruuliPhrase(ruruuliPhrase: String, count: Int, page: Int) : List<EnglishRuruuliTranslation>
}
