package ru.neexol.rtut.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(vm: SettingsViewModel = hiltViewModel()) {
	Column {
		LogoBar()
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
	}
}