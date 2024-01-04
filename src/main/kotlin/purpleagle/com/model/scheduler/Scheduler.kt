package purpleagle.com.model.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object Scheduler {
    fun <returnType> scheduleFunction(
        delay: Long,
        coroutineContext: CoroutineContext,
        function: () -> returnType
    ){
        CoroutineScope(coroutineContext).launch {
            delay(delay)
            function()
        }
    }
}