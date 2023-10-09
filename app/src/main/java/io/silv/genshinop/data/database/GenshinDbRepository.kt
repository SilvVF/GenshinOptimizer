package io.silv.genshinop.data.database

import android.util.Log
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import genshin.Artifact
import genshin.Character
import genshin.Weapon
import io.silv.Database
import io.silv.genshinop.data.GenshinData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GenshinDbRepository(
    private val db: Database,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun updateTables(genshinData: GenshinData) {
        withContext(ioDispatcher) {
            launch {
                genshinData.characters.map(::toDbCharacter).forEach {
                    db.characterQueries.insertObject(it)
                }
                Log.d("Chars",
                    genshinData.characters
                        .map(::toDbCharacter).map { "\"${it.key}\"" }.toString()
                )
            }
            launch {
                genshinData.weapons.map(::toDbWeapon).forEach {
                    db.weaponQueries.insertObject(it)
                }
            }
            launch {
                genshinData.artifacts.map(::toDbArtifact).forEach {
                    db.artifactQueries.insertObject(it)
                }
            }
        }
    }

    suspend fun insertWeapon(weapon: Weapon) {
        withContext(ioDispatcher) {
            db.weaponQueries.insertObject(weapon)
        }
    }

    suspend fun insertArtifact(artifact: Artifact) {
        withContext(ioDispatcher) {
            db.artifactQueries.insertObject(artifact)
        }
    }

    suspend fun insertCharacter(character: Character) {
        withContext(ioDispatcher) {
            db.characterQueries.insertObject(character)
        }
    }

    fun observeAllWeapons(): Flow<List<Weapon>> {
        return db.weaponQueries.selectAll().asFlow().mapToList(ioDispatcher)
    }

    fun observeAllCharacters(): Flow<List<Character>> {
        return db.characterQueries.selectAll().asFlow().mapToList(ioDispatcher)
    }

    fun observeAllArtifacts(): Flow<List<Artifact>> {
        return db.artifactQueries.selectAll().asFlow().mapToList(ioDispatcher)
    }
}