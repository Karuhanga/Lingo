package ug.karuhanga.lingo.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Language(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String
)