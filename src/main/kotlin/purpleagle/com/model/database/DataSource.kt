package purpleagle.com.model.database

import kotlinx.coroutines.flow.Flow
import purpleagle.com.Players
import kotlin.coroutines.CoroutineContext

interface DataSource {
    fun getSavedPlayers(context: CoroutineContext): Flow<List<Players>>
    fun getBestTeam(context: CoroutineContext): Flow<List<Players>>
    suspend fun insertIntoGeneral(player: Players): Boolean
    suspend fun insertIntoBest(player: Players): Boolean
    suspend fun batchInsertIntoGeneral(players: List<Players>): Boolean
    suspend fun batchInsertIntoBest(players: List<Players>): Boolean
    suspend fun deleteGeneral(): Boolean
    suspend fun deleteBest(): Boolean
    suspend fun vacuumGeneral(): Boolean
    suspend fun vacuumBest(): Boolean
    suspend fun clearGeneral(): Boolean
    suspend fun clearBest(): Boolean
}