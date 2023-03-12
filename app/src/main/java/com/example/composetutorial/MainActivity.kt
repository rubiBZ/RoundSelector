package com.example.composetutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import java.lang.Math.*
import kotlin.math.atan2


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewContent()
        }
    }
}

@Composable
fun Content() {
    var radius by remember {mutableStateOf(0f)}
    var shapeCenter by remember {mutableStateOf(Offset.Zero)}
    var handleCenter by remember {mutableStateOf(Offset.Zero)}
    var angle by remember {mutableStateOf(20.0)}

    Canvas(
        modifier = Modifier
           .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    handleCenter += dragAmount
                    angle = getRotationAngle(handleCenter, shapeCenter)
                    change.consume()
                }
            }
            .padding(30.dp)
    ) {
        shapeCenter = center
        radius = size.minDimension / 2
        val x = (shapeCenter.x + cos(toRadians(angle)) * radius).toFloat()
        val y = (shapeCenter.y + sin(toRadians(angle)) * radius).toFloat()
        handleCenter = Offset(x, y)


        drawArc(
            color = Color.Yellow,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(80f),
            topLeft = Offset(shapeCenter.x-radius,shapeCenter.y-radius),
            size = Size(radius*2, radius*2)
        )
        drawArc(
            color = Color.Blue,
            startAngle = 90f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(80f),
            topLeft = Offset(shapeCenter.x-radius,shapeCenter.y-radius),
            size = Size(radius*2, radius*2)
        )
        drawArc(
            color = Color.Green,
            startAngle = 180f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(80f),
            topLeft = Offset(shapeCenter.x-radius,shapeCenter.y-radius),
            size = Size(radius*2, radius*2)
        )
        drawArc(
            color = Color.Red,
            startAngle = 270f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(80f),
            topLeft = Offset(shapeCenter.x-radius,shapeCenter.y-radius),
            size = Size(radius*2, radius*2)
        )
        drawCircle(color = Color.Cyan, center = handleCenter, radius = 60f)
    }
}

private fun getRotationAngle(currentPosition: Offset, center: Offset): Double {
    val (dx, dy) = currentPosition - center
    val theta = atan2(dy, dx).toDouble()
    var angle = Math.toDegrees(theta)
    if (angle < 0) {
        angle += 360.0
    }
    Log.d("RRubi",angle.toString())
    return angle
}

@Preview
@Composable
fun PreviewContent() {
    ComposeTutorialTheme {
        Surface {
            Content()
        }
    }
}