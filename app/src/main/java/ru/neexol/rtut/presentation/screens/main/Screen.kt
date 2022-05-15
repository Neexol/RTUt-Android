package ru.neexol.rtut.presentation.screens.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.neexol.rtut.R

sealed class Screen(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
	object Schedule : Screen("schedule", R.string.schedule, R.drawable.ic_schedule_nav_24)
	object Teacher  : Screen("teacher" , R.string.teacher , R.drawable.ic_teacher_nav_24)
	object Map      : Screen("map"     , R.string.map     , R.drawable.ic_map_nav_24)
	object Settings : Screen("settings", R.string.settings, R.drawable.ic_settings_nav_24)
}