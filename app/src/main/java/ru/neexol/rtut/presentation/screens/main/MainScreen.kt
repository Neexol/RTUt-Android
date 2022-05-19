package ru.neexol.rtut.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import ru.neexol.rtut.presentation.screens.map.MapScreen
import ru.neexol.rtut.presentation.screens.notes.NotesScreen
import ru.neexol.rtut.presentation.screens.notes.NotesViewModel
import ru.neexol.rtut.presentation.screens.schedule.ScheduleScreen
import ru.neexol.rtut.presentation.screens.settings.SettingsScreen
import ru.neexol.rtut.presentation.screens.teacher.TeacherScreen

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen() {
	val navController = rememberNavController()

	val notesVM = viewModel<NotesViewModel>()
	val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
	val coroutineScope = rememberCoroutineScope()
	val showNotes: (Lesson, String) -> Unit = { l, w ->
		notesVM.setLesson(l, w)
		coroutineScope.launch { sheetState.show() }
	}

	ModalBottomSheetLayout(
		sheetState = sheetState,
		sheetContent = { NotesScreen(notesVM) },
		scrimColor = Color.Black.copy(alpha = 0.3f)
	) {
		Scaffold(
			bottomBar = { ScreensBottomBar(navController) }
		) { innerPadding ->
			NavHost(navController, Screen.Schedule.route, Modifier.padding(innerPadding)) {
				composable(Screen.Schedule.route) { ScheduleScreen { l, w -> showNotes(l, w) } }
				composable(Screen.Teacher.route) { TeacherScreen() }
				composable(Screen.Map.route) { MapScreen() }
				composable(Screen.Settings.route) { SettingsScreen() }
			}
		}
	}
}
