package purpleagle.com.model.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import purpleeagle.com.playersdatabase.PlayersDatabase


class DatabaseDriverFactory {
    fun init(): SqlDriver{
        val driver = JdbcSqliteDriver(
            "jdbc:sqlite:PlayersDatabase.db"
        )
        PlayersDatabase.Schema.create(driver)
        return driver
    }
}