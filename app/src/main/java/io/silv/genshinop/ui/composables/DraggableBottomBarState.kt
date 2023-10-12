@file:OptIn(ExperimentalMaterial3Api::class)

package io.silv.genshinop.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SheetValue.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Modifier.snapToPositionDraggable(
    maxHeight: Float,
    partialExpandHeight: Float
) = composed {

    val density = LocalDensity.current

    val bottomBarState by rememberDraggableBottomBarState(
        maxHeight = maxHeight,
        partialExpandHeight = partialExpandHeight
    )

    val dragState = rememberDraggableState {
        bottomBarState.onDrag(
            with(density) {
                it.toDp().value
            }
        )
    }

    return@composed draggable(
            dragState,
            Orientation.Vertical,
            true,
            onDragStopped = {
                bottomBarState.onDragEnd()
            }
        )
        .offset(bottomBarState.offset.value.dp)
}

@Composable
fun rememberDraggableBottomBarState(
    scope: CoroutineScope = rememberCoroutineScope(),
    maxHeight: Float,
    partialExpandHeight: Float,
    start: SheetValue = PartiallyExpanded
) = rememberSaveable(
    scope, maxHeight, partialExpandHeight, start,
    stateSaver = Saver(
        save = {
            it.progress
        },
        restore = {
            DraggableBottomBarState(
                scope, maxHeight, partialExpandHeight, it
            )
        }
    ),
) {
    mutableStateOf(
        DraggableBottomBarState(scope, maxHeight, partialExpandHeight, start)
    )
}

class DraggableBottomBarState(
    private val scope: CoroutineScope,
    private val maxHeight: Float,
    private val partialExpandHeight: Float,
    private val start: SheetValue = PartiallyExpanded
) {
    @OptIn(ExperimentalMaterial3Api::class)
    var progress by mutableStateOf(start)
        private set

    @OptIn(ExperimentalMaterial3Api::class)
    val offset = Animatable(
        when(progress) {
            Hidden -> -maxHeight
            Expanded -> 0f
            PartiallyExpanded -> maxHeight - partialExpandHeight
        }
    )



    fun onDrag(delta: Float) {
        scope.launch {
            offset.snapTo(
                (offset.value + delta).coerceAtMost(maxHeight)
            )
            progress = when {
                offset.value > partialExpandHeight -> Expanded
                else -> if (offset.value < partialExpandHeight / 3) Hidden else PartiallyExpanded
            }
        }
    }

    suspend fun onDragEnd() {
        when (progress) {
            Hidden -> offset.animateTo(-maxHeight)
            Expanded -> offset.animateTo(maxHeight)
            PartiallyExpanded -> offset.animateTo(partialExpandHeight)
        }
    }
}