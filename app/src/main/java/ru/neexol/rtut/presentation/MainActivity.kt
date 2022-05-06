package ru.neexol.rtut.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.neexol.rtut.presentation.grouplessons.GroupLessonsList
import ru.neexol.rtut.presentation.theme.RTUtTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RTUtTheme {
				Surface(
					modifier = Modifier.fillMaxSize()
				) {
					GroupLessonsList(vm = viewModel())
				}
			}
		}
	}
}