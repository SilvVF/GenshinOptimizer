package io.silv.genshinop.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import genshin.Character
import io.silv.genshinop.ui.theme.Anemo
import io.silv.genshinop.ui.theme.Cryo
import io.silv.genshinop.ui.theme.Dendro
import io.silv.genshinop.ui.theme.Electro
import io.silv.genshinop.ui.theme.GenshinopTheme
import io.silv.genshinop.ui.theme.Geo
import io.silv.genshinop.ui.theme.Hydro
import io.silv.genshinop.ui.theme.Pyro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterInfoCard(
    modifier: Modifier,
    onClick: () -> Unit,
    character: Character
) {

    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(
            topEnd = 12.dp,
            topStart = 12.dp,
        )
    ) {
        CardHeaderWithInfo(
            character = character
        )
        Text(text = "d")
        Text(text = "d")
        Text(text = "d")
        Text(text = "d")
        Text(text = "d")
        Text(text = "d")
        Text(text = "d")
    }
}

@Composable
private fun CardHeaderWithInfo(
    character: Character,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        NameCardImage(
            key = character.key,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillWidth
        )
        Column(
            Modifier
                .matchParentSize()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(Modifier.fillMaxWidth()) {
                CharacterImage(
                    key = character.key,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(0.4f)
                        .align(Alignment.Bottom),
                )
                Column(Modifier.fillMaxWidth()) {
                    TextWithElementBg(
                        character = character,
                        modifier = Modifier
                            .padding(horizontal = 22.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
        FadeBackground(start = 0.8f)
    }
}

@Composable
private fun TextWithElementBg(
    character: Character,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(60))
            .background(
                color = remember(character) { character.elementColor() }
            )
    ) {
        Text(
            text = character.key,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun BoxScope.FadeBackground(
    start: Float = 0f,
    end: Float = 1f,
    c1:Color = Color.Transparent,
    c2: Color = MaterialTheme.colorScheme.surfaceVariant
) {

    var height by remember {
        mutableIntStateOf(0)
    }

    Box(modifier = Modifier
        .matchParentSize()
        .onSizeChanged {
            height = it.height
        }
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(c1, c2),
                startY = height * start,
                endY = if (height == 1)
                    Float.POSITIVE_INFINITY
                else
                    height * end
            )
        )
    )
}

@Preview
@Composable
private fun CharacterInfoCardPreview() {
    GenshinopTheme {
        Column {
            var grid by rememberSaveable {
                mutableStateOf(false)
            }
            
            Button(onClick = { grid = !grid }, Modifier.fillMaxWidth()) {
               Text(text = "toggle layout") 
            }
            
            if (grid) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    Modifier.weight(1f)
                ) {
                    items(
                        listOf(
                            "barbara", "faruzan", "thoma", "kukishinobu", "shikanoinheizou", "sucrose",
                            "noelle", "bennett", "mika", "xingqiu", "lisa", "lynette", "yanfei", "beidou",
                            "razor", "rosaria", "kamisatoayaka", "qiqi", "diona", "kaedeharakazuha", "yelan",
                            "layla", "ganyu", "keqing", "sayu", "travelerhydro", "ningguang", "xiangling", "kirara",
                            "collei", "chongyun", "sangonomiyakokomi", "dori", "aloy", "kujousara", "kaeya", "amber",
                            "hutao", "candace", "diluc", "mona","yae", "yunjin", "gorou", "raidenshogun", "xinyan"
                        )
                    ) {
                        Column {
                            CharacterInfoCard(
                                modifier = Modifier,
                                onClick = {},
                                character = Character(
                                    it,
                                    it,
                                    (0L..4L).random(),
                                    false,
                                    (1..4).random().toLong(),
                                    "",
                                    infusionAura = "",
                                    level = 13L,
                                    talent_auto_lvl = 4L,
                                    talent_burst_lvl = 3L,
                                    talent_skill_lvl = 3L,
                                    team = emptyList()
                                )
                            )
                            Divider()
                        }
                    }
                }
            } else {
                LazyColumn(Modifier.weight(1f)) {
                    items(
                        listOf(
                            "barbara", "faruzan", "thoma", "kukishinobu", "shikanoinheizou", "sucrose",
                            "noelle", "bennett", "mika", "xingqiu", "lisa", "lynette", "yanfei", "beidou",
                            "razor", "rosaria", "kamisatoayaka", "qiqi", "diona", "kaedeharakazuha", "yelan",
                            "layla", "ganyu", "keqing", "sayu", "travelerhydro", "ningguang", "xiangling", "kirara",
                            "collei", "chongyun", "sangonomiyakokomi", "dori", "aloy", "kujousara", "kaeya", "amber",
                            "hutao", "candace", "diluc", "mona","yae", "yunjin", "gorou", "raidenshogun", "xinyan"
                        )
                    ) {
                        Column {
                            CharacterInfoCard(
                                modifier = Modifier,
                                onClick = {},
                                character = Character(
                                    it,
                                    it,
                                    (0L..4L).random(),
                                    false,
                                    (1..4).random().toLong(),
                                    "",
                                    infusionAura = "",
                                    level = 13L,
                                    talent_auto_lvl = 4L,
                                    talent_burst_lvl = 3L,
                                    talent_skill_lvl = 3L,
                                    team = emptyList()
                                )
                            )
                            Divider()
                        }
                    }
                }
            }
            
        }
    }
}

private fun Character.elementColor(): Color {
    return when (key.lowercase()) {
        "zhongli", "yunjin" , "ningguang", "noelle", "gorou", "candace", "albedo", "itto" -> Geo
        "shenhe", "rosaria", "qiqi", "mika", "layla", "kaeya" , "ganyu", "kamisatoayaka", "aloy", "chongyun",  "diona", "eula", "freminet" -> Cryo
        "yaoyao", "tighnari", "nahida", "kirara", "alhatham", "baizhu", "collei", "kaveh" -> Dendro
        "yoimiya", "xinyan","xiangling", "thoma", "lyney", "klee", "hutao", "amber", "bennett", "dehya", "diluc", "yanfei"  -> Pyro
        "yelan","xingqiu", "tartaglia", "nilou", "neuvillette", "mona", "sangonomiyakokomi", "barbara", "ayato" -> Hydro
        "yae", "raidenshogun", "kukishinobu", "kujousara", "razor", "lisa", "keqing", "beidou", "cyno", "dori",  "fischl"-> Electro
        "xiao", "wanderer", "venti", "sucrose", "sayu", "lynette", "kaedeharakazuha", "shikanoinheizou","jean", "faruzan" -> Anemo
        else -> Cryo
    }
}