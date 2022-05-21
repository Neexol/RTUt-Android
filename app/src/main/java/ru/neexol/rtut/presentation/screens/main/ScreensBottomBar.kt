package ru.neexol.rtut.presentation.screens.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.neexol.rtut.presentation.theme.backgroundVariant

@Composable
fun ScreensBottomBar(navController: NavController) {
	val screens = listOf(Screen.Schedule, Screen.Teacher, Screen.Map, Screen.Settings)
	Row(
		modifier = Modifier
			.height(66.dp)
			.fillMaxWidth()
			.background(MaterialTheme.colors.backgroundVariant)
			.padding(horizontal = 20.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		val navBackStackEntry by navController.currentBackStackEntryAsState()
		val currentDestination = navBackStackEntry?.destination
		screens.forEach { screen ->
			ScreenItem(
				screen = screen,
				selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
			) {
				navController.navigate(screen.route) {
					popUpTo(navController.graph.findStartDestination().id) {
						saveState = true
					}
					launchSingleTop = true
					restoreState = true
				}
			}
		}
	}
}

@Composable
private fun ScreenItem(
	screen: Screen,
	selected: Boolean,
	onClick: () -> Unit
) {
	val backgroundModifier = if (selected) Modifier.background(
		MaterialTheme.colors.primaryVariant,
		MaterialTheme.shapes.small
	) else Modifier
	Row(
		modifier = backgroundModifier
			.animateContentSize()
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null,
				onClick = onClick
			).padding(10.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			painter = painterResource(screen.icon),
			contentDescription = stringResource(screen.title),
			tint = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
		)
		if (selected) {
			Text(
				modifier = Modifier.padding(start = 5.dp),
				text = stringResource(screen.title),
				maxLines = 1,
				style = MaterialTheme.typography.caption,
				color = MaterialTheme.colors.primary
			)
		}
	}
}