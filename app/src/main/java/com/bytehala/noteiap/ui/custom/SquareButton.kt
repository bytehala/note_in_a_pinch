package com.bytehala.noteiap.ui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.bytehala.noteiap.ui.theme.Background
import com.bytehala.noteiap.ui.theme.Primary
import com.bytehala.noteiap.ui.theme.Transparent
import org.w3c.dom.Text

@Composable
fun SquareButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Transparent,
    contentColor: Color = Primary,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(60.dp),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        border = BorderStroke(width = 1.dp, Primary),
        shape = RectangleShape
    ) {
        content()
    }
}

@Composable
fun CustomButtonExample(text: String) {
    SquareButton(onClick = { /* Handle click */ }) {
        Text(text = text, fontSize = 4.em, overflow = TextOverflow.Visible)
    }
}

@Preview
@Composable
fun PreviewCustomButtonExample() {
    CustomButtonExample(">")
}
