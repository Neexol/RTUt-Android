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
import ru.neexol.rtut.presentation.theme.bar

@Composable
fun ScreensBottomBar(navController: NavController, screens: List<Screen>) {
	Row(
		modifier = Modifier
			.height(66.dp)
			.fillMaxWidth()
			.background(MaterialTheme.colors.bar)
	) {
		val navBackStackEntry by navController.currentBackStackEntryAsState()
		val currentDestination = navBackStackEntry?.destination
		screens.forEachIndexed { index, screen ->
			ScreenItem(
				screen = screen,
				isEdge = index == 0 || index == screens.lastIndex,
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
private fun RowScope.ScreenItem(
	screen: Screen,
	isEdge: Boolean,
	selected: Boolean,
	onClick: () -> Unit
) {
	var boxModifier = Modifier
		.clickable(
			interactionSource = remember { MutableInteractionSource() },
			indication = null
		) { onClick() }
		.fillMaxHeight()
	boxModifier = if (selected) {
		boxModifier
			.padding(16.dp)
			.background(
				MaterialTheme.colors.primaryVariant,
				MaterialTheme.shapes.small
			)
	} else if (isEdge) {
		boxModifier.padding(horizontal = 24.dp)
	} else boxModifier.weight(weight = 1f)

	Box(boxModifier) {
		Row(
			modifier = Modifier
				.padding(5.dp)
				.height(24.dp)
				.align(Alignment.Center)
				.animateContentSize()
		) {
			Icon(
				painter = painterResource(screen.icon),
				contentDescription = null,
				tint = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
			)
			if (selected) {
				Text(
					modifier = Modifier
						.padding(start = 5.dp)
						.align(Alignment.CenterVertically),
					text = stringResource(screen.title),
					style = MaterialTheme.typography.caption,
					color = MaterialTheme.colors.primary
				)
			}
		}
	}
}