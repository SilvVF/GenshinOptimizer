import androidx.compose.animation.core.Animatable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberExpandableBottomBarState(
    scope: CoroutineScope = rememberCoroutineScope(),
) = rememberSaveable(
    scope,
    saver = Saver(
        save = {state ->
            state.visible
        },
        restore = {
            ExpandableBottomBarState(scope, it)
        }
    )
) {
    ExpandableBottomBarState(scope)
}

@OptIn(ExperimentalMaterial3Api::class)
class ExpandableBottomBarState @OptIn(ExperimentalMaterial3Api::class) constructor(
    private val scope: CoroutineScope,
    initVisible: SheetValue = SheetValue.PartiallyExpanded
) {


    @OptIn(ExperimentalMaterial3Api::class)
    var visible by mutableStateOf(initVisible)

    var maxHeight by mutableStateOf(0.dp)

    var optionsHeight by  mutableStateOf(0.dp)

    private val offset = Animatable(0f)

    val offsetDp by derivedStateOf {
        offset.value.dp
    }

    init {
        scope.launch {
            snapshotFlow { visible }.collectLatest {
                if (visible == SheetValue.Hidden) {
                    offset.snapTo(0f)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onDrag(dragDp: Dp) {
        if (
            (visible == SheetValue.PartiallyExpanded)
            && (offset.value >= maxHeight.value / 3)
        ) {
            visible = SheetValue.Hidden
        }

        if (offset.value < 0 && abs(offset.value) >= 12.dp.value) {
            when (visible) {
                SheetValue.PartiallyExpanded -> visible = SheetValue.Expanded
                else -> Unit
            }
        }

        if (offset.value >= optionsHeight.value * 0.9f) {
            when (visible) {
                SheetValue.Expanded -> {
                    scope.launch {
                        visible = SheetValue.PartiallyExpanded
                        offset.snapTo(offset.value - optionsHeight.value)
                    }
                }
                else -> Unit
            }
        }

        scope.launch {
            if (visible == SheetValue.Expanded) {
                offset.snapTo(
                    (offset.value + dragDp.value).coerceAtLeast(0.dp.value)
                )
            } else {
                offset.snapTo((offset.value + dragDp.value))
            }
        }
    }

    fun onDragStop() {
        scope.launch {
            offset.animateTo(0f)
        }
    }
}
