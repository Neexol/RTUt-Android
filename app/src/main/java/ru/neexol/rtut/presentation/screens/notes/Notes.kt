package ru.neexol.rtut.presentation.screens.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.neexol.rtut.R
import ru.neexol.rtut.data.notes.models.Note
import ru.neexol.rtut.data.notes.models.NoteType

@Composable
fun Notes(vm: NotesViewModel, navController: NavController) {
	val uiState = vm.uiState
	var selectedType by rememberSaveable(
		saver = Saver(
			save = { it.value.enumType },
			restore = { mutableStateOf(
				if (it == NoteType.PRIVATE) NoteTypeTabData.Private else NoteTypeTabData.Public
			) }
		)
	) { mutableStateOf<NoteTypeTabData>(NoteTypeTabData.Private) }

	Box {
		Column(Modifier.padding(horizontal = 20.dp)) {
			LessonBar(vm.lesson, selectedType) {
				selectedType = it
			}
			uiState.notes?.let { notes ->
				NotesList(
					notes.filter { it.type == selectedType.enumType },
					vm.author,
					navController
				)
			}
			Spacer(Modifier.height(86.dp))
		}
		FloatingActionButton(
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(20.dp),
			shape = RoundedCornerShape(16.dp),
			elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
			onClick = {
				vm.typeToggled = selectedType == NoteTypeTabData.Public
				navController.navigate("edit")
			}
		) {
			Icon(painter = painterResource(R.drawable.ic_add_24), contentDescription = null)
		}
	}
}

@Composable
private fun NotesList(notes: List<Note>, author: String, navController: NavController) {
	if (notes.isEmpty()) {
		val color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 10.dp)
				.border(BorderStroke(1.dp, color), RoundedCornerShape(5.dp)),
		) {
			Text(
				modifier = Modifier.padding(14.dp),
				text = stringResource(R.string.no_notes),
				color = color
			)
		}
	} else {
		LazyColumn(
			modifier = Modifier.clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
			contentPadding = PaddingValues(vertical = 10.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp)
		) {
			items(notes) { note ->
				val canEdit = author == note.authorId
				val surfaceModifier = if (canEdit) {
					Modifier.clickable {
						navController.navigate("edit?note=" + Json.encodeToString(note))
					}
				} else Modifier

				Surface(
					modifier = surfaceModifier.fillMaxWidth(),
					color = MaterialTheme.colors.primaryVariant,
					shape = RoundedCornerShape(5.dp),
				) {
					val borderColor = MaterialTheme.colors.primary
					Row(
						modifier = Modifier.drawWithContent {
							drawContent()
							drawRect(
								color = borderColor,
								size = Size(4.dp.toPx(), size.height)
							)
						},
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(
							modifier = Modifier
								.padding(14.dp)
								.weight(1f),
							text = note.text,
							color = MaterialTheme.colors.onSurface
						)
						Column(Modifier.padding(10.dp)) {
							val isAllWeeks = note.weeks.contains(' ')
							if (isAllWeeks) {
								Icon(
									painter = painterResource(R.drawable.ic_all_weeks_note_24),
									contentDescription = null,
									tint = MaterialTheme.colors.primary
								)
							}
							if (canEdit) {
								if (isAllWeeks) {
									Spacer(Modifier.size(10.dp))
								}
								Icon(
									painter = painterResource(R.drawable.ic_can_edit_note_24),
									contentDescription = null,
									tint = MaterialTheme.colors.primary
								)
							}
						}
					}
				}
			}
		}
	}
}