package purpleagle.com.model.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayersHolder(
    val generalTeam: List<SPlayers>,
    val bestTeam: List<SPlayers>
)
