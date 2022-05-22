package ru.neexol.rtut.presentation.screens.notes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.screens.notes.NotesViewModel

@Composable
internal fun Notes(vm: NotesViewModel, navController: NavController) {
	val uiState = vm.uiState
	Box {
		Column(Modifier.padding(horizontal = 20.dp)) {
			LessonBar(vm.lesson, vm.isPublicType) {
				vm.isPublicType = it
			}
			uiState.notes?.let { notes ->
				NotesList(
					if (vm.isPublicType) notes.second else notes.first,
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
				navController.navigate("edit")
			}
		) {
			Icon(painter = painterResource(R.drawable.ic_add_24), contentDescription = null)
		}
	}
}