import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bytehala.noteiap.ui.theme.Transparent

@Composable
fun InkTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    cursorColor: Color = Color.Black,
    cursorWidth: Float = 2f,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        cursorBrush = SolidColor(Transparent), // Disable default cursor
        modifier = modifier.drawBehind {
            // Custom underscore cursor drawing logic
            val cursorOffset = value.text.substring(0, value.selection.end).length * textStyle.fontSize.toPx() / 2
            val cursorHeight = 2.dp.toPx() // Height of the underscore cursor
            drawRect(
                color = Color.Black,
                topLeft = androidx.compose.ui.geometry.Offset(cursorOffset, size.height - cursorHeight),
                size = androidx.compose.ui.geometry.Size(cursorWidth.dp.toPx(), cursorHeight)
            )
        },
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.background(backgroundColor)) {
                if (label != null && value.text.isEmpty()) {
                    label()
                }
                innerTextField()
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}