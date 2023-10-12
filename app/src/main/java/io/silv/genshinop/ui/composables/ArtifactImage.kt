package io.silv.genshinop.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import io.silv.genshinop.R

@Composable
fun ArtifactImage(
    setKey: String,
    slotKey: String,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = painterResource(
            id = rememberArtifactIdForKey(setKey = setKey, slotKey = slotKey)
        ),
        contentDescription = "$setKey $slotKey",
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

@Composable
private fun rememberArtifactIdForKey(setKey: String, slotKey: String): Int {
    return remember(setKey, slotKey) {
        when(setKey.lowercase()) {
            "blizzardstrayer" -> {
                when (slotKey.lowercase()) {
                    "flower" -> R.drawable.ui_relicicon_14001_4
                    "plume" -> R.drawable.ui_relicicon_14001_2
                    "sands" -> R.drawable.ui_relicicon_14001_5
                    "goblet" -> R.drawable.ui_relicicon_14001_1
                     else -> R.drawable.ui_relicicon_14001_3
                }
            }
            "emblemofseveredfate" -> {
                when (slotKey.lowercase()) {
                    "goblet" -> R.drawable.ui_relicicon_15020_1
                    "plume" -> R.drawable.ui_relicicon_15020_2
                    "circlet" -> R.drawable.ui_relicicon_15020_3
                    "flower" -> R.drawable.ui_relicicon_15020_4
                    else -> R.drawable.ui_relicicon_15020_5
                }
            }
            "crimsonwitchofflames" -> {
                when (slotKey.lowercase()) {
                    "goblet" -> R.drawable.ui_relicicon_15006_1
                    "plume" -> R.drawable.ui_relicicon_15006_2
                    "circlet" -> R.drawable.ui_relicicon_15006_3
                    "flower" -> R.drawable.ui_relicicon_15006_4
                    else -> R.drawable.ui_relicicon_15006_5
                }
            }
            else -> when (slotKey.lowercase()) {
                "flower" -> R.drawable.ui_relicicon_14001_4
                "plume" -> R.drawable.ui_relicicon_14001_2
                "sands" -> R.drawable.ui_relicicon_14001_5
                "goblet" -> R.drawable.ui_relicicon_14001_1
                else -> R.drawable.ui_relicicon_14001_3
            }
        }
    }
}