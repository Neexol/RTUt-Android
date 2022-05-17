package ru.neexol.rtut.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun FindTopBar(
	value: String,
	placeholder: String,
	onValueChange: (String) -> Unit,
	onImeAction: () -> Unit
) {
	val focusManager = LocalFocusManager.current
	
	BasicTextField(
		modifier = Modifier
			.fillMaxWidth()
			.height(56.dp)
			.background(MaterialTheme.colors.surface)
			.padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
		value = value,
		onValueChange = onValueChange,
		textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
		cursorBrush = SolidColor(LocalContentColor.current),
		singleLine = true,
		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
		keyboardActions = KeyboardActions {
			onImeAction()
			focusManager.clearFocus()
		},
		decorationBox = { innerTextField ->
			Row(
				modifier = Modifier
					.background(MaterialTheme.colors.background, MaterialTheme.shapes.medium)
					.padding(vertical = 10.dp, horizontal = 14.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					imageVector = Icons.Filled.Search,
					contentDescription = null,
					modifier = Modifier.alpha(ContentAlpha.medium)
				)
				Box(
					modifier = Modifier
						.padding(start = 10.dp)
						.height(24.dp),
					contentAlignment = Alignment.CenterStart
				) {
					if (value.isEmpty()) {
						Text(
							text = placeholder,
							modifier = Modifier.alpha(ContentAlpha.medium)
						)
					}
					innerTextField()
				}
			}
		}
	)
}