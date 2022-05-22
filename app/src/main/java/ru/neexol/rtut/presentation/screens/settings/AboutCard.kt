package ru.neexol.rtut.presentation.screens.settings

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ru.neexol.rtut.BuildConfig
import ru.neexol.rtut.R
import ru.neexol.rtut.core.Constants

val mailIntent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri()).apply {
	putExtra(Intent.EXTRA_EMAIL, Constants.MAIL_ADDRESS)
}
val telegramIntent = Intent(Intent.ACTION_VIEW, Constants.TELEGRAM_URL.toUri())
val githubIntent = Intent(Intent.ACTION_VIEW, Constants.GITHUB_URL.toUri())

@Composable
fun AboutCard() {
	val context = LocalContext.current

	Surface(
		modifier = Modifier.fillMaxWidth(),
		color = MaterialTheme.colors.surface.copy(alpha = ContentAlpha.disabled),
		shape = MaterialTheme.shapes.medium
	) {
		Column(
			modifier = Modifier
				.padding(top = 20.dp, bottom = 10.dp)
				.padding(horizontal = 20.dp),
			verticalArrangement = Arrangement.spacedBy(5.dp)
		) {
			Text(text = "${stringResource(R.string.app_name)} ${BuildConfig.VERSION_NAME}")
			Text(
				modifier = Modifier.alpha(ContentAlpha.medium),
				text = stringResource(R.string.about_app),
				style = MaterialTheme.typography.body2
			)
			Row(Modifier.align(Alignment.End)) {
				IconButton(onClick = { context.startActivity(mailIntent) }) {
					Icon(
						imageVector = Icons.Filled.Email,
						contentDescription = null,
						tint = MaterialTheme.colors.primary
					)
				}
				IconButton(onClick = { context.startActivity(telegramIntent) }) {
					Icon(
						painter = painterResource(R.drawable.ic_telegram_24),
						contentDescription = null,
						tint = MaterialTheme.colors.primary
					)
				}
				IconButton(onClick = { context.startActivity(githubIntent) }) {
					Icon(
						painter = painterResource(R.drawable.ic_github_24),
						contentDescription = null,
						tint = MaterialTheme.colors.primary
					)
				}
			}
		}
	}
}