@file:OptIn(ExperimentalMaterial3Api::class)

package io.silv.genshinop.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SheetValue.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: (Modifier.() -> Modifier)? = null,
): Modifier {
    return if (condition) {
        then(ifTrue(Modifier))
    } else if (ifFalse != null) {
        then(ifFalse(Modifier))
    } else {
        this
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun Modifier.snapToPositionDraggable(
    state: DraggableBottomBarState,
    fillMaxHeight: Float
) = composed {

    val density = LocalDensity.current
    val config = LocalConfiguration.current

    val dragState = rememberDraggableState {
        state.onDrag(
            with(density) {
                it.toDp().value
            }
        )
    }

    // setting height to 0.dp while hidden to avoid
    // consuming drags gestures
    return@composed this
        .onSizeChanged {
            if (it.height > 0) {
                state.maxHeight = with(density) {
                    it.height.toDp().value
                        .roundToInt()
                        .toFloat()
                }
            }
        }
        .conditional(
            condition = state.progress == Hidden,
            ifTrue = {
               height(0.dp)
            },
            ifFalse = {
                fillMaxHeight(fillMaxHeight)
            }
        )
        .draggable(
            dragState,
            Orientation.Vertical,
            onDragStopped = {
                state.onDragEnd()
            }
        )
        .offset(
            y = state.offset.value.dp
        )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDraggableBottomBarState(
    scope: CoroutineScope = rememberCoroutineScope(),
    partialExpandFraction: Float,
    start: SheetValue = PartiallyExpanded
) = rememberSaveable(
    scope,
    partialExpandFraction,
    start,
    saver = Saver(
        save = {
            it.progress
        },
        restore = {
            DraggableBottomBarState(
                scope, partialExpandFraction, it
            )
        }
    ),
) {
    DraggableBottomBarState(scope, partialExpandFraction, start)
}

@OptIn(ExperimentalMaterial3Api::class)
class DraggableBottomBarState(
    private val scope: CoroutineScope,
    private val partialExpandFraction: Float,
    start: SheetValue = PartiallyExpanded
) {
    var maxHeight by mutableFloatStateOf(Float.MAX_VALUE)

    private val partialExpandHeight by derivedStateOf {
        maxHeight * partialExpandFraction
    }

    init {
        scope.launch {
            snapshotFlow { maxHeight }.collectLatest {
                snapProgressTo(progress)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    var progress by mutableStateOf(start)
        private set

    @OptIn(ExperimentalMaterial3Api::class)
    val offset = Animatable(
        // when restoring state set the Animatable to the correct progress value.
        when(progress) {
            Hidden -> maxHeight
            Expanded -> 0f
            PartiallyExpanded -> maxHeight - partialExpandHeight
        }
    )

    fun onDrag(deltaAsDp: Float) {
        scope.launch {
            offset.snapTo(
                (offset.value + deltaAsDp).coerceAtLeast(0f)
            )
            progress = when {
                offset.value < maxHeight - partialExpandHeight -> Expanded
                else -> if (offset.value >  maxHeight - partialExpandHeight / 2) Hidden else PartiallyExpanded
            }
        }
    }

    suspend fun onDragEnd() {
        when (progress) {
            Hidden -> offset.animateTo(maxHeight)
            Expanded -> offset.animateTo(0f)
            PartiallyExpanded -> offset.animateTo(maxHeight - partialExpandHeight)
        }
    }

    fun snapProgressTo(sheetValue: SheetValue) {
        scope.launch {
            // order matters here, if the progress is not set first when prev hidden
            // the height will not animate from the hidden state.
            // it is set to 0.dp while hidden to avoid detecting any swipe gestures.
            if (sheetValue != Hidden)
                progress = sheetValue
            when (sheetValue) {
                Hidden -> offset.animateTo(maxHeight)
                Expanded -> offset.animateTo(0f)
                PartiallyExpanded -> offset.animateTo(maxHeight - partialExpandHeight)
            }
            if (sheetValue == Hidden)
                progress = sheetValue
        }
    }
}
