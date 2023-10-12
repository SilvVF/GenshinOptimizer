package io.silv.genshinop

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.More
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import genshin.Artifact
import genshin.Character
import io.silv.genshinop.data.database.GenshinDbRepository
import io.silv.genshinop.ui.composables.CharCardSizingType
import io.silv.genshinop.ui.composables.CharacterInfoCard
import io.silv.genshinop.ui.composables.Element
import io.silv.genshinop.ui.composables.ElementFilterRow
import io.silv.genshinop.ui.composables.SearchTopAppBar
import io.silv.genshinop.ui.composables.SettingsDialog
import io.silv.genshinop.ui.composables.SettingsDialogThemeChooserRow
import io.silv.genshinop.ui.composables.element
import io.silv.genshinop.ui.composables.rememberDraggableBottomBarState
import io.silv.genshinop.ui.composables.snapToPositionDraggable
import io.silv.genshinop.ui.theme.GenshinopTheme
import kotlinx.coroutines.flow.combine
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.inject

data class CharacterWithArtifacts(
    val character: Character,
    val artifacts: List<Artifact>
)

class MainActivity : ComponentActivity() {

    private val dbRepo by inject<GenshinDbRepository>()
    private val json by inject<Json>()

    private val characterWithArtifacts = dbRepo.observeAllCharacters()
        .combine(dbRepo.observeAllArtifacts()) { characters, artifacts ->
            characters.map { character ->
                CharacterWithArtifacts(
                    character = character,
                    artifacts = artifacts.filter { it.location == character.id }
                )
            }
        }

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

            val allCharacters by characterWithArtifacts.collectAsState(initial = emptyList())
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
                mutableIntStateOf(1)
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


            val scope = rememberCoroutineScope()


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
                    DisplayOptions(
                        gridCells = gridCells,
                        changeCells = { gridCells = it },
                        onDismiss = {
                            displayOptionsVisible = false
                        }
                    )
                }
                var elementFilter by rememberSaveable {
                    mutableStateOf<Element?>(null)
                }

                var searching by rememberSaveable {
                    mutableStateOf(false)
                }
                var searchText by rememberSaveable {
                    mutableStateOf("")
                }

                val characters by remember(allCharacters, elementFilter, searchText) {
                    derivedStateOf {
                        val elementFiltered = if (elementFilter == null) {
                            allCharacters
                        } else {
                            allCharacters.filter { character ->
                                character.character.element() == elementFilter
                            }
                        }
                        return@derivedStateOf if (searchText.isNotBlank()) {
                            elementFiltered.filter { it.character.id.contains(searchText, true) }
                        } else {
                            elementFiltered
                        }
                    }
                }

                val bottomBarState = rememberDraggableBottomBarState(
                    maxHeight = 120f + 60f,
                    partialExpandHeight = 60f
                )

                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
                val navBarHeight = 80.dp

                Scaffold(
                    contentWindowInsets = ScaffoldDefaults.contentWindowInsets
                        .exclude(WindowInsets.statusBars),
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        SearchTopAppBar(
                            scrollBehavior = scrollBehavior,
                            onSearchText = {
                                  searchText = it
                            },
                            color = Color.Transparent,
                            navigationIconLabel = "",
                            onNavigationIconClicked = { searching = false },
                            actions = {
                               IconButton(onClick = { bottomBarState.snapProgressTo(Expanded) }) {
                                   Icon(imageVector = Icons.Filled.FilterList, null)
                               }
                            },
                            searchText = searchText,
                            showTextField = searching,
                            onSearchChanged = {
                                searching = it
                            },
                        )
                    },
                    bottomBar = {
                        Column(Modifier.fillMaxWidth()) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .snapToPositionDraggable(
                                        state = bottomBarState
                                    )
                            ) {
                                LazyRow(
                                    Modifier
                                        .height(60.dp)
                                ) {
                                    ElementFilterRow {

                                    }
                                }
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 12.dp,
                                                topEnd = 12.dp
                                            )
                                        )
                                        .height(120.dp)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(6.dp)
                                ) {
                                    Row(
                                        Modifier
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.More,
                                            contentDescription = "Group characters by...",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .rotate(180f)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(text = "Group characters by...")
                                    }
                                    Row(
                                        Modifier
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Tune,
                                            contentDescription = "Options",
                                            modifier = Modifier
                                                .size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(text = "Display options")
                                    }
                                }
                            }
                            NavigationBar(
                                Modifier.height(navBarHeight)
                            ) {
                                IconButton(
                                    onClick = {
                                        bottomBarState.snapProgressTo(
                                            when(bottomBarState.progress) {
                                                Hidden -> PartiallyExpanded
                                                Expanded -> Hidden
                                                PartiallyExpanded -> Expanded
                                            }
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home"
                                    )
                                }
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(bottom = navBarHeight)
                    ) {
                        if (cardType == CharCardSizingType.List) {
                            LazyColumn {
                                item {
                                    Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding()))
                                }
                                items(
                                    characters,
                                    key = { character -> character.character.key }
                                ) { character ->
                                    CharacterInfoCard(
                                        onClick = {},
                                        character = character.character,
                                        artifacts = character.artifacts,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(90.dp)
                                            .animateItemPlacement(),
                                        sizing = CharCardSizingType.List
                                    )
                                    Spacer(Modifier.height(8.dp))
                                }
                            }
                        } else {
                            LazyVerticalGrid(
                                columns =  GridCells.Fixed(gridCells),
                                state = rememberLazyGridState()
                            ) {
                                item(span = { GridItemSpan(gridCells)}) {
                                    Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding()))
                                }
                                items(
                                    characters,
                                    key = { character -> character.character.key }
                                ) { character ->
                                    CharacterInfoCard(
                                        onClick = {},
                                        character = character.character,
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxWidth()
                                            .animateItemPlacement(),
                                        artifacts = character.artifacts,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayOptions(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    gridCells: Int,
    changeCells: (Int) -> Unit,
    onDismiss: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        sheetState.expand()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {},
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
                onClick = { changeCells(4) },
            )
            SettingsDialogThemeChooserRow(
                text = "Compact Grid",
                selected = gridCells == 3,
                onClick = {  changeCells(3) },
            )
            SettingsDialogThemeChooserRow(
                text = "Comfortable Grid",
                selected = gridCells == 2,
                onClick = { changeCells(2) },
            )
            SettingsDialogThemeChooserRow(
                text = "Full Card",
                selected = gridCells == 1,
                onClick = {  changeCells(1) },
            )
        }
    }
}


@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

