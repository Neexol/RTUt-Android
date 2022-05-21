package ru.neexol.rtut.presentation.screens.notes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.R
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.presentation.theme.bar

@Composable
fun LessonBar(
	lesson: Lesson?,
	isPublicType: Boolean,
	onToggleType: (Boolean) -> Unit
) {
	Row(
		modifier = Modifier
			.padding(vertical = 10.dp)
			.padding(top = 10.dp)
			.height(58.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.width(4.dp)
				.fillMaxHeight()
				.background(MaterialTheme.colors.onSurface, MaterialTheme.shapes.small)
		)
		Text(
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 10.dp),
			text = lesson?.lessonWithType.toString(),
			maxLines = 3,
			overflow = TextOverflow.Ellipsis,
			style = MaterialTheme.typography.body2,
			color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
		)
		Row(
			modifier = Modifier
				.width(155.dp)
				.fillMaxHeight()
				.background(MaterialTheme.colors.bar, MaterialTheme.shapes.medium)
				.padding(horizontal = 10.dp)
				.clickable(
					interactionSource = remember { MutableInteractionSource() },
					indication = null
				) {
					onToggleType(!isPublicType)
				},
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			NoteTypeTab(R.string.private_notes, R.drawable.ic_private_24, !isPublicType)
			NoteTypeTab(R.string.public_notes, R.drawable.ic_public_24, isPublicType)
		}
	}
}

@Composable
private fun NoteTypeTab(
	titleId: Int,
	iconId: Int,
	selected: Boolean
) {
	val backgroundModifier = if (selected) Modifier.background(
		MaterialTheme.colors.primaryVariant,
		MaterialTheme.shapes.small
	) else Modifier
	Row(
		modifier = backgroundModifier
			.animateContentSize()
			.padding(5.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			painter = painterResource(iconId),
			contentDescription = stringResource(titleId),
			tint = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
		)
		if (selected) {
			Text(
				modifier = Modifier.padding(start = 5.dp),
				text = stringResource(titleId),
				maxLines = 1,
				style = MaterialTheme.typography.caption,
				color = MaterialTheme.colors.primary
			)
		}
	}
}