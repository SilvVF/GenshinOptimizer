@file:OptIn(ExperimentalMaterial3Api::class)

package io.silv.genshinop.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import genshin.Artifact
import genshin.Character
import io.silv.genshinop.testCharacterKeys
import io.silv.genshinop.ui.composables.CharCardSizingType.*
import io.silv.genshinop.ui.composables.TalentType.*
import io.silv.genshinop.ui.theme.Anemo
import io.silv.genshinop.ui.theme.Cryo
import io.silv.genshinop.ui.theme.Dendro
import io.silv.genshinop.ui.theme.Electro
import io.silv.genshinop.ui.theme.GenshinopTheme
import io.silv.genshinop.ui.theme.Geo
import io.silv.genshinop.ui.theme.Hydro
import io.silv.genshinop.ui.theme.Pyro

enum class CharCardSizingType {
    Compact, SemiCompact, Full, List
}

@Composable
fun CharacterInfoCard(
    modifier: Modifier,
    onClick: () -> Unit,
    character: Character,
    artifacts: List<Artifact>,
    sizing: CharCardSizingType = Compact
) {
    when (sizing) {
        Compact -> CompactCharCard(
            modifier = modifier,
            character = character,
            showStars = false,
            onClick = onClick
        )
        SemiCompact -> CompactCharCard(
            modifier = modifier,
            character = character,
            showStars = true,
            onClick = onClick
        )
        Full -> FullCharCard(
            modifier = modifier,
            character = character,
            artifacts = artifacts,
            onClick = onClick
        )
        List -> ListCharCard(
            modifier = modifier,
            character = character,
            onClick = onClick
        )
    }
}

@Composable
fun ListCharCard(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: () -> Unit
) {

    val elementColor by character.elementColor()

    Row(modifier.clickable { onClick() }) {
        Box(
            Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(20))
        ) {
            NameCardImage(
                key = character.key,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            CharacterImage(
                key = character.key,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
            )
            ConstellationNumber(
                elementColor = elementColor,
                constellation = character.constellation,
                modifier = Modifier
                    .align(Alignment.BottomStart),
                shape = RoundedCornerShape(topEndPercent = 12)
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 4.dp, start = 4.dp)
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            TextWithElementBg(
                character = character,
                modifier = Modifier.fillMaxWidth(),
                elementColor = elementColor
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Stars(amount = character.starCount().value, modifier.fillMaxHeight())
            }
        }
        Spacer(Modifier.width(4.dp))
        Row(
            Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TalentLevel(
                modifier = Modifier
                    .size(26.dp),
                talentLevel = character.talent_auto_lvl,
                constellation = character.constellation,
                elementColor = elementColor,
                type = BasicAtk
            )
            TalentLevel(
                modifier = Modifier
                    .size(26.dp),
                talentLevel = character.talent_skill_lvl,
                constellation = character.constellation,
                elementColor = elementColor,
                type = Skill
            )
            TalentLevel(
                modifier = Modifier.size(26.dp),
                talentLevel = character.talent_burst_lvl,
                constellation = character.constellation,
                elementColor = elementColor,
                type = Burst
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FullCharCard(
    modifier: Modifier = Modifier,
    character: Character,
    artifacts: List<Artifact>,
    onClick: () -> Unit
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
        FlowRow {
            artifacts.fastForEach {
                ArtifactWithInfo(
                    artifact = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(90.dp)
                )
            }
        }
    }
}

@Composable
fun ArtifactWithInfo(
    artifact: Artifact,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Box(
            Modifier
                .aspectRatio(1f)
                .background(
                    brush = Brush.linearGradient(
                        when (artifact.rarity) {
                            else -> listOf(Color(69,54,53), Color(0xffe6ac54))
                        }
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            ArtifactImage(
                setKey = artifact.setKey,
                slotKey = artifact.slotKey,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(bottomEndPercent = 12))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(2.dp)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "+${artifact.level}",
                )
            }
        }
    }
}

@Composable
fun CompactCharCard(
    modifier: Modifier = Modifier,
    character: Character,
    showStars: Boolean,
    onClick: () -> Unit
) {
    val elementColor by character.elementColor()
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(30)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NameCardImage(
                key = character.key,
                modifier = Modifier
                    .matchParentSize(),
                contentScale = ContentScale.Crop
            )
            CharacterImage(
                key = character.key,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showStars) {
                    Stars(amount = character.starCount().value)
                }
                TextWithElementBg(
                    character = character,
                    modifier = Modifier.fillMaxWidth(.85f),
                    style = MaterialTheme.typography.labelSmall,
                    elementColor = elementColor
                )
            }
        }
    }
}

