package ru.neexol.rtut.presentation.screens.main.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.neexol.rtut.presentation.screens.settings.SettingsViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen() {
	val vm: SettingsViewModel = viewModel()
	Surface(
		modifier = Modifier.fillMaxSize(),
		color = MaterialTheme.colors.primaryVariant
	) {
		Crossfade(vm.groupUiState.group) { group ->
			when (group?.isEmpty()) {
				null -> {}
				true -> InitialGroupChoose(vm::editGroup)
				else -> {
					val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
					val isSheetHidden by remember {
						derivedStateOf { !sheetState.isVisible }
					}

					Column(Modifier.navigationBarsPadding()) {
						StatusBar(!isSheetHidden)
						Screens(sheetState, isSheetHidden, vm)
					}
				}
			}
		}
	}
}