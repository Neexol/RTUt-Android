package ru.neexol.rtut.presentation.screens.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.neexol.rtut.presentation.screens.map.MapScreen
import ru.neexol.rtut.presentation.screens.schedule.ScheduleScreen
import ru.neexol.rtut.presentation.screens.settings.SettingsScreen
import ru.neexol.rtut.presentation.screens.teacher.TeacherScreen

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
	) {
		NavHost(navController, Screen.Schedule.route) {
			composable(Screen.Schedule.route) { ScheduleScreen(hiltViewModel()) }
			composable(Screen.Teacher.route)  { TeacherScreen(hiltViewModel()) }
			composable(Screen.Map.route)      { MapScreen(hiltViewModel()) }
			composable(Screen.Settings.route) { SettingsScreen(hiltViewModel()) }
		}
	}
}
