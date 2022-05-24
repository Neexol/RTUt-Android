package ru.neexol.rtut.presentation.screens.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.GroupTextField

@Composable
fun InitialGroupChoose(onNewGroup: ((String) -> Unit)) {
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
				GroupTextField(stringResource(R.string.group_pattern), onNewGroup)
			}
		}
	}
}