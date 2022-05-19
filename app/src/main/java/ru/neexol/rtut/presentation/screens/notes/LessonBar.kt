package ru.neexol.rtut.presentation.screens.notes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
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
import ru.neexol.rtut.data.notes.models.NoteType
import ru.neexol.rtut.presentation.theme.bar

@Composable
fun LessonBar(
	lesson: Lesson?,
	selectedType: NoteTypeTabData,
	onSelectType: (NoteTypeTabData) -> Unit
) {
	Row(
		modifier = Modifier
			.padding(vertical = 10.dp)
			.padding(top = 10.dp)
			.height(56.dp),
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
				.padding(10.dp)
				.clickable(
					interactionSource = remember { MutableInteractionSource() },
					indication = null
				) {
					val type = NoteTypeTabData.Private.takeIf { selectedType != it }
						?: NoteTypeTabData.Public
					onSelectType(type)
				},
			verticalAlignment = Alignment.CenterVertically
		) {
			listOf(NoteTypeTabData.Private, NoteTypeTabData.Public).forEach {
				NoteTypeTab(it, selectedType == it)
			}
		}
	}
}

sealed class NoteTypeTabData(
	val enumType: NoteType,
	@StringRes val title: Int,
	@DrawableRes val icon: Int
) {
	object Private : NoteTypeTabData(NoteType.PRIVATE, R.string.private_notes, R.drawable.ic_private_24)
	object Public : NoteTypeTabData(NoteType.PUBLIC, R.string.public_notes, R.drawable.ic_public_24)
}

@Composable
private fun RowScope.NoteTypeTab(
	noteType: NoteTypeTabData,
	selected: Boolean
) {
	val rowModifier = if (selected) {
		Modifier
			.fillMaxHeight()
			.wrapContentWidth()
			.background(MaterialTheme.colors.primaryVariant, MaterialTheme.shapes.small)
			.padding(horizontal = 5.dp)
			.weight(1f)
	} else {
		Modifier.padding(horizontal = 5.dp)
	}
	Row(
		modifier = rowModifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.aligned(Alignment.End)
	) {
		Icon(
			painter = painterResource(noteType.icon),
			contentDescription = null,
			tint = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
		)
		AnimatedVisibility(selected) {
			Text(
				modifier = Modifier.padding(start = 4.dp),
				text = stringResource(noteType.title),
				style = MaterialTheme.typography.caption,
				color = MaterialTheme.colors.primary
			)
		}
	}
}