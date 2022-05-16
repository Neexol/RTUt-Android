package ru.neexol.rtut.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import ru.neexol.rtut.presentation.screens.main.MainScreen
import ru.neexol.rtut.presentation.theme.RTUtTheme

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { RTUtTheme { MainScreen() } }
	}
}