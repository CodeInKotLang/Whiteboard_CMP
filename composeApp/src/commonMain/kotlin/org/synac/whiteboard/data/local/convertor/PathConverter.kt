package org.synac.whiteboard.data.local.convertor

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.vector.PathParser
import androidx.room.TypeConverter

class PathConverter {

    @TypeConverter
    fun fromPath(path: Path): String {
        return serializePath(path)
    }

    @TypeConverter
    fun toPath(pathString: String): Path {
        return deserializePath(pathString)
    }

}

private fun serializePath(path: Path): String {
    val pathStringBuilder = StringBuilder()
    val pathMeasure = PathMeasure()
    pathMeasure.setPath(path, false)
    val pathLength = pathMeasure.length
    var distance = 0f
    while (distance < pathLength) {
        val pos = pathMeasure.getPosition(distance)
        if (distance == 0f) {
            pathStringBuilder.append("M${pos.x},${pos.y} ")
        } else {
            pathStringBuilder.append("L${pos.x},${pos.y} ")
        }
        distance += 5f
    }
    return pathStringBuilder.toString().trim()
}

private fun deserializePath(pathString: String): Path {
    val pathParser = PathParser().parsePathString(pathString)
    return pathParser.toPath()
}