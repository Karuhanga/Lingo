package ug.karuhanga.lingo.model.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ug.karuhanga.lingo.utils.RURUULI
import ug.karuhanga.lingo.utils.removeBracketed
import ug.karuhanga.lingo.utils.removeBrackets

@Entity(indices = [Index(value = arrayOf("english", "ruruuli"), unique = true)])
data class EnglishRuruuliTranslation(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var english: String,
    var ruruuli: String,
    var ruruuli_context: String,
    var ruruuli_with_context: String,
    var word_type: String,
    var english_example: String,
    var ruruuli_example: String){

    fun getText1(language: String): String {
        return if (language == RURUULI){
            getRuruuliDisplay()
        } else{
            removeBracketed(english)
        }
    }

    fun getText2(language: String): String {
        return if (language == RURUULI){
            removeBracketed(english)
        } else{
            getRuruuliDisplay()
        }
    }

    fun getRuruuliDisplay(): String {
        return if (ruruuli_context.isNotBlank()){
            removeBrackets(ruruuli_context)
        } else {
            removeBracketed(ruruuli)
        }
    }
}
