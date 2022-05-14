package ru.neexol.rtut.presentation.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
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
	var selected by remember { mutableStateOf(0) }
	val width = LocalConfiguration.current.screenWidthDp.dp.let { it / SCREENS.size }
	val offset by animateDpAsState(
		width * selected,
		spring(dampingRatio = Spring.DampingRatioLowBouncy)
	)
	val gradient = Brush.verticalGradient(
		0f to Color.Transparent,
		1f to Color(0x66000000)
	)

	Surface {
		Box(Modifier.fillMaxSize()) {
			Row (
				modifier = Modifier
					.zIndex(1f)
					.align(Alignment.BottomCenter)
					.height(64.dp)
					.background(gradient)
			) {
				SCREENS.forEachIndexed { index, screen ->
					Box(
						modifier = Modifier
							.weight(1f)
							.fillMaxHeight()
							.clickable(
								interactionSource = remember { MutableInteractionSource() },
								indication = null
							) {
								selected = index
								navController.navigate(screen.route) {
									popUpTo(navController.graph.findStartDestination().id) {
										saveState = true
									}
									launchSingleTop = true
									restoreState = true
								}
							}
					) {
						if (index == 0) {
							Canvas(
								modifier = Modifier
									.padding(vertical = 8.dp)
									.size(48.dp)
									.align(Alignment.Center)
									.absoluteOffset(x = offset)
							) {
								drawCircle(color = Color.Gray)
							}
						}
						Icon(
							modifier = Modifier.align(Alignment.Center),
							imageVector = screen.icon,
							contentDescription = null
						)
					}
				}
			}
			NavHost(navController, Screen.Group.route) {
				composable(Screen.Group.route) { GroupLessonsScreen(hiltViewModel()) }
				composable(Screen.Teacher.route) { TeacherLessonsScreen(hiltViewModel()) }
				composable(Screen.Maps.route) { MapsScreen(hiltViewModel()) }
				composable(Screen.Settings.route) { SettingsScreen(hiltViewModel()) }
			}
		}
	}
//	Scaffold(
//		bottomBar = {
//
//			BottomNavigation {
//				val navBackStackEntry by navController.currentBackStackEntryAsState()
//				val currentDestination = navBackStackEntry?.destination
//				listOf(
//					Screen.Group,
//					Screen.Teacher,
//					Screen.Maps,
//					Screen.Settings
//				).forEach { screen ->
//					BottomNavigationItem(
//						icon = { Icon(screen.icon, contentDescription = null) },
////						label = { Text(screen.name) },
//						selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//						onClick = {
//							navController.navigate(screen.route) {
//								popUpTo(navController.graph.findStartDestination().id) {
//									saveState = true
//								}
//								launchSingleTop = true
//								restoreState = true
//							}
//						}
//					)
//				}
//			}
//		}
//	) { innerPadding ->

//	}
}

sealed class Screen(val route: String, val name: String, val icon: ImageVector) {
	object Group : Screen("group", "Group", Icons.Filled.List)
	object Teacher : Screen("teacher", "Teacher", Icons.Filled.Person)
	object Maps : Screen("maps", "Maps", Icons.Filled.Place)
	object Settings : Screen("settings", "Settings", Icons.Filled.Settings)
}

private val SCREENS = listOf(
	Screen.Group,
	Screen.Teacher,
	Screen.Maps,
	Screen.Settings
)