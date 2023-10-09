package io.silv.genshinop

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.core.view.WindowCompat
import io.silv.genshinop.data.database.GenshinDbRepository
import io.silv.genshinop.ui.composables.CharCardSizingType
import io.silv.genshinop.ui.composables.CharacterInfoCard
import io.silv.genshinop.ui.composables.FilterTag
import io.silv.genshinop.ui.composables.Gap
import io.silv.genshinop.ui.composables.SelectedOption
import io.silv.genshinop.ui.composables.SettingsDialog
import io.silv.genshinop.ui.composables.SettingsDialogThemeChooserRow
import io.silv.genshinop.ui.theme.GenshinopTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {

    private val dbRepo by inject<GenshinDbRepository>()
    private val json by inject<Json>()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            WindowCompat.setDecorFitsSystemWindows(window, false)

            LaunchedEffect(key1 = Unit) {
                dbRepo.updateTables(
                    json.decodeFromString(database1data)
                )
            }

            val characters by dbRepo.observeAllCharacters().collectAsState(initial = emptyList())
            val weapons by dbRepo.observeAllWeapons().collectAsState(initial = emptyList())
            val artifacts by dbRepo.observeAllArtifacts().collectAsState(initial = emptyList())

            var displayOptionsVisible by remember {
                mutableStateOf(false)
            }

            var settingsVisible by remember {
                mutableStateOf(false)
            }

            var useDynamicColor by remember {
                mutableStateOf(false)
            }

            var theme by remember {
                mutableIntStateOf(0)
            }

            var gridCells by remember {
                mutableIntStateOf(4)
            }

            val cardType by remember {
                derivedStateOf {
                    when (gridCells) {
                        1 -> CharCardSizingType.Full
                        2 -> CharCardSizingType.SemiCompact
                        3 -> CharCardSizingType.Compact
                        else -> CharCardSizingType.List
                    }
                }
            }

            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(
                    initialValue = SheetValue.PartiallyExpanded,
                    skipHiddenState = false
                )
            )

            val scope = rememberCoroutineScope()

            fun showBottomSheet() {
                scope.launch {
                    scaffoldState.bottomSheetState.show()
                }
            }

            fun hideBottomSheet() {
                scope.launch {
                    scaffoldState.bottomSheetState.hide()
                }
            }


            if (settingsVisible) {
                SettingsDialog(
                    useDynamicColor = useDynamicColor,
                    theme = theme,
                    onDismiss = { settingsVisible = false },
                    onChangeDynamicColorPreference = { useDynamicColor = it},
                    onChangeDarkThemeConfig = { theme = it }
                )
            }

            GenshinopTheme(
                darkTheme = when (theme) {
                    0 -> isSystemInDarkTheme()
                    1 -> false
                    else -> true
                },
                dynamicColor = useDynamicColor
            ) {
                if (displayOptionsVisible) {
                    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

                    LaunchedEffect(key1 = Unit) {
                        sheetState.expand()
                    }

                    ModalBottomSheet(
                        onDismissRequest = { displayOptionsVisible = !displayOptionsVisible },
                        sheetState = sheetState,
                        dragHandle = {}
                    ) {
                        TabRow(
                            selectedTabIndex = 1,
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(0.3f)
                        ) {
                            Tab(
                                selected = true,
                                onClick = { /*TODO*/ }
                            ) {
                                Text("Display", modifier = Modifier.padding(4.dp))
                            }
                        }
                        Column(
                            Modifier
                                .selectableGroup()
                                .padding(4.dp)
                                .systemBarsPadding()
                        ) {
                            SettingsDialogThemeChooserRow(
                                text = "List",
                                selected = gridCells == 4,
                                onClick = { gridCells = 4 },
                            )
                            SettingsDialogThemeChooserRow(
                                text = "Compact Grid",
                                selected = gridCells == 3,
                                onClick = {  gridCells = 3 },
                            )
                            SettingsDialogThemeChooserRow(
                                text = "Comfortable Grid",
                                selected = gridCells == 2,
                                onClick = {  gridCells = 2 },
                            )
                            SettingsDialogThemeChooserRow(
                                text = "Full Card",
                                selected = gridCells == 1,
                                onClick = {  gridCells = 1 },
                            )
                        }
                    }
                }

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = {  },
                            actions = {
                                IconButton(onClick = ::showBottomSheet) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Options"
                                    )
                                }
                                IconButton(onClick = { settingsVisible = !settingsVisible }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "settings"
                                    )
                                }
                            }
                        )
                    },
                    sheetContainerColor = Color.Transparent,
                    sheetShape = RectangleShape,
                    sheetTonalElevation = 0.dp,
                    sheetShadowElevation = 0.dp,
                    sheetDragHandle = {},
                    sheetContent = {

                        val selectedList = remember {
                            mutableStateListOf(
                                SelectedOption.None, SelectedOption.None, SelectedOption.None, SelectedOption.None
                            )
                        }

                        LazyRow(
                            Modifier
                                .padding(bottom = 32.dp)
                        ) {
                            item {
                                androidx.compose.animation.AnimatedVisibility(
                                    visible = selectedList.fastAny { it != SelectedOption.None },
                                    exit = fadeOut() + slideOutHorizontally(),
                                    enter = fadeIn() + slideInHorizontally()
                                ) {
                                    AssistChip(
                                        shape = RoundedCornerShape(50),
                                        modifier = Modifier.height(34.dp),
                                        onClick = { selectedList.replaceAll { SelectedOption.None } },
                                        label = {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "Clear"
                                            )
                                        },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                }
                                Gap(padding = 4.dp)
                            }
                            itemsIndexed(selectedList) { i, selected ->
                                FilterTag(
                                    leftText = "l text $i",
                                    rightText = "r text $i",
                                    selectedOption = selected,
                                    onOptionSelected = { op ->
                                        selectedList[i] = if (op == selected) SelectedOption.None else op
                                    },
                                    modifier = Modifier.animateItemPlacement()
                                )
                                Gap(padding = 4.dp)
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(bottom = 32.dp)
                        ) {
                            IconButton(onClick = { displayOptionsVisible = !displayOptionsVisible }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Options",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Text(
                                "Display Options",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                gridCells = if (gridCells == 4) 1 else gridCells + 1
                            }
                            .padding(it.calculateTopPadding()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (cardType == CharCardSizingType.List) {
                            LazyColumn {
                                items(characters) { character ->
                                    CharacterInfoCard(
                                        onClick = {},
                                        character = character,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(90.dp),
                                        sizing = CharCardSizingType.List
                                    )
                                    Spacer(Modifier.height(8.dp))
                                }
                            }
                        } else {
                            LazyVerticalGrid(
                                columns =  GridCells.Fixed(gridCells),
                            ) {
                                items(characters) { character ->
                                    CharacterInfoCard(
                                        onClick = {},
                                        character = character,
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxWidth(),
                                        sizing = cardType
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GenshinopTheme {
        Greeting("Android")
    }
}