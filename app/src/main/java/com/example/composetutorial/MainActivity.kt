package com.example.composetutorial

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import java.lang.Math.*
import kotlin.math.atan2

val displayMetrics = DisplayMetrics()
var width = 0f
var height = 0f
var dns = displayMetrics.density
var rd = 1f

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        dns = displayMetrics.density
        width = (displayMetrics.widthPixels)/dns
        height = (displayMetrics.heightPixels)/dns


        rd = (width-100)/2
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
            .padding(100.dp)
    ) {
        shapeCenter = center
        radius = size.minDimension / 2+100
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
    Log.d("RRubi", "width: "+width.toString()+" height: "+ height.toString()+"  rd: "+rd.toString()+"  dns: "+dns.toString())
    return angle
}

@SuppressLint("ResourceType")
@Preview
@Composable
fun LoadingImageFromDisk() {

    val imageModifier = Modifier
        .offset(y= ((height/2)-rd/2).dp,x= ((width/2)-rd/2).dp)
        .clip(CircleShape)
        .size((rd).dp)



    Image(
        painter = painterResource(id = R.raw.pic2),
        contentDescription = stringResource(id = R.string.dog_content_description),
        contentScale = ContentScale.Fit,
        modifier = imageModifier,
    )
}

@Preview
@Composable
fun PreviewContent() {
    ComposeTutorialTheme {
        Surface {
            LoadingImageFromDisk()
            Content()

        }
    }
}