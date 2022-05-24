package ru.neexol.rtut.presentation.screens.settings.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.neexol.rtut.presentation.screens.settings.SettingsViewModel

@Composable
fun SettingsScreen(vm: SettingsViewModel = hiltViewModel()) {
	val coroutineScope = rememberCoroutineScope()
	val snackbarHostState = remember { SnackbarHostState() }
	LaunchedEffect(vm.groupUiState.message) {
		vm.groupUiState.message?.let {
			coroutineScope.launch {
				snackbarHostState.currentSnackbarData?.dismiss()
				snackbarHostState.showSnackbar(it)
				vm.clearGroupMessage()
			}
		}
	}
	LaunchedEffect(vm.authorUiState.message) {
		vm.authorUiState.message?.let {
			coroutineScope.launch {
				snackbarHostState.currentSnackbarData?.dismiss()
				snackbarHostState.showSnackbar(it)
				vm.clearAuthorMessage()
			}
		}
	}

	Column {
		LogoBar()
		Box(Modifier.fillMaxSize()) {
			Column(
				modifier = Modifier
					.padding(20.dp)
					.verticalScroll(rememberScrollState()),
				verticalArrangement = Arrangement.spacedBy(14.dp)
			) {
				GroupChangeCard(
					currentGroup = vm.groupUiState.group ?: "",
					onNewGroup = { vm.editGroup(it) }
				)
				AuthorChangeCard(
					currentAuthor = vm.authorUiState.author ?: "",
					onNewAuthor = { vm.editAuthor(it) }
				)
				AboutCard()
			}
			SnackbarHost(
				hostState = snackbarHostState,
				modifier = Modifier.align(Alignment.BottomCenter)
			)
		}
	}
}