package io.silv.genshinop

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import genshin.Artifact
import genshin.Character
import io.silv.Database
import io.silv.genshinop.data.database.Adapters
import io.silv.genshinop.data.database.GenshinDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Json {
            isLenient = true
            prettyPrint = true
        }
    }

    single {
        GenshinDbRepository(get(), Dispatchers.IO)
    }

    single {
        Database(
            driver = AndroidSqliteDriver(
                schema = Database.Schema,
                context = androidContext(),
                name = "genshin.db"
            ),
            artifactAdapter = Artifact.Adapter(
                substatsAdapter = Adapters.listOfPairStringFloatAdapter
            ),
            characterAdapter = Character.Adapter(
                teamAdapter = Adapters.listOfStringsAdapter,
            ),
        )
    }

}