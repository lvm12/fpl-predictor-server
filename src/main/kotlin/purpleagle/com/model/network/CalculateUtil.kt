package purpleagle.com.model.network

import io.ktor.util.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import purpleagle.com.Players
import purpleagle.com.model.database.DataSourceImpl
import purpleagle.com.model.functions.calculateScore
import purpleagle.com.model.models.official.util.DataHolderList
import purpleagle.com.model.models.official.util.toPlayers

class CalculateUtil(
    private val apiConnection: OfficialApiConnector = OfficialApiConnector(),
    private val dataSource: DataSourceImpl,
    private val scope: CoroutineScope
) {
    /*init {
        scope.launch {
            execute()
        }
    }*/
    private val _state = MutableStateFlow(ApiDataState())
    val state = _state
        .stateIn(
            scope,
            SharingStarted.WhileSubscribed(5000L),
            ApiDataState()
        )
    suspend fun execute(){
        println("Simple: Getting bootstrap"); getBootstrap()
        println("Simple: Getting elements"); getElements()
        getElementSummaries()
        calculateScores()
        println("Simple: Setting general table"); setGeneralTable()
        findBestTeam()
        println("Simple: Setting best table"); setBestTable()
    }
    private suspend fun getBootstrap(){
        _state.update {it.copy(
            bootStrapModel = apiConnection.getBootStrap().getOrThrow()
        )}
    }
    private fun getElements(){
        _state.update { it.copy(
            playerList = _state.value.bootStrapModel!!.elements
        ) }
    }
    private suspend fun getElementSummaries(){
        _state.value.playerList.forEach {element ->
            println("Simple: Getting summary for ${element.webName}")
            _state.value.elementSummaries[element.id!!] = apiConnection.getElementSummaryById(element.id!!).getOrThrow()
        }
    }
    private suspend fun calculateScores(){
        val fixtures = apiConnection.getFixtures().getOrThrow()
        with(_state.value){
            playerList.forEachIndexed() { _, element->
                println("Calculating: Calculating element ${element.webName}")
                scoreList[element.id!!] = calculateScore(
                    bootStrapModel!!,
                    element,
                    elementSummaries[element.id]!!,
                    fixtures
                )
            }
        };with(_state){
            update {state->
                state.copy(
                    dataHolderList = DataHolderList(
                        elements = value.playerList,
                        elementSummaries = value.elementSummaries,
                        scores = value.scoreList
                    ).sortedByDescending {
                        it.score
                    }.filter {
                        !it.score.isNaN() && it.score.isFinite()
                    },
                    attackerList = DataHolderList(
                        elements = value.playerList,
                        elementSummaries = value.elementSummaries,
                        scores = value.scoreList
                    ).sortedByDescending {
                        it.score
                    }.filter {
                        !it.score.isNaN() && it.score.isFinite() && it.element.elementType == 4
                    },
                    midfielderList = DataHolderList(
                        elements = value.playerList,
                        elementSummaries = value.elementSummaries,
                        scores = value.scoreList
                    ).sortedByDescending {
                        it.score
                    }.filter {
                        !it.score.isNaN() && it.score.isFinite() && it.element.elementType == 3
                    },
                    defenderList = DataHolderList(
                        elements = value.playerList,
                        elementSummaries = value.elementSummaries,
                        scores = value.scoreList
                    ).sortedByDescending {
                        it.score
                    }.filter {
                        !it.score.isNaN() && it.score.isFinite() && it.element.elementType == 2
                    },
                    goalkeeperList = DataHolderList(
                        elements = value.playerList,
                        elementSummaries = value.elementSummaries,
                        scores = value.scoreList
                    ).sortedByDescending {
                        it.score
                    }.filter {
                        !it.score.isNaN() && it.score.isFinite() && it.element.elementType == 1
                    }
                )
            }
        }
    }
    private suspend fun setGeneralTable(){
        dataSource.clearGeneral()
        dataSource.batchInsertIntoGeneral(_state.value.dataHolderList.map { it.toPlayers() })
    }
    private fun findBestTeam(){
        val goalkeeperList = _state.value.goalkeeperList.toMutableList()
        val defenderList = _state.value.defenderList.toMutableList()
        val midfielderList = _state.value.midfielderList.toMutableList()
        val attackerList = _state.value.attackerList.toMutableList()
        var defNumber = 0
        var midNumber = 0L
        var attNumber = 0

        val totalCost = 1000-
                (goalkeeperList.minBy { it.element.nowCost?:1000 }.element.nowCost?:1000) -
                (defenderList.minBy { it.element.nowCost?:1000 }.element.nowCost?:1000) -
                (defenderList.minBy { it.element.nowCost?:1000 }.element.nowCost?:1000) -
                (midfielderList.minBy { it.element.nowCost?:1000 }.element.nowCost?:1000) -
                (goalkeeperList.maxBy { it.score }.element.nowCost?:1000)

        val goalkeeper = goalkeeperList.maxBy { it.score }.toPlayers()

        //Defenders
        val bestDefenderList: List<Players> = run{
            var currentBest = Pair<Float?, List<Players>>(null, emptyList())
            CoroutineScope(Dispatchers.IO).launch {
                for (d1 in defenderList){
                    defNumber++
                    if (d1.element.nowCost == null) continue
                    for (d2 in defenderList){
                        defNumber++
                        if (d2.element.nowCost == null) continue
                        for (d3 in defenderList){
                            defNumber++
                            println("BestDefense: On combination $defNumber")
                            if (d3.element.nowCost == null) continue
                            if (listOf(d1, d2, d3).distinct().size != 3) continue
                            val price = d1.element.nowCost!! + d2.element.nowCost!! + d3.element.nowCost!!

                            if (price>totalCost/10*3) continue
                            val score = d1.score + d2.score + d3.score
                            if (currentBest.first == null) currentBest = Pair(score, listOf(
                                d1.toPlayers(),
                                d2.toPlayers(),
                                d3.toPlayers()
                            ))else if(currentBest.first!! < score){
                                currentBest = Pair(score, listOf(
                                    d1.toPlayers(),
                                    d2.toPlayers(),
                                    d3.toPlayers()
                                ))
                            }
                        }
                    }
                }
            }
            currentBest.second
        }

        //Midfielders
        val bestMidfielderList: List<Players> = run {
            var currentBest: Pair<Float?, List<Players>> = Pair(null, emptyList())
            CoroutineScope(Dispatchers.IO).launch {
                    for (m1 in midfielderList) {
                        midNumber++
                        if(m1.element.nowCost == null) continue
                        for (m2 in midfielderList) {
                            midNumber++
                            if (m2.element.nowCost == null) continue
                            for (m3 in midfielderList) {
                                midNumber++
                                if (m3.element.nowCost == null) continue
                                for (m4 in midfielderList) {
                                    midNumber++
                                    println("BestMidfield: On combination $midNumber")
                                    if (m4.element.nowCost == null) continue
                                    if (listOf(m1, m2, m3, m4).distinct().size != 4) continue

                                    val price = m1.element.nowCost!! + m2.element.nowCost!! + m3.element.nowCost!! + m4.element.nowCost!!
                                    if (price > totalCost/10*4) continue

                                    val score = m1.score + m2.score + m3.score + m4.score

                                    if (currentBest.first == null){
                                        currentBest = Pair(score, listOf(
                                            m1.toPlayers(),
                                            m2.toPlayers(),
                                            m3.toPlayers(),
                                            m4.toPlayers()
                                        ))
                                    }else if (currentBest.first!! < score){
                                        currentBest = Pair(score, listOf(
                                            m1.toPlayers(),
                                            m2.toPlayers(),
                                            m3.toPlayers(),
                                            m4.toPlayers()
                                        ))
                                    }
                                }
                            }
                        }
                    }
                }
            currentBest.second

        }

        //Attackers
        val bestAttackerList: List<Players> = run {
            var currentBest = Pair<Float?, List<Players>>(null, emptyList())
            CoroutineScope(Dispatchers.IO).launch {
                for (a1 in attackerList){
                    attNumber++
                    if (a1.element.nowCost == null) continue
                    for (a2 in attackerList){
                        attNumber++
                        if (a2.element.nowCost == null) continue
                        for (a3 in attackerList){
                            attNumber++
                            println("BestAttack: On combination $attNumber")
                            if (a3.element.nowCost == null) continue
                            if (listOf(a1, a2, a3).distinct().size != 3) continue

                            val price = a1.element.nowCost!! + a2.element.nowCost!! + a3.element.nowCost!!
                            if (price > totalCost/10*3) continue

                            val score = a1.score + a2.score + a3.score

                            if (currentBest.first == null){
                                currentBest = Pair(score, listOf(
                                    a1.toPlayers(),
                                    a2.toPlayers(),
                                    a3.toPlayers()
                                ))
                            }else if (currentBest.first!! < score){
                                currentBest = Pair(score, listOf(
                                    a1.toPlayers(),
                                    a2.toPlayers(),
                                    a3.toPlayers()
                                ))
                            }
                        }
                    }
                }
            }
            currentBest.second
        }
        _state.update { it.copy(
            bestTeam = listOf(goalkeeper).union(bestDefenderList).union(bestMidfielderList).union(bestAttackerList)
        ) }
    }
    private suspend fun setBestTable(){
        with(_state.value){
            dataSource.clearBest()
            dataSource.batchInsertIntoBest(bestTeam.toList())
        }
        _state.update { it.copy(
            finished = true
        ) }
    }
}