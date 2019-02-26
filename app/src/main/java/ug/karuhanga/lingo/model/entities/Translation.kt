package ug.karuhanga.lingo.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import ug.karuhanga.lingo.utils.Listable

@Entity(foreignKeys = [ForeignKey(
    entity = Phrase::class,
    parentColumns = arrayOf("phrase"),
    childColumns = arrayOf("english"),
    onDelete = CASCADE
)], indices = [Index(value = arrayOf("english", "ruruuli"), unique = true
)]
)
data class Translation(
    @PrimaryKey var id: Int,
    var english: String,
    var ruruuli: String
): Listable {
    override fun getText1(): String {
        return english
    }

    override fun getText2(): String {
        return ruruuli
    }
}