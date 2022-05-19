package ru.neexol.rtut.presentation.screens.notes

import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun NotesScreen(vm: NotesViewModel, isHidden: Boolean) {
	val navController = rememberNavController()

	LaunchedEffect(isHidden) {
		if (isHidden) {
			navController.popBackStack("list", false)
		}
	}

	NavHost(navController, "list", Modifier.imePadding()) {
		composable("list") { Notes(vm, navController) }
		composable(
			route = "edit?note={note}",
			arguments = listOf(navArgument("note") { nullable = true })
		) { backStackEntry ->
			EditNote(
				vm,
				navController,
				backStackEntry.arguments?.getString("note")?.let { Json.decodeFromString(it) }
			)
		}
	}
}