@Composable
private fun CardHeaderWithInfo(
    character: Character,
    modifier: Modifier = Modifier,
) {
    val elementColor by character.elementColor()

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
            modifier = Modifier
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
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    TextWithElementBg(
                        character = character,
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LevelText(character.level, character.ascension)
                        ConstellationNumber(
                            elementColor,
                            character.constellation,
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TalentLevel(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(26.dp),
                            talentLevel = character.talent_auto_lvl,
                            constellation = character.constellation,
                            elementColor = elementColor,
                            type = BasicAtk
                        )
                        TalentLevel(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(26.dp),
                            talentLevel = character.talent_skill_lvl,
                            constellation = character.constellation,
                            elementColor = elementColor,
                            type = Skill
                        )
                        TalentLevel(
                            modifier = Modifier.size(26.dp),
                            talentLevel = character.talent_burst_lvl,
                            constellation = character.constellation,
                            elementColor = elementColor,
                            type = Burst
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Stars(
                        amount = character.starCount().value
                    )
                }
            }
        }
        FadeBackground(start = 0.8f)
    }
}

enum class TalentType {
    BasicAtk, Skill, Burst
}

@Composable
private fun Stars(
    amount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
       for (i in 0 until amount) {
           Icon(
               imageVector = Icons.Default.Star,
               contentDescription = "star",
               tint = Color(255, 193, 7)
           )
       }
    }
}

@Composable
private fun TalentLevel(
    talentLevel: Long,
    constellation: Long,
    elementColor: Color,
    type: TalentType,
    modifier: Modifier = Modifier,
) {
    val normalBg =  MaterialTheme.colorScheme.surfaceVariant
    val talentAfterConstellationBuff = remember(constellation) {
        when (type) {
            BasicAtk -> talentLevel
            Skill -> if (constellation >= 5) talentLevel + 3 else talentLevel
            Burst -> if (constellation >= 3) talentLevel + 3 else talentLevel
        }
            .toString()
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                when (type) {
                    BasicAtk -> normalBg
                    Skill -> if (constellation >= 5) elementColor else normalBg
                    Burst -> if (constellation >= 3) elementColor else normalBg
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = talentAfterConstellationBuff,
            style = MaterialTheme.typography.labelLarge,
            color = when (type) {
                BasicAtk -> elementColor
                Skill -> if (constellation >= 5) normalBg else elementColor
                Burst -> if (constellation >= 3) normalBg else elementColor
            },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ConstellationNumber(
    elementColor: Color,
    constellation: Long,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(30)
) {
    
    Box(
        modifier = modifier
            .clip(shape)
            .size(26.dp)
            .background(elementColor)
            .padding(2.dp)
    ) {
        Text(
            text = "C${constellation}",
            style = MaterialTheme.typography.titleMedium.copy(
                MaterialTheme.colorScheme.surfaceVariant,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun LevelText(
    level: Long,
    ascension: Long
) {
    val s1 = MaterialTheme.typography.titleLarge
        .copy(
            color = MaterialTheme.colorScheme.surfaceVariant,
            fontWeight = FontWeight.ExtraBold
        ).toSpanStyle()
    Text(
        text = remember(level, ascension, s1) {
            val lvl = "Lv. $level"
            val cap = "/${
                when(ascension.toInt()) {
                    0 -> 20
                    1 -> 40
                    2 -> 50
                    3 -> 60
                    4 -> 70
                    5 -> 80
                    6 -> 90
                    else -> ""
                }
            }"
            buildAnnotatedString {
                append(lvl)
                addStyle(style = s1, 0, lvl.length)
                append(cap)
                addStyle(
                    style = s1.copy(
                        color = s1.color.copy(alpha = 0.8f)
                    ),
                    lvl.length,
                    lvl.length + cap.length
                )
            }
        }
    )
}

@Composable
private fun TextWithElementBg(
    character: Character,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    elementColor: Color = character.elementColor().value
) {
    val formatedText = remember(character) {
        StringBuilder().apply {
            character.key.forEachIndexed { i, c ->
                if (c.isUpperCase() && i != 0) {
                    append(' ')
                }
                append(c)
            }
        }
            .toString()
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(60))
            .background(
                color = elementColor
            )
    ) {
        Text(
            text = formatedText,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = style
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
                        testCharacterKeys
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
                                ),
                                emptyList(),
                            )
                            Divider()
                        }
                    }
                }
            } else {
                LazyColumn(Modifier.weight(1f)) {
                    items(
                        testCharacterKeys
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
                                ),
                                emptyList()
                            )
                            Divider()
                        }
                    }
                }
            }
            
        }
    }
}

