package org.synac.whiteboard.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import org.synac.whiteboard.data.util.Constant.WHITEBOARD_TABLE_NAME

@Entity(tableName = WHITEBOARD_TABLE_NAME)
data class WhiteboardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val lastEdited: LocalDate,
    val canvasColor: Int
)
