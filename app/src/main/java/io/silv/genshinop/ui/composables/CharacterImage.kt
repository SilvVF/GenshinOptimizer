package io.silv.genshinop.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.silv.genshinop.R
import io.silv.genshinop.ui.theme.GenshinopTheme

@Composable
fun CharacterImage(
    key: String,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {

    val resourceId = remember(key) {
        getResIdForKey(key)
    }

    Image(
        painter = painterResource(
            id = resourceId
        ),
        contentDescription = key,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

@Preview()
@Composable
private fun CharacterImagePreview() {
    GenshinopTheme {
        LazyColumn {
          items(
              listOf(
                  "barbara", "faruzan", "thoma", "kukishinobu", "shikanoinheizou", "sucrose",
                  "noelle", "bennett", "mika", "xingqiu", "lisa", "lynette", "yanfei", "beidou",
                  "razor", "rosaria", "kamisatoayaka", "qiqi", "diona", "kaedeharakazuha", "yelan",
                  "layla", "ganyu", "keqing", "sayu", "travelerhydro", "ningguang", "xiangling", "kirara",
                  "collei", "chongyun", "sangonomiyakokomi", "dori", "aloy", "kujousara", "kaeya", "amber",
                  "hutao", "candace", "diluc", "mona", "yunjin", "gorou", "raidenshogun", "xinyan"
              )
          ) {
              Column {
                  Text(text = it)
                  CharacterImage(
                      key = it,
                      modifier = Modifier.size(200.dp)
                  )
                  Divider()
              }
          }
        }
    }
}

private fun getResIdForKey(key: String): Int {
    return when (key.lowercase()) {
        "albedo" -> R.drawable.ui_avatar_icon_albedo
        "kamisatoayaka" -> R.drawable.ui_avatar_icon_ayaka
        "alhatham" -> R.drawable.ui_avatar_icon_alhatham
        "aloy" -> R.drawable.ui_avatar_icon_aloy
        "amber" -> R.drawable.ui_avatar_icon_amber
        "ayato" -> R.drawable.ui_avatar_icon_ayato
        "baizhu" -> R.drawable.ui_avatar_icon_baizhu
        "barbara" -> R.drawable.ui_avatar_icon_barbara
        "beidou" -> R.drawable.ui_avatar_icon_beidou
        "bennett" -> R.drawable.ui_avatar_icon_bennett
        "candace" -> R.drawable.ui_avatar_icon_candace
        "chongyun" -> R.drawable.ui_avatar_icon_chongyun
        "collei" -> R.drawable.ui_avatar_icon_collei
        "cyno" -> R.drawable.ui_avatar_icon_cyno
        "dehya" -> R.drawable.ui_avatar_icon_dehya
        "diluc" -> R.drawable.ui_avatar_icon_diluc
        "diona" -> R.drawable.ui_avatar_icon_diona
        "dori" -> R.drawable.ui_avatar_icon_dori
        "eula" -> R.drawable.ui_avatar_icon_eula
        "faruzan" -> R.drawable.ui_avatar_icon_faruzan
        "fischl" -> R.drawable.ui_avatar_icon_fischl
        "yanfei" -> R.drawable.ui_avatar_icon_feiyan
        "freminet" -> R.drawable.ui_avatar_icon_freminet
        "ganyu" -> R.drawable.ui_avatar_icon_ganyu
        "gorou" -> R.drawable.ui_avatar_icon_gorou
        "shikanoinheizou" -> R.drawable.ui_avatar_icon_heizo
        "hutao" -> R.drawable.ui_avatar_icon_hutao
        "itto" -> R.drawable.ui_avatar_icon_itto
        "jean" -> R.drawable.ui_avatar_icon_jean
        "kaeya" -> R.drawable.ui_avatar_icon_kaeya
        "kaveh" -> R.drawable.ui_avatar_icon_kaveh
        "kaedeharakazuha" -> R.drawable.ui_avatar_icon_kazuha
        "klee" -> R.drawable.ui_avatar_icon_klee
        "keqing" -> R.drawable.ui_avatar_icon_keqing
        "sangonomiyakokomi" -> R.drawable.ui_avatar_icon_kokomi
        "layla" -> R.drawable.ui_avatar_icon_layla
        "lyney" -> R.drawable.ui_avatar_icon_liney
        "lynette" -> R.drawable.ui_avatar_icon_linette
        "lisa" -> R.drawable.ui_avatar_icon_lisa
        "mika" -> R.drawable.ui_avatar_icon_mika
        "mona" -> R.drawable.ui_avatar_icon_mona
        "kirara" -> R.drawable.ui_avatar_icon_momoka
        "neuvillette" -> R.drawable.ui_avatar_icon_neuvillette
        "nahida" -> R.drawable.ui_avatar_icon_nahida
        "nilou" -> R.drawable.ui_avatar_icon_nilou
        "noelle" -> R.drawable.ui_avatar_icon_noel
        "ningguang" -> R.drawable.ui_avatar_icon_ningguang
        "player_girl" -> R.drawable.ui_avatar_icon_player_girl
        "qiqi" -> R.drawable.ui_avatar_icon_qiqi
        "razor" -> R.drawable.ui_avatar_icon_razor
        "rosaria" -> R.drawable.ui_avatar_icon_rosaria
        "kujousara" -> R.drawable.ui_avatar_icon_sara
        "sayu" -> R.drawable.ui_avatar_icon_sayu
        "shenhe" -> R.drawable.ui_avatar_icon_shenhe
        "kukishinobu" -> R.drawable.ui_avatar_icon_shinobu
        "raidenshogun" -> R.drawable.ui_avatar_icon_shougun
        "sucrose" -> R.drawable.ui_avatar_icon_sucrose
        "tartaglia" -> R.drawable.ui_avatar_icon_tartaglia
        "thoma" -> R.drawable.ui_avatar_icon_tohma
        "tighnari" -> R.drawable.ui_avatar_icon_tighnari
        "venti" -> R.drawable.ui_avatar_icon_venti
        "wanderer" -> R.drawable.ui_avatar_icon_wanderer
        "xiangling" -> R.drawable.ui_avatar_icon_xiangling
        "xiao" -> R.drawable.ui_avatar_icon_xiao
        "xingqiu" -> R.drawable.ui_avatar_icon_xingqiu
        "xinyan" -> R.drawable.ui_avatar_icon_xinyan
        "yelan" -> R.drawable.ui_avatar_icon_yelan
        "yae" -> R.drawable.ui_avatar_icon_yae
        "yaoyao" -> R.drawable.ui_avatar_icon_yaoyao
        "yunjin" -> R.drawable.ui_avatar_icon_yunjin
        "yoimiya" -> R.drawable.ui_avatar_icon_yoimiya
        "zhongli" -> R.drawable.ui_avatar_icon_zhongli
        else -> R.drawable.ui_avatar_icon_player_girl
    }
}