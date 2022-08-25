package com.baka3k.architecture.core.ui.theme
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt
val ShimmerColorShades = listOf(
    Color.LightGray.copy(0.9f),
    Color.LightGray.copy(0.2f),
    Color.LightGray.copy(0.9f)
)
internal fun Color.lighten(luminance: Float): Color {
    val hsl = FloatArray(3)
    ColorUtils.RGBToHSL(
        (red * 256).roundToInt(),
        (green * 256).roundToInt(),
        (blue * 256).roundToInt(),
        hsl
    )
    hsl[2] = luminance
    val color = Color(ColorUtils.HSLToColor(hsl))
    return color
}
