package io.silv.genshinop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.silv.genshinop.data.database.GenshinDbRepository
import io.silv.genshinop.ui.composables.CharacterImage
import io.silv.genshinop.ui.theme.GenshinopTheme
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {

    private val dbRepo by inject<GenshinDbRepository>()
    private val json by inject<Json>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LaunchedEffect(key1 = Unit) {
                dbRepo.updateTables(
                    json.decodeFromString(database1data)
                )
            }

            val characters by dbRepo.observeAllCharacters().collectAsState(initial = emptyList())
            val weapons by dbRepo.observeAllWeapons().collectAsState(initial = emptyList())
            val artifacts by dbRepo.observeAllArtifacts().collectAsState(initial = emptyList())

            GenshinopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn {
                        items(characters) {
                            Column {
                                Text(it.key)
                                CharacterImage(
                                    key = it.key,
                                    modifier = Modifier.size(200.dp)
                                )
                            }
                        }
                        item {
                            Divider()
                        }
                        items(weapons) {
                            Text(it.toString())
                        }
                    }
                }
            }
        }
    }
}

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