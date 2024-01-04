package purpleagle.com.model.models.official.fixtures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class A (

    @SerialName("value"   ) var value   : Int? = null,
    @SerialName("element" ) var element : Int? = null

)