@Composable
private fun Character.elementColor(): State<Color> {
    val color = when (key.lowercase()) {
        "zhongli", "yunjin", "ningguang", "noelle", "gorou", "candace", "albedo", "itto" -> Geo
        "shenhe", "rosaria", "qiqi", "mika", "layla", "kaeya", "ganyu", "kamisatoayaka", "aloy", "chongyun", "diona", "eula", "freminet" -> Cryo
        "yaoyao", "tighnari", "nahida", "kirara", "alhatham", "baizhu", "collei", "kaveh" -> Dendro
        "yoimiya", "xinyan", "xiangling", "thoma", "lyney", "klee", "hutao", "amber", "bennett", "dehya", "diluc", "yanfei" -> Pyro
        "yelan", "xingqiu", "tartaglia", "nilou", "neuvillette", "mona", "sangonomiyakokomi", "barbara", "ayato" -> Hydro
        "yae", "raidenshogun", "kukishinobu", "kujousara", "razor", "lisa", "keqing", "beidou", "cyno", "dori", "fischl" -> Electro
        "xiao", "wanderer", "venti", "sucrose", "sayu", "lynette", "kaedeharakazuha", "shikanoinheizou", "jean", "faruzan" -> Anemo
        else -> Cryo
    }
    return rememberUpdatedState(newValue = color)
}

@Composable
private fun Character.starCount(): State<Int> {
        val count = when (key.lowercase()) {
            "zhongli", "itto", "shenhe", "qiqi", "lyney", "tartaglia", "neuvillette", "keqing", "jean", "cyno", "xiao", "wanderer", "diluc",
            "yoimiya", "albedo","kamisatoayaka", "ganyu", "aloy","alhatham", "baizhu", "tighnari", "nahida","kaedeharakazuha",
            "venti", "yae", "raidenshogun", "hutao", "ayato","dehya",
            "klee","nilou", "mona", "sangonomiyakokomi", "yelan", "eula" -> 5
            "yunjin", "ningguang", "noelle", "gorou", "candace", "rosaria", "mika", "layla", "kaeya",
            "chongyun", "diona", "freminet", "yaoyao",  "kirara", "collei", "kaveh", "xinyan",
            "xiangling", "thoma", "amber", "bennett", "yanfei", "xingqiu",  "barbara",
            "kukishinobu", "kujousara", "razor", "lisa", "beidou", "dori", "fischl", "sucrose",
            "sayu", "lynette","shikanoinheizou", "faruzan" -> 4
            else -> 5
        }
    return rememberUpdatedState(newValue = count)
}