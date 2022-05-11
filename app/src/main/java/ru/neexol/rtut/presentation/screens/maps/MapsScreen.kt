package ru.neexol.rtut.presentation.screens.maps

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder

@Composable
fun MapsScreen(vm: MapsViewModel) {
	val imageLoader = ImageLoader.Builder(LocalContext.current)
		.components {
			add(SvgDecoder.Factory())
		}
		.build()

	val mapsRes by vm.mapsResourceFlow.collectAsState()
	mapsRes(
		onSuccess = {
			LazyColumn {
				items(it) { map ->
					AsyncImage(
						model = map,
						contentDescription = null,
						imageLoader = imageLoader
					)
				}
			}
		}
	)
}