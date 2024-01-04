package purpleagle.com.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import purpleagle.com.model.database.DataSourceImpl
import purpleagle.com.model.routing.fplRoutes

fun Application.configureRouting(dataSource: DataSourceImpl) {
    routing {
        fplRoutes(dataSource)
    }
}
