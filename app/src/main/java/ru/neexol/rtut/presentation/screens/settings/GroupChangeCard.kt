package ru.neexol.rtut.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R

@Composable
fun GroupChangeCard(
	currentGroup: String,
	onNewGroup: (String) -> Unit
) {
	val focusManager = LocalFocusManager.current
	var newGroup by remember { mutableStateOf("") }

	Surface(
		modifier = Modifier.fillMaxWidth(),
		shape = MaterialTheme.shapes.medium
	) {
		Row(
			modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(text = stringResource(R.string.group))
			BasicTextField(
				modifier = Modifier.size(height = 44.dp, width = 144.dp),
				value = newGroup,
				onValueChange = { newGroup = formatGroup(it) },
				visualTransformation = groupTransformation,
				textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
				cursorBrush = SolidColor(LocalContentColor.current),
				singleLine = true,
				keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
				keyboardActions = KeyboardActions {
					onNewGroup(newGroup)
					newGroup = ""
					focusManager.clearFocus()
				},
				decorationBox = { innerTextField ->
					Box(
						modifier = Modifier
							.background(
								MaterialTheme.colors.background,
								MaterialTheme.shapes.medium
							)
							.padding(10.dp),
						contentAlignment = Alignment.CenterStart
					) {
						if (newGroup.isEmpty()) {
							Text(
								modifier = Modifier.alpha(ContentAlpha.medium),
								text = currentGroup
							)
						}
						innerTextField()
					}
				}
			)
		}
	}
}

fun formatGroup(input: String) = StringBuilder().apply {
	input.take(8).forEachIndexed { i, c ->
		when {
			i in 0..3 && c.isLetter() -> append(c.uppercaseChar())
			i in 4..7 && c.isDigit() -> append(c)
			else -> return@forEachIndexed
		}
	}
}.toString()

val groupTransformation = VisualTransformation { text ->
	val trimmed = text.text.take(8)

	var out = ""
	trimmed.forEachIndexed { index, c ->
		out += c
		if (index == 3 || index == 5) out += '-'
	}

	val numberOffsetTranslator = object : OffsetMapping {
		override fun originalToTransformed(offset: Int) = when {
			offset < 4 -> offset
			offset < 6 -> offset + 1
			offset < 8 -> offset + 2
			else -> 10
		}

		override fun transformedToOriginal(offset: Int) = when {
			offset < 5 -> offset
			offset < 7 -> offset - 1
			offset < 9 -> offset - 2
			else -> 8
		}
	}

	TransformedText(AnnotatedString(out), numberOffsetTranslator)
}