package purpleagle.com.model.models.official.elementsummary

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ElementSummary(
    val fixtures: List<Fixture>,
    val history: List<History>,
    @SerialName("history_past") val historyPast: List<HistoryPast>
)
