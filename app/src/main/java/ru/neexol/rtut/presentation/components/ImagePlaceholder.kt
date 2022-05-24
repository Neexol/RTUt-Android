package ru.neexol.rtut.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImagePlaceholder(
	@DrawableRes iconId: Int,
	@StringRes labelId: Int
) {
	Box(
		modifier = Modifier.fillMaxSize().imePadding(),
		contentAlignment = Alignment.Center
	) {
		Column(
			modifier = Modifier.padding(horizontal = 90.dp).alpha(ContentAlpha.medium),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Icon(
				modifier = Modifier.size(80.dp),
				painter = painterResource(iconId),
				contentDescription = stringResource(labelId)
			)
			Text(
				text = stringResource(labelId),
				fontSize = 20.sp,
				textAlign = TextAlign.Center
			)
		}
	}
}