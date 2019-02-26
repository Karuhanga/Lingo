package ug.karuhanga.lingo.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(
    entity = Language::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("language"),
    onDelete = ForeignKey.CASCADE
)), indices = [Index(value = ["language"])])
data class Phrase(
    @PrimaryKey var phrase: String,
    var language: Int
)