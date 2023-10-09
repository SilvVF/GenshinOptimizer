package io.silv.genshinop.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.silv.genshinop.ui.composables.SelectedOption.*

@Composable
fun RowScope.Gap(width: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(width))
}

@Composable
fun ColumnScope.Gap(height: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(height))
}

@Composable
fun LazyItemScope.Gap(padding: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.size(padding))
}

enum class SelectedOption {
    None, Left, Right
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTag(
    leftText: String,
    rightText: String,
    height: Dp = 34.dp,
    shape: Shape = RoundedCornerShape(50),
    selectedOption: SelectedOption,
    onOptionSelected: (SelectedOption)  -> Unit,
    modifier: Modifier
) {
    Row(
        modifier
            .clip(shape)
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                shape
            )
            .height(height)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(
            visible = selectedOption == Left || selectedOption == None,
        ) {
            FilterChip(
                selected = selectedOption == Left,
                onClick = { onOptionSelected(Left) },
                label = { Text(text = leftText) },
                shape = shape,
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.Transparent
                ),
            )
        }
        AnimatedVisibility(visible = selectedOption == None) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .width(0.5.dp)
                    .background(Color.LightGray)
            )
        }
        AnimatedVisibility(
            visible = selectedOption == Right || selectedOption == None,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally(),
        ) {
            FilterChip(
                selected = selectedOption == Right,
                onClick = { onOptionSelected(Right) },
                label = { Text(text = rightText) },
                shape = shape,
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun TriStateCheckboxRow(
    state: ToggleableState,
    toggleState: (ToggleableState) -> Unit,
    rowText: String,
    modifier: Modifier = Modifier,
    rowTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    disabled: Boolean = false,
) {
    Row(
        modifier = modifier.clickable {
            toggleStateIfAble(disabled, state, toggleState)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TriStateCheckbox(
            state = state,
            onClick = { toggleStateIfAble(disabled, state, toggleState) },
            enabled = !disabled,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = MaterialTheme.colorScheme.surface,
            ),
        )
        Gap(4.dp)
        Text(
            text = rowText,
            color = if (!disabled)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.7f
                ),
            style = rowTextStyle
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriStateFilterChip(
    state: ToggleableState,
    toggleState: (ToggleableState) -> Unit,
    name: String,
    modifier: Modifier = Modifier,
    hideIcons: Boolean = false,
    labelTextStyle: TextStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
) {
    FilterChip(
        modifier = modifier,
        selected = state == ToggleableState.On || state == ToggleableState.Indeterminate,
        onClick = { toggleStateIfAble(false, state, toggleState) },
        leadingIcon = {
            if (!hideIcons) {
                if (state == ToggleableState.On) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                } else if (state == ToggleableState.Indeterminate) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        },
        shape = RoundedCornerShape(100),
        label = { Text(text = name, style = labelTextStyle) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            selectedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
            selectedLabelColor = MaterialTheme.colorScheme.primary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.primary,
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            selectedBorderColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
        ),
    )
}

private fun toggleStateIfAble(disabled: Boolean, state: ToggleableState, toggleState: (ToggleableState) -> Unit) {
    if (!disabled) {
        val newState = when (state) {
            ToggleableState.On -> ToggleableState.Indeterminate
            ToggleableState.Indeterminate -> ToggleableState.Off
            ToggleableState.Off -> ToggleableState.On
        }
        toggleState(newState)
    }
}