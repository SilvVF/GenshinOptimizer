package io.silv.genshinop.data.database

import genshin.Artifact
import genshin.Character
import genshin.Weapon
import io.silv.genshinop.data.GenshinData

@JvmName("toDbArtifactMap")
fun toDbArtifact(a: GenshinData.Artifact): Artifact {
    return a.toDbArtifact()
}

fun GenshinData.Artifact.toDbArtifact(): Artifact {
    return Artifact(
        id = id.split("_")[1].toLong(),
        setKey = setKey,
        slotKey = slotKey,
        location = location,
        lock = lock,
        main_stat_key = mainStatKey,
        rarity = rarity.toLong(),
        level = level.toLong(),
        substats = substats.map { ss ->
            ss.key to ss.value.toFloat()
        }
    )
}

fun toDbWeapon(weapon: GenshinData.Weapon): Weapon {
    return Weapon(
        id = weapon.id.split("_")[1].toLong(),
        key = weapon.key,
        level = weapon.level.toLong(),
        location = weapon.location,
        lock = weapon.lock,
        refinement = weapon.refinement.toLong()
    )
}

@JvmName("toDbCharacterMap")
fun toDbCharacter(c: GenshinData.Character): Character {
    return c.toDbCharacter()
}

fun GenshinData.Character.toDbCharacter(): Character {
    return Character(
        id = id,
        key = key,
        ascension = ascension.toLong(),
        compare_data = compareData,
        constellation = constellation.toLong(),
        hitMode = hitMode,
        infusionAura = infusionAura,
        level = level.toLong(),
        talent_auto_lvl = talent.auto.toLong(),
        talent_skill_lvl = talent.skill.toLong(),
        talent_burst_lvl = talent.burst.toLong(),
        team = team
    )
}