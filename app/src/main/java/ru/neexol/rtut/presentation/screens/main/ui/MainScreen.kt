package ru.neexol.rtut.presentation.screens.main.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen() {
	val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
	val isSheetHidden by remember {
		derivedStateOf { !sheetState.isVisible }
	}

	Column(Modifier.navigationBarsPadding()) {
		StatusBar(!isSheetHidden)
		Screens(sheetState, isSheetHidden)
	}
}