package ug.karuhanga.lingo.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import ug.karuhanga.lingo.model.entities.Phrase

@Dao
interface PhraseDao{
    @Insert(onConflict = IGNORE)
    fun insertPhrase(phrase: Phrase)
}