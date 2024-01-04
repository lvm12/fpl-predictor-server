package purpleagle.com.model.network

import purpleagle.com.Players
import purpleagle.com.model.models.official.bootstrapstatic.BootStrapModel
import purpleagle.com.model.models.official.bootstrapstatic.Element
import purpleagle.com.model.models.official.elementsummary.ElementSummary
import purpleagle.com.model.models.official.util.DataHolder

data class ApiDataState(
    val bootStrapModel: BootStrapModel? = null,
    val elementSummaries: MutableMap<Int, ElementSummary> = mutableMapOf(),
    val load: String = "",
    val playerList: List<Element> = emptyList(),
    val scoreList: MutableMap<Int, Float> = mutableMapOf(),
    val dataHolderList: List<DataHolder> = emptyList(),
    val attackerList: List<DataHolder> = emptyList(),
    val midfielderList: List<DataHolder> = emptyList(),
    val defenderList: List<DataHolder> = emptyList(),
    val goalkeeperList: List<DataHolder> = emptyList(),
    val bestTeam: Set<Players> = emptySet(),
    val finished: Boolean = false
)
