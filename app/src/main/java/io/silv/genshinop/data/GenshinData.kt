package io.silv.genshinop.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenshinData(
    @SerialName("artifacts")
    val artifacts: List<Artifact> = listOf(),
    @SerialName("buildResults")
    val buildResults: List<BuildResult> = listOf(),
    @SerialName("buildSettings")
    val buildSettings: List<BuildSetting> = listOf(),
    @SerialName("charMetas")
    val charMetas: List<CharMetas> = listOf(),
    @SerialName("charTCs")
    val charTCs: List<CharTC> = listOf(),
    @SerialName("characters")
    val characters: List<Character> = listOf(),
    @SerialName("dbMeta")
    val dbMeta: DbMeta = DbMeta(),
    @SerialName("dbVersion")
    val dbVersion: Int = 0,
    @SerialName("display_artifact")
    val displayArtifact: DisplayArtifact = DisplayArtifact(),
    @SerialName("display_character")
    val displayCharacter: DisplayCharacter = DisplayCharacter(),
    @SerialName("display_optimize")
    val displayOptimize: DisplayOptimize = DisplayOptimize(),
    @SerialName("display_tool")
    val displayTool: DisplayTool = DisplayTool(),
    @SerialName("display_weapon")
    val displayWeapon: DisplayWeapon = DisplayWeapon(),
    @SerialName("format")
    val format: String = "",
    @SerialName("source")
    val source: String = "",
    @SerialName("version")
    val version: Int = 0,
    @SerialName("weapons")
    val weapons: List<Weapon> = listOf()
) {

    @Serializable
    data class CharTC(
        val weapon: Weapon = Weapon(),
        val artifact: Artifact = Artifact()
    )

    @Serializable
    data class CharMetas(
        val rvFilter: List<String> = emptyList(),
        val favorite: Boolean = false
    )

    @Serializable
    data class Artifact(
        @SerialName("id")
        val id: String = "",
        @SerialName("level")
        val level: Int = 0,
        @SerialName("location")
        val location: String = "",
        @SerialName("lock")
        val lock: Boolean = false,
        @SerialName("mainStatKey")
        val mainStatKey: String = "",
        @SerialName("rarity")
        val rarity: Int = 0,
        @SerialName("setKey")
        val setKey: String = "",
        @SerialName("slotKey")
        val slotKey: String = "",
        @SerialName("substats")
        val substats: List<Substat> = listOf()
    ) {
        @Serializable
        data class Substat(
            @SerialName("key")
            val key: String = "",
            @SerialName("value")
            val value: Double = 0.0
        )
    }

    @Serializable
    data class BuildResult(
        @SerialName("buildDate")
        val buildDate: Long = 0,
        @SerialName("builds")
        val builds: List<List<String>> = listOf(),
        @SerialName("id")
        val id: String = ""
    )

    @Serializable
    data class BuildSetting(
        @SerialName("allowLocationsState")
        val allowLocationsState: String = "",
        @SerialName("allowPartial")
        val allowPartial: Boolean = false,
        @SerialName("artExclusion")
        val artExclusion: List<String> = listOf(),
        @SerialName("artSetExclusion")
        val artSetExclusion: ArtSetExclusion = ArtSetExclusion(),
        @SerialName("compareBuild")
        val compareBuild: Boolean = false,
        @SerialName("excludedLocations")
        val excludedLocations: List<String> = listOf(),
        @SerialName("id")
        val id: String = "",
        @SerialName("levelHigh")
        val levelHigh: Int = 0,
        @SerialName("levelLow")
        val levelLow: Int = 0,
        @SerialName("mainStatAssumptionLevel")
        val mainStatAssumptionLevel: Int = 0,
        @SerialName("mainStatKeys")
        val mainStatKeys: MainStatKeys = MainStatKeys(),
        @SerialName("maxBuildsToShow")
        val maxBuildsToShow: Int = 0,
        @SerialName("optimizationTarget")
        val optimizationTarget: List<String> = listOf(),
        @SerialName("plotBase")
        val plotBase: List<String> = listOf(),
        @SerialName("statFilters")
        val statFilters: StatFilters = StatFilters(),
        @SerialName("useExcludedArts")
        val useExcludedArts: Boolean = false
    ) {
        @Serializable
        data class ArtSetExclusion(
            @SerialName("Adventurer")
            val adventurer: List<Int> = listOf(),
            @SerialName("ArchaicPetra")
            val archaicPetra: List<Int> = listOf(),
            @SerialName("Berserker")
            val berserker: List<Int> = listOf(),
            @SerialName("BloodstainedChivalry")
            val bloodstainedChivalry: List<Int> = listOf(),
            @SerialName("BraveHeart")
            val braveHeart: List<Int> = listOf(),
            @SerialName("CrimsonWitchOfFlames")
            val crimsonWitchOfFlames: List<Int> = listOf(),
            @SerialName("DeepwoodMemories")
            val deepwoodMemories: List<Int> = listOf(),
            @SerialName("DefendersWill")
            val defendersWill: List<Int> = listOf(),
            @SerialName("DesertPavilionChronicle")
            val desertPavilionChronicle: List<Int> = listOf(),
            @SerialName("EchoesOfAnOffering")
            val echoesOfAnOffering: List<Int> = listOf(),
            @SerialName("EmblemOfSeveredFate")
            val emblemOfSeveredFate: List<Int> = listOf(),
            @SerialName("FlowerOfParadiseLost")
            val flowerOfParadiseLost: List<Int> = listOf(),
            @SerialName("Gambler")
            val gambler: List<Int> = listOf(),
            @SerialName("GildedDreams")
            val gildedDreams: List<Int> = listOf(),
            @SerialName("GladiatorsFinale")
            val gladiatorsFinale: List<Int> = listOf(),
            @SerialName("GoldenTroupe")
            val goldenTroupe: List<Int> = listOf(),
            @SerialName("HeartOfDepth")
            val heartOfDepth: List<Int> = listOf(),
            @SerialName("HuskOfOpulentDreams")
            val huskOfOpulentDreams: List<Int> = listOf(),
            @SerialName("Instructor")
            val instructor: List<Int> = listOf(),
            @SerialName("Lavawalker")
            val lavawalker: List<Int> = listOf(),
            @SerialName("LuckyDog")
            val luckyDog: List<Int> = listOf(),
            @SerialName("MaidenBeloved")
            val maidenBeloved: List<Int> = listOf(),
            @SerialName("MarechausseeHunter")
            val marechausseeHunter: List<Int> = listOf(),
            @SerialName("MartialArtist")
            val martialArtist: List<Int> = listOf(),
            @SerialName("NoblesseOblige")
            val noblesseOblige: List<Int> = listOf(),
            @SerialName("NymphsDream")
            val nymphsDream: List<Int> = listOf(),
            @SerialName("OceanHuedClam")
            val oceanHuedClam: List<Int> = listOf(),
            @SerialName("PaleFlame")
            val paleFlame: List<Int> = listOf(),
            @SerialName("ResolutionOfSojourner")
            val resolutionOfSojourner: List<Int> = listOf(),
            @SerialName("RetracingBolide")
            val retracingBolide: List<Int> = listOf(),
            @SerialName("Scholar")
            val scholar: List<Int> = listOf(),
            @SerialName("ShimenawasReminiscence")
            val shimenawasReminiscence: List<Int> = listOf(),
            @SerialName("TenacityOfTheMillelith")
            val tenacityOfTheMillelith: List<Int> = listOf(),
            @SerialName("TheExile")
            val theExile: List<Int> = listOf(),
            @SerialName("ThunderingFury")
            val thunderingFury: List<Int> = listOf(),
            @SerialName("Thundersoother")
            val thundersoother: List<Int> = listOf(),
            @SerialName("TinyMiracle")
            val tinyMiracle: List<Int> = listOf(),
            @SerialName("TravelingDoctor")
            val travelingDoctor: List<Int> = listOf(),
            @SerialName("VermillionHereafter")
            val vermillionHereafter: List<Int> = listOf(),
            @SerialName("ViridescentVenerer")
            val viridescentVenerer: List<Int> = listOf(),
            @SerialName("VourukashasGlow")
            val vourukashasGlow: List<Int> = listOf(),
            @SerialName("WanderersTroupe")
            val wanderersTroupe: List<Int> = listOf()
        )

        @Serializable
        data class MainStatKeys(
            @SerialName("circlet")
            val circlet: List<String> = listOf(),
            @SerialName("goblet")
            val goblet: List<String> = listOf(),
            @SerialName("sands")
            val sands: List<String> = listOf()
        )

        @Serializable
        class StatFilters
    }

    @Serializable
    data class Character(
        @SerialName("ascension")
        val ascension: Int = 0,
        @SerialName("bonusStats")
        val bonusStats: BonusStats = BonusStats(),
        @SerialName("compareData")
        val compareData: Boolean = false,
        @SerialName("conditional")
        val conditional: Conditional = Conditional(),
        @SerialName("constellation")
        val constellation: Int = 0,
        @SerialName("customMultiTarget")
        val customMultiTarget: List<String> = listOf(),
        @SerialName("enemyOverride")
        val enemyOverride: EnemyOverride = EnemyOverride(),
        @SerialName("hitMode")
        val hitMode: String = "",
        @SerialName("id")
        val id: String = "",
        @SerialName("infusionAura")
        val infusionAura: String = "",
        @SerialName("key")
        val key: String = "",
        @SerialName("level")
        val level: Int = 0,
        @SerialName("reaction")
        val reaction: String = "",
        @SerialName("talent")
        val talent: Talent = Talent(),
        @SerialName("team")
        val team: List<String> = listOf(),
        @SerialName("teamConditional")
        val teamConditional: TeamConditional = TeamConditional()
    ) {
        @Serializable
        class BonusStats

        @Serializable
        data class Conditional(
            @SerialName("BlizzardStrayer")
            val blizzardStrayer: BlizzardStrayer = BlizzardStrayer()
        ) {
            @Serializable
            data class BlizzardStrayer(
                @SerialName("state")
                val state: String = ""
            )
        }

        @Serializable
        class EnemyOverride

        @Serializable
        data class Talent(
            @SerialName("auto")
            val auto: Int = 0,
            @SerialName("burst")
            val burst: Int = 0,
            @SerialName("skill")
            val skill: Int = 0
        )

        @Serializable
        class TeamConditional
    }

    @Serializable
    data class DbMeta(
        @SerialName("gender")
        val gender: String = "",
        @SerialName("lastEdit")
        val lastEdit: Long = 0,
        @SerialName("name")
        val name: String = ""
    )

    @Serializable
    data class DisplayArtifact(
        @SerialName("ascending")
        val ascending: Boolean = false,
        @SerialName("effFilter")
        val effFilter: List<String> = listOf(),
        @SerialName("filterOption")
        val filterOption: FilterOption = FilterOption(),
        @SerialName("probabilityFilter")
        val probabilityFilter: ProbabilityFilter = ProbabilityFilter(),
        @SerialName("sortType")
        val sortType: String = ""
    ) {
        @Serializable
        data class FilterOption(
            @SerialName("artSetKeys")
            val artSetKeys: List<String> = listOf(),
            @SerialName("levelHigh")
            val levelHigh: Int = 0,
            @SerialName("levelLow")
            val levelLow: Int = 0,
            @SerialName("lines")
            val lines: List<Int> = listOf(),
            @SerialName("locations")
            val locations: List<String> = listOf(),
            @SerialName("locked")
            val locked: List<String> = listOf(),
            @SerialName("mainStatKeys")
            val mainStatKeys: List<String> = listOf(),
            @SerialName("rarity")
            val rarity: List<Int> = listOf(),
            @SerialName("rvHigh")
            val rvHigh: Int = 0,
            @SerialName("rvLow")
            val rvLow: Int = 0,
            @SerialName("showEquipped")
            val showEquipped: Boolean = false,
            @SerialName("showInventory")
            val showInventory: Boolean = false,
            @SerialName("slotKeys")
            val slotKeys: List<String> = listOf(),
            @SerialName("substats")
            val substats: List<String> = listOf()
        )

        @Serializable
        class ProbabilityFilter
    }

    @Serializable
    data class DisplayCharacter(
        @SerialName("ascending")
        val ascending: Boolean = false,
        @SerialName("element")
        val element: List<String> = listOf(),
        @SerialName("pageIndex")
        val pageIndex: Int = 0,
        @SerialName("sortType")
        val sortType: String = "",
        @SerialName("weaponType")
        val weaponType: List<String> = listOf()
    )

    @Serializable
    data class DisplayOptimize(
        @SerialName("threads")
        val threads: Int = 0
    )

    @Serializable
    data class DisplayTool(
        @SerialName("resin")
        val resin: Int = 0,
        @SerialName("resinDate")
        val resinDate: Long = 0,
        @SerialName("timeZoneKey")
        val timeZoneKey: String = ""
    )

    @Serializable
    data class DisplayWeapon(
        @SerialName("ascending")
        val ascending: Boolean = false,
        @SerialName("editWeaponId")
        val editWeaponId: String = "",
        @SerialName("rarity")
        val rarity: List<Int> = listOf(),
        @SerialName("sortType")
        val sortType: String = "",
        @SerialName("weaponType")
        val weaponType: List<String> = listOf()
    )

    @Serializable
    data class Weapon(
        @SerialName("ascension")
        val ascension: Int = 0,
        @SerialName("id")
        val id: String = "",
        @SerialName("key")
        val key: String = "",
        @SerialName("level")
        val level: Int = 0,
        @SerialName("location")
        val location: String = "",
        @SerialName("lock")
        val lock: Boolean = false,
        @SerialName("refinement")
        val refinement: Int = 0
    )
}