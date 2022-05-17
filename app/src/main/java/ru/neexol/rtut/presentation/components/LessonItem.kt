package ru.neexol.rtut.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime

@Composable
fun LessonItem(lesson: Lesson?, time: LessonTime) {
	Surface(
		modifier = if (lesson == null) Modifier.alpha(ContentAlpha.disabled) else Modifier,
		shape = MaterialTheme.shapes.medium
	) {
		Row(Modifier.padding(16.dp)) {
			Time(time)
			Payload(lesson)
			Classroom(lesson?.classroom)
		}
	}
}

@Composable
private fun Time(time: LessonTime) {
	Column(Modifier.padding(top = 2.dp)) {
		Text(
			text = time.begin,
			style = MaterialTheme.typography.body2,
			color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
		)
		Text(
			modifier = Modifier.padding(top = 2.dp),
			text = time.end,
			style = MaterialTheme.typography.body2,
			color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
		)
	}
}

@Composable
private fun RowScope.Payload(lesson: Lesson?) {
	val lessonInfo = lesson?.run {
		name + if (type.isNotBlank()) ", ${type.uppercase()}" else ""
	}.toContent()

	Column(
		modifier = Modifier
			.weight(1f)
			.padding(horizontal = 10.dp)
	) {
		Text(
			text = lessonInfo,
			color = MaterialTheme.colors.onSurface
		)
		Text(
			modifier = Modifier.padding(top = 4.dp),
			text = lesson?.teacher.toContent(),
			style = MaterialTheme.typography.body2,
			color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
		)
	}
}

@Composable
private fun Classroom(text: String?) {
	Text(
		modifier = Modifier
			.background(MaterialTheme.colors.primary, MaterialTheme.shapes.small)
			.padding(vertical = 5.dp, horizontal = 8.dp),
		text = text?.uppercase().toContent(),
		style = MaterialTheme.typography.caption,
		color = MaterialTheme.colors.onPrimary
	)
}

private fun String?.toContent() = takeIf { !it.isNullOrBlank() } ?: "—"