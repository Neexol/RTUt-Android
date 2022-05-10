package ru.neexol.rtut.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.neexol.rtut.presentation.grouplessons.GroupLessonsScreen
import ru.neexol.rtut.presentation.maps.MapsScreen
import ru.neexol.rtut.presentation.teacherlessons.TeacherLessonsScreen
import ru.neexol.rtut.presentation.theme.RTUtTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RTUtTheme {
				MainContainer()
			}
		}
	}
}

sealed class Screen(val route: String, val name: String, val icon: ImageVector) {
	object Group : Screen("group", "Group", Icons.Filled.List)
	object Teacher : Screen("teacher", "Teacher", Icons.Filled.Person)
	object Maps : Screen("maps", "Maps", Icons.Filled.Place)
}

@Composable
fun MainContainer() {
	val navController = rememberNavController()

	Scaffold(
		bottomBar = {
			BottomNavigation {
				val navBackStackEntry by navController.currentBackStackEntryAsState()
				val currentDestination = navBackStackEntry?.destination
				listOf(
					Screen.Group,
					Screen.Teacher,
					Screen.Maps
				).forEach { screen ->
					BottomNavigationItem(
						icon = { Icon(screen.icon, contentDescription = null) },
						label = { Text(screen.name) },
						selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
						onClick = {
							navController.navigate(screen.route) {
								popUpTo(navController.graph.findStartDestination().id) {
									saveState = true
								}
								launchSingleTop = true
								restoreState = true
							}
						}
					)
				}
			}
		}
	) { innerPadding ->
		NavHost(navController, Screen.Group.route, Modifier.padding(innerPadding)) {
			composable(Screen.Group.route) { GroupLessonsScreen(hiltViewModel()) }
			composable(Screen.Teacher.route) { TeacherLessonsScreen(hiltViewModel()) }
			composable(Screen.Maps.route) { MapsScreen(hiltViewModel()) }
		}
	}
}