package ru.neexol.rtut.presentation.screens.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.GroupTextField

@Composable
internal fun GroupChangeCard(
	currentGroup: String,
	onNewGroup: (String) -> Unit
) {
	Surface(
		modifier = Modifier.fillMaxWidth(),
		shape = MaterialTheme.shapes.medium
	) {
		Row(
			modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(text = stringResource(R.string.group))
			GroupTextField(currentGroup, onNewGroup)
		}
	}
}