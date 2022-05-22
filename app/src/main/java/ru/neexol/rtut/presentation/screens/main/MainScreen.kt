package ru.neexol.rtut.presentation.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.presentation.screens.map.MapScreen
import ru.neexol.rtut.presentation.screens.notes.NotesScreen
import ru.neexol.rtut.presentation.screens.notes.NotesViewModel
import ru.neexol.rtut.presentation.screens.schedule.ScheduleScreen
import ru.neexol.rtut.presentation.screens.settings.SettingsScreen
import ru.neexol.rtut.presentation.screens.teacher.TeacherScreen
import ru.neexol.rtut.presentation.theme.backgroundVariant

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen() {
	val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
	val isSheetHidden by remember {
		derivedStateOf { !sheetState.isVisible }
	}

	Column(Modifier.navigationBarsPadding()) {
		StatusBar(!isSheetHidden)
		Screens(sheetState, isSheetHidden)
	}
}

@Composable
fun StatusBar(isScrim: Boolean) {
	Box {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.background(MaterialTheme.colors.backgroundVariant)
		) {
			Box(
				modifier = Modifier
					.offset(x = 64.dp + 8.dp)
					.background(MaterialTheme.colors.primaryVariant)
					.padding(WindowInsets.statusBars.asPaddingValues())
					.width(64.dp)
			)
		}
		AnimatedVisibility(
			visible = isScrim,
			enter = fadeIn(),
			exit = fadeOut()
		) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.background(Color.Black.copy(alpha = 0.3f))
					.padding(WindowInsets.statusBars.asPaddingValues())
			)
		}
	}
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Screens(sheetState: ModalBottomSheetState, isSheetHidden: Boolean) {
	val mainNavController = rememberNavController()

	val notesVM = viewModel<NotesViewModel>()
	LaunchedEffect(isSheetHidden) {
		if (isSheetHidden) {
			notesVM.clearState()
		}
	}
	val coroutineScope = rememberCoroutineScope()
	val showNotes: (Lesson, String) -> Unit = { l, w ->
		notesVM.setLesson(l, w)
		coroutineScope.launch { sheetState.show() }
	}

	ModalBottomSheetLayout(
		sheetState = sheetState,
		sheetContent = { NotesScreen(notesVM, isSheetHidden) },
		scrimColor = Color.Black.copy(alpha = 0.3f)
	) {
		Scaffold(
			bottomBar = { ScreensBottomBar(mainNavController) }
		) { innerPadding ->
			NavHost(mainNavController, Screen.Schedule.route, Modifier.padding(innerPadding)) {
				composable(Screen.Schedule.route) { ScheduleScreen { l, w -> showNotes(l, w) } }
				composable(Screen.Teacher.route) { TeacherScreen() }
				composable(Screen.Map.route) { MapScreen() }
				composable(Screen.Settings.route) { SettingsScreen() }
			}
		}
	}

	BackHandler(!isSheetHidden) {
		coroutineScope.launch {
			sheetState.hide()
		}
	}
}