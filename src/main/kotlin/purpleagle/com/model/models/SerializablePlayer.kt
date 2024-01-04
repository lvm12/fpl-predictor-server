package purpleagle.com.model.models

import kotlinx.serialization.Serializable
import purpleagle.com.BestTeam
import purpleagle.com.Players

@Serializable
data class SPlayers(
    val id: Long,
    val name: String,
    val cost: Long,
    val score: Double?,
)
fun BestTeam.toPlayers(): Players{
    return Players(
        id, name, cost, score
    )
}

fun Players.toBestTeam(): BestTeam{
    return BestTeam(
        id, name, cost, score
    )
}

fun SPlayers.toSql(): Players{
    return Players(
        id, name, cost, score
    )
}

fun Players.toSerializable(): SPlayers{
    return SPlayers(
        id, name, cost, score
    )
}