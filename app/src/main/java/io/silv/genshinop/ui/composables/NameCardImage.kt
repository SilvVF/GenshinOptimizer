package io.silv.genshinop.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.silv.genshinop.R
import io.silv.genshinop.testCharacterKeys
import io.silv.genshinop.ui.theme.GenshinopTheme

@Composable
fun NameCardImage(
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

    resourceId?.let {
        Image(
            painter = painterResource(
                id = it
            ),
            contentDescription = key,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    } ?: Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    listOf(Color(0xff695453), Color(0xffe6ac54))
                )
            )
            .aspectRatio(21f / 10f, true)
    )
}

@Preview()
@Composable
private fun CharacterImagePreview() {
    GenshinopTheme {
        LazyColumn {
            items(
                testCharacterKeys
            ) {
                Column {
                    Text(text = it)
                    NameCardImage(
                        key = it,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Divider()
                }
            }
        }
    }
}

private fun getResIdForKey(key: String): Int? {
    return when (key.lowercase()) {
        "albedo" -> R.drawable.ui_namecardpic_albedo
        "kamisatoayaka" -> R.drawable.ui_namecardpic_ayaka
        "alhatham" -> R.drawable.ui_namecardpic_alhatham
        "aloy" -> R.drawable.ui_namecardpic_aloy
        "amber" -> R.drawable.ui_namecardpic_ambor
        "ayato" -> R.drawable.ui_namecardpic_ayato
        "baizhu" -> R.drawable.ui_namecardpic_baizhuer
        "barbara" -> R.drawable.ui_namecardpic_barbara
        "beidou" -> R.drawable.ui_namecardpic_beidou
        "bennett" -> R.drawable.ui_namecardpic_bennett
        "candace" -> R.drawable.ui_namecardpic_candace
        "chongyun" -> R.drawable.ui_namecardpic_chongyun
        "collei" -> R.drawable.ui_namecardpic_collei
        "cyno" -> R.drawable.ui_namecardpic_cyno
        "dehya" -> R.drawable.ui_namecardpic_dehya
        "diluc" -> R.drawable.ui_namecardpic_diluc
        "diona" -> R.drawable.ui_namecardpic_diona
        "dori" -> R.drawable.ui_namecardpic_dori
        "eula" -> R.drawable.ui_namecardpic_eula
        "faruzan" -> R.drawable.ui_namecardpic_faruzan
        "fischl" -> R.drawable.ui_namecardpic_fischl
        "yanfei" -> R.drawable.ui_namecardpic_feiyan
        "freminet" -> R.drawable.ui_namecardpic_freminet
        "ganyu" -> R.drawable.ui_namecardpic_ganyu
        "gorou" -> R.drawable.ui_namecardpic_gorou
        "shikanoinheizou" -> R.drawable.ui_namecardpic_heizo
        "hutao" -> R.drawable.ui_namecardpic_hutao
        "itto" -> R.drawable.ui_namecardpic_itto
        "jean" -> R.drawable.ui_namecardpic_qin
        "kaeya" -> R.drawable.ui_namecardpic_kaeya
        "kaveh" -> R.drawable.ui_namecardpic_kaveh
        "kaedeharakazuha" -> R.drawable.ui_namecardpic_kazuha
        "klee" -> R.drawable.ui_namecardpic_klee
        "keqing" -> R.drawable.ui_namecardpic_keqing
        "sangonomiyakokomi" -> R.drawable.ui_namecardpic_kokomi
        "layla" -> R.drawable.ui_namecardpic_layla
        "lyney" -> R.drawable.ui_namecardpic_liney
        "lynette" -> R.drawable.ui_namecardpic_linette
        "lisa" -> R.drawable.ui_namecardpic_lisa
        "mika" -> R.drawable.ui_namecardpic_mika
        "mona" -> R.drawable.ui_namecardpic_mona
        "kirara" -> R.drawable.ui_namecardpic_kirara
        "neuvillette" -> R.drawable.ui_namecardpic_neuvillette
        "nahida" -> R.drawable.ui_namecardpic_nahida
        "nilou" -> R.drawable.ui_namecardpic_nilou
        "noelle" -> R.drawable.ui_namecardpic_noel
        "ningguang" -> R.drawable.ui_namecardpic_ningguang
        "qiqi" -> R.drawable.ui_namecardpic_qiqi
        "razor" -> R.drawable.ui_namecardpic_razor
        "rosaria" -> R.drawable.ui_namecardpic_rosaria
        "kujousara" -> R.drawable.ui_namecardpic_sara
        "sayu" -> R.drawable.ui_namecardpic_sayu
        "shenhe" -> R.drawable.ui_namecardpic_shenhe
        "kukishinobu" -> R.drawable.ui_namecardpic_shinobu
        "raidenshogun" -> R.drawable.ui_namecardpic_shougun
        "sucrose" -> R.drawable.ui_namecardpic_sucrose
        "tartaglia" -> R.drawable.ui_namecardpic_tartaglia
        "thoma" -> R.drawable.ui_namecardpic_tohma
        "tighnari" -> R.drawable.ui_namecardpic_tighnari
        "venti" -> R.drawable.ui_namecardpic_venti
        "wanderer" -> R.drawable.ui_namecardpic_wanderer
        "xiangling" -> R.drawable.ui_namecardpic_xiangling
        "xiao" -> R.drawable.ui_namecardpic_xiao
        "xingqiu" -> R.drawable.ui_namecardpic_xingqiu
        "xinyan" -> R.drawable.ui_namecardpic_xinyan
        "yelan" -> R.drawable.ui_namecardpic_yelan
        "yae" -> R.drawable.ui_namecardpic_yae1
        "yaoyao" -> R.drawable.ui_namecardpic_yaoyao
        "yunjin" -> R.drawable.ui_namecardpic_yunjin
        "yoimiya" -> R.drawable.ui_namecardpic_yoimiya
        "zhongli" -> R.drawable.ui_namecardpic_zhongli
        else -> null
    }
}