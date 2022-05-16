package ru.neexol.rtut.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.neexol.rtut.presentation.screens.map.MapScreen
import ru.neexol.rtut.presentation.screens.schedule.ScheduleScreen
import ru.neexol.rtut.presentation.screens.settings.SettingsScreen
import ru.neexol.rtut.presentation.screens.teacher.TeacherScreen

@ExperimentalPagerApi
@Composable
fun MainScreen() {
	val navController = rememberNavController()

	Scaffold(
		bottomBar = {
			ScreensBottomBar(
				navController,
				listOf(Screen.Schedule, Screen.Teacher, Screen.Map, Screen.Settings)
			)
		}
	) { innerPadding ->
		NavHost(navController, Screen.Schedule.route, Modifier.padding(innerPadding)) {
			composable(Screen.Schedule.route) { ScheduleScreen(hiltViewModel()) }
			composable(Screen.Teacher.route)  { TeacherScreen(hiltViewModel()) }
			composable(Screen.Map.route)      { MapScreen(hiltViewModel()) }
			composable(Screen.Settings.route) { SettingsScreen(hiltViewModel()) }
		}
	}
}
