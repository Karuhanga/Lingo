package ug.karuhanga.lingo.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ug.karuhanga.lingo.model.entities.Language

@Dao
interface LanguageDao{
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertLanguage(language: Language)

    @Query("SELECT * FROM language")
    fun loadAllLanguages() : List<Language>

    @Query("SELECT * FROM language WHERE name=:name LIMIT 1")
    fun loadLanguageByName(name: String) : Language?
}