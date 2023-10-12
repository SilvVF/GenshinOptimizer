package io.silv.genshinop.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import genshin.Character


fun Character.element(): Element {
    return when (key.lowercase()) {
        "zhongli", "yunjin", "ningguang", "noelle", "gorou", "candace", "albedo", "itto" -> Element.Geo
        "shenhe", "rosaria", "qiqi", "mika", "layla", "kaeya", "ganyu", "kamisatoayaka", "aloy", "chongyun", "diona", "eula", "freminet" -> Element.Cryo
        "yaoyao", "tighnari", "nahida", "kirara", "alhatham", "baizhu", "collei", "kaveh" -> Element.Dendro
        "yoimiya", "xinyan", "xiangling", "thoma", "lyney", "klee", "hutao", "amber", "bennett", "dehya", "diluc", "yanfei" -> Element.Pyro
        "yelan", "xingqiu", "tartaglia", "nilou", "neuvillette", "mona", "sangonomiyakokomi", "barbara", "ayato" -> Element.Hydro
        "yae", "raidenshogun", "kukishinobu", "kujousara", "razor", "lisa", "keqing", "beidou", "cyno", "dori", "fischl" -> Element.Electro
        "xiao", "wanderer", "venti", "sucrose", "sayu", "lynette", "kaedeharakazuha", "shikanoinheizou", "jean", "faruzan" -> Element.Anemo
        else -> Element.Anemo
    }
}


enum class Element(val color: Color) {
    Cryo(io.silv.genshinop.ui.theme.Cryo),
    Pyro(io.silv.genshinop.ui.theme.Pyro),
    Hydro(io.silv.genshinop.ui.theme.Hydro),
    Anemo(io.silv.genshinop.ui.theme.Anemo),
    Electro(io.silv.genshinop.ui.theme.Electro),
    Dendro(io.silv.genshinop.ui.theme.Dendro),
    Geo(io.silv.genshinop.ui.theme.Geo),
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.ElementFilterRow(
    onElementSelected: (Element?) -> Unit,
) {
    item {
        val selectedList = remember {
            mutableStateListOf(
                TagSelection(Element.Anemo),
                TagSelection(Element.Hydro),
                TagSelection(Element.Cryo),
                TagSelection(Element.Pyro),
                TagSelection(Element.Dendro),
                TagSelection(Element.Electro),
                TagSelection(Element.Geo)
            )
        }
        MultiFilterTag(
            modifier = Modifier.animateItemPlacement().height(38.dp),
            tagSelections = selectedList,
            label = { (item, selected) ->
                Text(
                    text = item.name,
                    color = if (!selected) item.color else MaterialTheme.colorScheme.surface
                )
            },
            selectedColor = selectedList.fastFirstOrNull { it.selected }?.item?.color
                ?: MaterialTheme.colorScheme.secondaryContainer,
            onTagSelected = {
                onElementSelected(
                    if (it.selected) null else it.item
                )
                val idx = selectedList.indexOf(it)
                val item = selectedList[idx]
                selectedList[idx] = item.copy(selected = !item.selected)
            }
        )
        Spacer(Modifier.width(4.dp))
    }
}