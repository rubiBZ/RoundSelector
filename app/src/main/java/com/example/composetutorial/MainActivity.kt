package com.example.composetutorial

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import java.lang.Math.*
import kotlin.math.atan2

val displayMetrics = DisplayMetrics()
var width = 0f
var height = 0f
var dns = displayMetrics.density
var rd = 1f
var angle = 200.1
var pic =R.raw.pic1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        dns = displayMetrics.density
        width = (displayMetrics.widthPixels)/dns
        height = (displayMetrics.heightPixels)/dns
        rd = (width-100)/2 // Padding
        setContent {
            PreviewContent()
        }
    }
}

@Composable
fun Content() {
   // Column {
     //   Text(text = "msg.author")
        var radius by remember { mutableStateOf(0f) }
        var shapeCenter by remember { mutableStateOf(Offset.Zero) }
        var handleCenter by remember { mutableStateOf(Offset.Zero) }
        var angle by remember { mutableStateOf(20.0) }
        val paint = Paint().asFrameworkPaint().apply {
            textSize = 100F
            textAlign = android.graphics.Paint.Align.CENTER
        }
        val imageModifier = Modifier
            .offset(y = ((height / 2) - rd / 2).dp, x = ((width / 2) - rd / 2).dp)
            .clip(CircleShape)
            .size((rd).dp)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF25BFEA),
                            Color(0xFF06B7E9)
                        )
                    )
                    onDrawBehind {
                        drawRoundRect(
                            brush,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        handleCenter += dragAmount
                        angle = getRotationAngle(handleCenter, shapeCenter)
                        change.consume()
                    }
                }
                .padding(100.dp)
        )
        {
            shapeCenter = center
            radius = size.minDimension / 2 + 100
            val x = (shapeCenter.x + cos(toRadians(angle)) * radius).toFloat()
            val y = (shapeCenter.y + sin(toRadians(angle)) * radius).toFloat()
            handleCenter = Offset(x, y)

            drawArc(
                color = Color.Green,
                startAngle = 270f,
                sweepAngle = 72f,
                useCenter = false,
                style = Stroke(80f),
                topLeft = Offset(shapeCenter.x - radius, shapeCenter.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            drawArc(
                color = Color.Cyan,
                startAngle = 342f,
                sweepAngle = 72f,
                useCenter = false,
                style = Stroke(80f),
                topLeft = Offset(shapeCenter.x - radius, shapeCenter.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            drawArc(
                color = Color.Yellow,
                startAngle = 54f,
                sweepAngle = 72f,
                useCenter = false,
                style = Stroke(80f),
                topLeft = Offset(shapeCenter.x - radius, shapeCenter.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            drawArc(
                color = Color.Magenta,
                startAngle = 126f,
                sweepAngle = 72f,
                useCenter = false,
                style = Stroke(80f),
                topLeft = Offset(shapeCenter.x - radius, shapeCenter.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            drawArc(
                color = Color.Red,
                startAngle = 198f,
                sweepAngle = 72f,
                useCenter = false,
                style = Stroke(80f),
                topLeft = Offset(shapeCenter.x - radius, shapeCenter.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            drawIntoCanvas {
                it.nativeCanvas.drawText("מה ההרגשה כרגע בגוף?",  250f, 200f, paint)
            }
            /*
            drawLine(
                start = Offset(x = 0f, y = height/2),
                end = Offset(x = width/2, y = 0f),
                color = Color.Blue,
                strokeWidth = 20f
            )

             */
            drawCircle(color = Color.Blue, center = handleCenter, radius = 60f)
        }
        Image(
            painter = painterResource(
                id = getPic(angle)
            ),
            contentDescription = stringResource(id = R.string.dog_content_description),
            contentScale = ContentScale.Fit,
            modifier = imageModifier,
        )
   // }
}

private fun getPic(angl: Double): Int {
   // Log.d("RRubi",angl.toString())
    when (angl) {
        in 270.1..342.0 -> pic =  R.raw.pic1
        in 342.1..359.9 -> pic =  R.raw.pic2
        in 0.0..54.0 -> pic =  R.raw.pic2
        in 54.1..126.0 -> pic =  R.raw.pic3
        in 126.1..198.0 -> pic =  R.raw.pic4
        in 198.1..270.0 -> pic =  R.raw.pic5
        else -> print("none of the above")
    }
    return pic
}

private fun getRotationAngle(currentPosition: Offset, center: Offset): Double {
    val (dx, dy) = currentPosition - center
    val theta = atan2(dy, dx).toDouble()
    angle = Math.toDegrees(theta)
    if (angle < 0) {
        angle += 360.0
    }
    return angle
}

@Composable
fun MessageCard(msg: String) {
    Column {
        Text(text = msg)
        Text(text = msg)
    }
}

@Composable
fun SimpleTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        }
    )
}

@Preview
@Composable
fun PreviewContent() {
    ComposeTutorialTheme {
        Surface {
            Content()
            MyDB1()
            MyDB2()
            MyDB3()
            sendButton()
            //MessageCard("rubi")
           // SimpleTextField()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyDB1() {
    val dropDounModifier = Modifier
        .offset(y = ((height / 2) + rd*1.2).dp, x = 8.dp)
        .size((width/1.6).dp)
        .height((rd/3).dp)
    val listItems = arrayOf("אהבה","אושר","שמחה","נעימות","אמון","בטחון","גאווה","נינוחות","יציבות","התרגשות","סלחנות","חמלה","אכפתיות","רוממות רוח","פיוס","אדיבות","אמפטיה","מוצלחות","סיפוק","הישג","עליונות","כבוד","עונג","רעננות","נאמנות","הכרת תודה","אינטימיות","תקווה","השראה","הצלחה","סקרנות","אומץ","חיבה","נדיבות","איפוק","שלווה")
    val contextForToast = LocalContext.current.applicationContext
    var expanded by remember {mutableStateOf(false)} // state of the menu
    var selectedItem by remember {mutableStateOf(listItems[0])} // remember the selected item
    // box
    ExposedDropdownMenuBox(
        expanded = expanded,
        modifier = dropDounModifier,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "סל חיובי") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded = false
                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyDB2() {
    val dropDounModifier = Modifier
        .offset(y = ((height / 2) + rd*1.7).dp, x = 8.dp)
        .size((width/1.6).dp)
    val listItems = arrayOf("פעלתנות","שחרור","זיכוך","חופש","נחרצות","להיטות","תעוזה","מסוגלות","ערנות","התרגשות","ספקנות","מחויבות","אדישות","אפתיה","ניתוק","אטימות","קפדנות")
    val contextForToast = LocalContext.current.applicationContext
    var expanded by remember {mutableStateOf(false)} // state of the menu
    var selectedItem by remember {mutableStateOf(listItems[0])} // remember the selected item
    // box
    ExposedDropdownMenuBox(
        expanded = expanded,
        modifier = dropDounModifier,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "סל נייטרלי") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded = false
                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyDB3() {
    val dropDounModifier = Modifier
        .offset(y = ((height / 2) + rd*2.2).dp, x = 8.dp)
        .size((width/1.6).dp)
    val listItems = arrayOf("פחד","לחץ","עצב","כעס","שנאה","קנאה","עקצוץ","גועל","בוז","דיכאון","בגידה","אכזבה","התנגדות","זיעה","רעד","גירוד","גל קור","גל חום","עוינות","ציניות","זלזול","חוסר איזון","עצבנות","עייפות","דחייה","נטישה","פספוס – החמצה","חוסר ערך","דאגה","בלבול","תסכול","בדידות","מועקה","חוסר וודאות","קורבנות","מרירות")
    val contextForToast = LocalContext.current.applicationContext
    var expanded by remember {mutableStateOf(false)} // state of the menu
    var selectedItem by remember {mutableStateOf(listItems[0])} // remember the selected item
    // box
    ExposedDropdownMenuBox(
        expanded = expanded,
        modifier = dropDounModifier,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "סל שלילי") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded = false
                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
}

@Composable
fun sendButton() {
    val btnModifier = Modifier
        .offset(y = ((height / 2) + rd*2.2).dp, x = (width/1.4).dp)
        //.size((width/4).dp)
        .height((width/6).dp)
        .width((width/4).dp)
        .clip(CircleShape)

    Button(
        modifier = btnModifier,
        onClick = {
        //your onclick code here
    }) {
        Text(text = "שלח")
    }
}

