package purpleagle.com.model.models.official.util

import purpleagle.com.Players
import purpleagle.com.model.models.official.bootstrapstatic.Element
import purpleagle.com.model.models.official.elementsummary.ElementSummary

data class DataHolder(
    val element: Element,
    val elementSummary: ElementSummary,
    val score: Float
)

fun DataHolder.toPlayers(): Players{
    return Players(
        element.id!!.toLong(),
        element.webName?:"null",
        element.nowCost!!.toLong(),
        score.toDouble()
    )
}

@Suppress("FunctionName")
fun DataHolderList(
    elements: List<Element>,
    elementSummaries: Map<Int, ElementSummary>,
    scores: Map<Int, Float>
): List<DataHolder>{
    val list: MutableList<DataHolder> = mutableListOf()
    for(i in elements.indices){
        list.add(
            DataHolder(
            elements[i],
            elementSummaries[elements[i].id!!]!!,
            scores[elements[i].id!!]!!
        )
        )
    }
    return list
}