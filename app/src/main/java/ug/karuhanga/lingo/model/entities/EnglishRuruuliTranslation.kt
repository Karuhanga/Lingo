package ug.karuhanga.lingo.model.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ug.karuhanga.lingo.utils.Listable

@Entity(indices = [Index(value = arrayOf("english", "ruruuli"), unique = true)])
data class EnglishRuruuliTranslation(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var english: String,
    var ruruuli: String,
    var ruruuli_context: String,
    var word_type: String,
    var english_example: String,
    var ruruuli_example: String
): Listable {
    override fun getText1(): String {
        return english
    }

    override fun getText2(): String {
        return ruruuli
    }
}
