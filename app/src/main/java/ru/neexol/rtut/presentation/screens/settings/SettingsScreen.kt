package ru.neexol.rtut.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(vm: SettingsViewModel) {
	val keyboardController = LocalSoftwareKeyboardController.current
	val group = vm.groupUiState.group
	val author = vm.authorUiState.author

	Column {
		Text("Group:")
		TextField(
			value = vm.newGroup,
			onValueChange = { vm.newGroup = formatGroup(it) },
			label = { Text(group.toString()) },
			singleLine = true,
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
			keyboardActions = KeyboardActions {
				vm.editGroup()
				keyboardController?.hide()
			},
			visualTransformation = groupTransformation
		)
		Divider()
		Text("Author:")
		TextField(
			value = vm.newAuthor,
			onValueChange = { vm.newAuthor = it },
			label = { Text(author.toString()) },
			singleLine = true,
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
			keyboardActions = KeyboardActions {
				vm.editAuthor()
				keyboardController?.hide()
			}
		)
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