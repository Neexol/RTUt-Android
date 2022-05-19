package ru.neexol.rtut.presentation.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.neexol.rtut.R
import ru.neexol.rtut.data.notes.models.Note
import ru.neexol.rtut.presentation.theme.bar

@Composable
fun EditNote(vm: NotesViewModel, navController: NavController, note: Note?) {
	LaunchedEffect(note) {
		vm.setNote(note)
	}
	Column(Modifier.background(MaterialTheme.colors.primaryVariant)) {
		TopBar(vm, navController, note)
		NoteText(vm)
		BottomBar(vm, navController)
	}
}

@Composable
private fun TopBar(vm: NotesViewModel, navController: NavController, note: Note?) {
	Row(
		modifier = Modifier
			.padding(horizontal = 10.dp)
			.padding(top = 10.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		IconButton(onClick = { navController.popBackStack() }) {
			Icon(
				painter = painterResource(R.drawable.ic_arrow_back_24),
				contentDescription = null
			)
		}
		Text(
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 20.dp),
			text = stringResource(R.string.note),
			style = MaterialTheme.typography.h6
		)
		if (note != null) {
			IconButton(
				onClick = {
					vm.deleteNote()
					navController.popBackStack()
				}
			) {
				Icon(
					painter = painterResource(R.drawable.ic_delete_24),
					contentDescription = null
				)
			}
		}
	}
}

@Composable
private fun ColumnScope.NoteText(vm: NotesViewModel) {
	TextField(
		modifier = Modifier
			.weight(1f)
			.fillMaxWidth(),
		value = vm.noteText,
		onValueChange = {vm.noteText = it.trimStart()},
		placeholder = { Text(stringResource(R.string.note_text)) },
		keyboardOptions = KeyboardOptions(
			capitalization = KeyboardCapitalization.Sentences
		),
		colors = TextFieldDefaults.textFieldColors(
			backgroundColor = MaterialTheme.colors.primaryVariant,
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
			disabledIndicatorColor = Color.Transparent
		)
	)
}

@Composable
private fun BottomBar(vm: NotesViewModel, navController: NavController) {
	Row(
		modifier = Modifier
			.background(MaterialTheme.colors.bar)
			.padding(vertical = 10.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Spacer(Modifier.size(10.dp))
		ToggleButton(
			painterResource(R.drawable.ic_public_24),
			stringResource(R.string.public_note),
			vm.typeToggled,
			vm::toggleType
		)
		Spacer(Modifier.size(10.dp))
		ToggleButton(
			painterResource(R.drawable.ic_all_weeks_note_24),
			stringResource(R.string.for_all_weeks),
			vm.weekToggled,
			vm::toggleWeek
		)
		Spacer(Modifier.weight(1f))
		TextButton(
			modifier = Modifier.padding(end = 16.dp),
			enabled = vm.noteText.isNotEmpty(),
			onClick = {
				vm.putNote()
				navController.popBackStack()
			}
		) {
			Text(
				text = stringResource(R.string.save),
				style = MaterialTheme.typography.body1
			)
		}
	}
}

@Composable
private fun ToggleButton(icon: Painter, text: String, toggled: Boolean, onToggle: () -> Unit) {
	val rowModifier = if (toggled) {
		Modifier.background(MaterialTheme.colors.primaryVariant, MaterialTheme.shapes.small)
	} else {
		Modifier.border(
			1.dp,
			MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled),
			MaterialTheme.shapes.small
		)
	}
	val color = if (toggled) {
		MaterialTheme.colors.primary
	} else MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)

	Row(
		modifier = rowModifier
			.height(36.dp)
			.padding(5.dp)
			.clickable(onClick = onToggle),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			painter = icon,
			contentDescription = null,
			tint = color
		)
		Text(
			modifier = Modifier.padding(start = 4.dp),
			text = text,
			style = MaterialTheme.typography.caption,
			color = color
		)
	}
}