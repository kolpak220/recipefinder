package com.example.recipefinder.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

@Composable
fun PercentageCircle(
    modifier: Modifier = Modifier,
    percent: Float,
    size: Dp = 50.dp,
    strokeWidth: Dp = 8.dp,
    primaryColor: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.toPx() / 2
            val sweepAngle = percent.coerceIn(0f, 100f) * 3.6f

            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${percent.toInt()}%",
            fontSize = (size.value / 3).sp,
            fontWeight = FontWeight.Normal,
            color = primaryColor
        )
    }
}