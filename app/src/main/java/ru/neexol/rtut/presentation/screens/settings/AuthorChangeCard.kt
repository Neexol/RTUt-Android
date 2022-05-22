package ru.neexol.rtut.presentation.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R

@Composable
fun AuthorChangeCard(currentAuthor: String, onNewAuthor: (String?) -> Unit) {
	val clipboardManager = LocalClipboardManager.current

	Surface(
		modifier = Modifier.fillMaxWidth(),
		shape = MaterialTheme.shapes.medium
	) {
		Column(
			modifier = Modifier.padding(20.dp),
			verticalArrangement = Arrangement.spacedBy(5.dp)
		) {
			Text(text = stringResource(R.string.unique_identifier))
			Text(
				modifier = Modifier.alpha(ContentAlpha.medium),
				text = stringResource(R.string.unique_identifier_desc),
				style = MaterialTheme.typography.body2
			)
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 10.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				TextButton(
					onClick = { onNewAuthor(clipboardManager.getText()?.toString()) },
					shape = RoundedCornerShape(50)
				) {
					Text(
						text = stringResource(R.string.restore),
						style = MaterialTheme.typography.body2
					)
				}
				Button(
					onClick = { clipboardManager.setText(AnnotatedString(currentAuthor)) },
					shape = RoundedCornerShape(50)
				) {
					Row(
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(
							painter = painterResource(R.drawable.ic_copy_24),
							contentDescription = stringResource(R.string.copy)
						)
						Text(
							modifier = Modifier.padding(start = 8.dp),
							text = stringResource(R.string.copy),
							style = MaterialTheme.typography.body2
						)
					}
				}
			}
		}
	}
}