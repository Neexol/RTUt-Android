package ru.neexol.rtut.presentation.screens.notes.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.screens.notes.NotesViewModel

@Composable
internal fun Notes(vm: NotesViewModel, navController: NavController) {
	val coroutineScope = rememberCoroutineScope()
	val snackbarHostState = remember { SnackbarHostState() }
	LaunchedEffect(vm.uiState.message) {
		vm.uiState.message?.let {
			coroutineScope.launch {
				snackbarHostState.currentSnackbarData?.dismiss()
				snackbarHostState.showSnackbar(it)
				vm.clearMessage()
			}
		}
	}

	Column {
		LessonBar(vm.lesson, vm.isPublicType) {
			vm.isPublicType = it
		}
		vm.uiState.notes?.let { notes ->
			NotesList(
				if (vm.isPublicType) notes.second else notes.first,
				vm.author,
				navController
			)
		}
		FloatingActionButton(
			modifier = Modifier.padding(20.dp).align(Alignment.End),
			shape = RoundedCornerShape(16.dp),
			elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
			onClick = { navController.navigate("edit") }
		) {
			Icon(painter = painterResource(R.drawable.ic_add_24), contentDescription = null)
		}
		SnackbarHost(
			hostState = snackbarHostState,
			modifier = Modifier.animateContentSize()
		)
	}
}