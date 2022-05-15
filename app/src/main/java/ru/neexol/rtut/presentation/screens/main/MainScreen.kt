package ru.neexol.rtut.presentation.screens.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.neexol.rtut.presentation.screens.grouplessons.GroupLessonsScreen
import ru.neexol.rtut.presentation.screens.maps.MapsScreen
import ru.neexol.rtut.presentation.screens.settings.SettingsScreen
import ru.neexol.rtut.presentation.screens.teacherlessons.TeacherLessonsScreen

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
			composable(Screen.Schedule.route) { GroupLessonsScreen(hiltViewModel()) }
			composable(Screen.Teacher.route) { TeacherLessonsScreen(hiltViewModel()) }
			composable(Screen.Map.route) { MapsScreen(hiltViewModel()) }
			composable(Screen.Settings.route) { SettingsScreen(hiltViewModel()) }
		}
	}
}
