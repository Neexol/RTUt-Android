package ru.neexol.rtut.presentation.screens.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.GroupTextField
import ru.neexol.rtut.presentation.screens.settings.SettingsViewModel

@Composable
fun InitialGroupChoose(vm: SettingsViewModel) {
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

	Surface {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colors.primaryVariant),
			contentAlignment = Alignment.Center
		) {
			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Icon(
					modifier = Modifier.size(148.dp),
					tint = MaterialTheme.colors.primary,
					painter = painterResource(R.drawable.ic_launcher_foreground),
					contentDescription = null
				)
				Text(stringResource(R.string.enter_group))
				Spacer(Modifier.size(16.dp))
				GroupTextField(stringResource(R.string.group_pattern), vm::editGroup)
			}
			SnackbarHost(
				hostState = snackbarHostState,
				modifier = Modifier.align(Alignment.BottomCenter)
			)
		}
	}
}