package ug.karuhanga.lingo.model.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ug.karuhanga.lingo.utils.RURUULI
import ug.karuhanga.lingo.utils.removeBracketed

@Entity(indices = [Index(value = arrayOf("english", "ruruuli"), unique = true)])
data class EnglishRuruuliTranslation(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var english: String,
    var ruruuli: String,
    var ruruuli_context: String,
    var word_type: String,
    var english_example: String,
    var ruruuli_example: String){

    fun getText1(language: String): String {
        return if (language == RURUULI){
            removeBracketed(ruruuli)
        } else{
            removeBracketed(english)
        }
    }

    fun getText2(language: String): String {
        return if (language == RURUULI){
            removeBracketed(english)
        } else{
            removeBracketed(ruruuli)
        }
    }
}
