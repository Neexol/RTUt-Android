package ru.neexol.rtut.presentation.screens.notes

import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NotesScreen(vm: NotesViewModel) {
	val navController = rememberNavController()
	NavHost(navController, "list", Modifier.imePadding()) {
		composable("list") { Notes(vm, navController) }
		composable("edit") { EditNote(vm, navController) }
	}
}



