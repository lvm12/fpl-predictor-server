package purpleagle.com

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import purpleagle.com.model.database.DataSourceImpl
import purpleagle.com.model.database.DatabaseDriverFactory
import purpleagle.com.model.network.CalculateUtil
import purpleagle.com.model.scheduler.Scheduler
import purpleagle.com.plugins.*
import purpleeagle.com.playersdatabase.PlayersDatabase
import java.lang.Thread.sleep

fun main() {
    println("In main function")
    val dataSource = DataSourceImpl(
        db = PlayersDatabase.invoke(
            DatabaseDriverFactory().init()
        )
    )
    CoroutineScope(Dispatchers.IO).launch {
        println("In coroutine scope")
        while (true) {
            println("In while loop")
            val start = Clock.System.now()
            CalculateUtil(
                dataSource = dataSource,
                scope = this
            ).execute()
            val end = Clock.System.now()
            println((end - start).toIsoString())
            sleep(1000 * 60 * 60 * 12 - (end - start).inWholeMilliseconds)
        }
    }
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = {
        module(dataSource)
    })
        .start(wait = true)
}

fun Application.module(dataSource: DataSourceImpl) {
    configureSerialization()
    configureRouting(dataSource)
}
