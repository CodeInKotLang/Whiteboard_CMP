package org.synac.whiteboard.domain.model

import org.jetbrains.compose.resources.DrawableResource
import whiteboard.composeapp.generated.resources.Res
import whiteboard.composeapp.generated.resources.ic_arrow
import whiteboard.composeapp.generated.resources.ic_circle
import whiteboard.composeapp.generated.resources.ic_line
import whiteboard.composeapp.generated.resources.ic_rectangle
import whiteboard.composeapp.generated.resources.ic_triangle
import whiteboard.composeapp.generated.resources.img_eraser
import whiteboard.composeapp.generated.resources.img_highlighter
import whiteboard.composeapp.generated.resources.img_laser_pen
import whiteboard.composeapp.generated.resources.img_pen

enum class DrawingTool(
    val res: DrawableResource
) {
    PEN(res = Res.drawable.img_pen),
    HIGHLIGHTER(res = Res.drawable.img_highlighter),
    LASER_PEN(res = Res.drawable.img_laser_pen),
    ERASER(res = Res.drawable.img_eraser),
    LINE(res = Res.drawable.ic_line),
    ARROW(res = Res.drawable.ic_arrow),
    RECTANGLE(res = Res.drawable.ic_rectangle),
    CIRCLE(res = Res.drawable.ic_circle),
    TRIANGLE(res = Res.drawable.ic_triangle)
}