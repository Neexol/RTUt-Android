package ru.neexol.rtut.presentation.screens.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.theme.backgroundVariant

@Composable
internal fun LogoBar() {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.background(MaterialTheme.colors.backgroundVariant)
			.padding(horizontal = 8.dp)
			.padding(bottom = 12.dp)
	) {
		Box(
			modifier = Modifier
				.size(64.dp)
				.offset(x = 64.dp)
				.background(
					MaterialTheme.colors.primaryVariant,
					RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
				),
			contentAlignment = Alignment.Center
		) {
			Icon(
				painter = painterResource(R.drawable.ic_launcher_foreground),
				contentDescription = stringResource(R.string.app_name)
			)
		}
	}
}