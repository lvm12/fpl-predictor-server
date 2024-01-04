package purpleagle.com.model.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import purpleagle.com.Players
import purpleagle.com.model.models.toBestTeam
import purpleagle.com.model.models.toPlayers
import purpleeagle.com.playersdatabase.PlayersDatabase
import kotlin.coroutines.CoroutineContext

class DataSourceImpl(
    db: PlayersDatabase
) : DataSource {

    private val bestTeamQueries = db.bestTeamQueries
    private val playersQueries = db.playersQueries

    override fun getSavedPlayers(context: CoroutineContext): Flow<List<Players>> {
        return playersQueries
            .getAllPlayers()
            .asFlow()
            .mapToList(context)
    }

    override fun getBestTeam(context: CoroutineContext): Flow<List<Players>> {
        return bestTeamQueries
            .getWholeTeam()
            .asFlow()
            .mapToList(context)
            .map{ list ->
                list.map { it.toPlayers()
                }
            }
    }

    override suspend fun insertIntoGeneral(player: Players): Boolean {
        return try {
            playersQueries.insertPlayer(player)
            true
        }catch (e: Exception) {false}
    }

    override suspend fun insertIntoBest(player: Players): Boolean {
        return try {
            bestTeamQueries.insertPlayer(player.toBestTeam())
            true
        }catch (e: Exception) {false}
    }

    override suspend fun batchInsertIntoGeneral(players: List<Players>): Boolean {
        return try {
            players.forEach {
                insertIntoGeneral(it)
            }
            true
        }catch (e: Exception){
            false
        }
    }

    override suspend fun batchInsertIntoBest(players: List<Players>): Boolean {
        return try {
            players.forEach {
                insertIntoBest(it)
            }
            true
        }catch (e: Exception){
            false
        }
    }

    override suspend fun deleteGeneral(): Boolean {
        return try {
            playersQueries.deleteTable()
            true
        }catch (e: Exception){
            false
        }
    }

    override suspend fun deleteBest(): Boolean {
        return try {
            bestTeamQueries.deleteTable()
            true
        }catch (e: Exception){
            false
        }
    }

    override suspend fun vacuumGeneral(): Boolean {
        return try {
            playersQueries.vaccum()
            true
        }catch (e: Exception){
            false
        }
    }

    override suspend fun vacuumBest(): Boolean {
        return try {
            bestTeamQueries.vacuum()
            true
        }catch (e: Exception){
            false
        }
    }

    override suspend fun clearGeneral(): Boolean {
        return deleteGeneral() && vacuumGeneral()
    }

    override suspend fun clearBest(): Boolean {
        return deleteBest() && vacuumBest()
    }
}