package purpleagle.com.model.functions

import purpleagle.com.model.models.official.bootstrapstatic.BootStrapModel
import purpleagle.com.model.models.official.bootstrapstatic.Events
import purpleagle.com.model.models.official.bootstrapstatic.Element
import purpleagle.com.model.models.official.elementsummary.ElementSummary
import purpleagle.com.model.models.official.elementsummary.Fixture
import purpleagle.com.model.models.official.fixtures.FixtureApi
import purpleagle.com.model.models.official.util.DoubleType

fun calculateScore(
    bootStrapModel: BootStrapModel,
    element: Element,
    elementSummary: ElementSummary,
    fixtures: List<FixtureApi>
): Float{
    val currentGameweek: MutableList<Events>
    var last4GameWeeks: List<FixtureApi> = emptyList()
    val next3GameWeeks: List<Fixture>
    val homeOrAway: MutableMap<Int, DoubleType<Boolean, FixtureApi>> = mutableMapOf()
    var last4difficulties = 12
    var nextDifficulty = 0f
    try {
        currentGameweek = bootStrapModel.events.filter { it.isCurrent == true }.toMutableList()
        if (currentGameweek[0].finished == false) {
            currentGameweek[0] =
                bootStrapModel.events.filter { it.id == currentGameweek[0].id!! - 1 }[0]
        }
        try {
            last4GameWeeks = fixtures.filter {
                (it.event.toIntOr(100) < currentGameweek[0].id.toIntOr(0) && it.event.toIntOr(0) > currentGameweek[0].id.toIntOr(100) - 5) && ((element.team == it.teamA) || (element.team == it.teamH))
            }
            last4GameWeeks.forEach {
                homeOrAway[it.event!!] = DoubleType(
                    element.team == it.teamH,
                    it
                )
            }
            homeOrAway.forEach {
                last4difficulties += if (it.value.first) {
                    it.value.second.teamADifficulty ?: 0
                } else {
                    it.value.second.teamHDifficulty ?: 0
                }
            }
        }catch(_:Exception){ }
        next3GameWeeks = elementSummary.fixtures.filter {
            it.event.toIntOr(-1) >= currentGameweek[0].id.toIntOr(100) && it.event.toIntOr(100) < currentGameweek[0].id.toIntOr(-1) + 4
        }
    }catch (e:Exception){
        return 0f
    }
    return try{
        var chanceOfPlaying = (((element.chanceOfPlayingThisRound.toString().toFloatOrNull() ?: 0f)+(element.chanceOfPlayingNextRound.toString().toFloatOrNull()?:0f))/2)/100
        if (chanceOfPlaying == 0f){
            chanceOfPlaying = 1f
        }
        val pointsPerGame = element.pointsPerGame.toFloatOrNull() ?: 0f
        var formDivider = last4GameWeeks.size
        if(formDivider == 0) formDivider = 4
        val formAverage = (
                (
                        element.form.toString().toFloatOrNull() ?: (0f *
                                (((last4difficulties)) / formDivider))
                        )
                )

        val ictAverage = (
                (element.ictIndex.toString().toFloatOrNull()?:0f)/fixtures.size
                )
        try {
            next3GameWeeks.forEach {
                nextDifficulty+= it.difficulty!!
            }
            nextDifficulty/= next3GameWeeks.size
        }catch (e: Exception){
            nextDifficulty = 3f
        }
        if(nextDifficulty == 0f){
            nextDifficulty = 1f
        }
        val score = chanceOfPlaying*((pointsPerGame+formAverage+ictAverage+element.dreamteamCount.toIntOr(0)*2)/nextDifficulty)
        score
    }catch(e: Exception){
        0f
    }
}