package ru.neexol.rtut.presentation.screens.main.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.presentation.screens.main.Screen
import ru.neexol.rtut.presentation.screens.map.ui.MapScreen
import ru.neexol.rtut.presentation.screens.notes.NotesViewModel
import ru.neexol.rtut.presentation.screens.notes.ui.NotesScreen
import ru.neexol.rtut.presentation.screens.schedule.ui.ScheduleScreen
import ru.neexol.rtut.presentation.screens.settings.ui.SettingsScreen
import ru.neexol.rtut.presentation.screens.teacher.ui.TeacherScreen

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
internal fun Screens(sheetState: ModalBottomSheetState, isSheetHidden: Boolean) {
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