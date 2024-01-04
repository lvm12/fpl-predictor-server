package purpleagle.com.model.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.first
import purpleagle.com.model.database.DataSourceImpl
import purpleagle.com.model.models.PlayersHolder
import purpleagle.com.model.models.toSerializable

fun Route.fplRoutes(dataSource: DataSourceImpl){
    route("/fpl/api/"){
        get ("data"){
            call.respond<PlayersHolder>(PlayersHolder(
                generalTeam = dataSource
                    .getSavedPlayers(coroutineContext)
                    .first()
                    .map { it.toSerializable() },
                bestTeam = dataSource
                    .getBestTeam(coroutineContext)
                    .first()
                    .map { it.toSerializable() }
            ))
        }
    }
}