package org.synac.whiteboard.domain.model

import org.jetbrains.compose.resources.DrawableResource
import whiteboard.composeapp.generated.resources.Res
import whiteboard.composeapp.generated.resources.ic_arrow_one_sided
import whiteboard.composeapp.generated.resources.ic_arrow_two_sided
import whiteboard.composeapp.generated.resources.ic_circle_filled
import whiteboard.composeapp.generated.resources.ic_circle_outline
import whiteboard.composeapp.generated.resources.ic_line_dotted
import whiteboard.composeapp.generated.resources.ic_line_plain
import whiteboard.composeapp.generated.resources.ic_rectangle_filled
import whiteboard.composeapp.generated.resources.ic_rectangle_outline
import whiteboard.composeapp.generated.resources.ic_triangle_filled
import whiteboard.composeapp.generated.resources.ic_triangle_outline
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
    LINE_PLAIN(res = Res.drawable.ic_line_plain),
    LINE_DOTTED(res = Res.drawable.ic_line_dotted),
    ARROW_ONE_SIDED(res = Res.drawable.ic_arrow_one_sided),
    ARROW_TWO_SIDED(res = Res.drawable.ic_arrow_two_sided),
    RECTANGLE_OUTLINE(res = Res.drawable.ic_rectangle_outline),
    CIRCLE_OUTLINE(res = Res.drawable.ic_circle_outline),
    TRIANGLE_OUTLINE(res = Res.drawable.ic_triangle_outline),
    RECTANGLE_FILLED(res = Res.drawable.ic_rectangle_filled),
    CIRCLE_FILLED(res = Res.drawable.ic_circle_filled),
    TRIANGLE_FILLED(res = Res.drawable.ic_triangle_filled),
}