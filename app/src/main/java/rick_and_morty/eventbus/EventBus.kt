package rick_and_morty.eventbus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus @Inject constructor() {

    private val bus = MutableSharedFlow<BusEvent>(extraBufferCapacity = 64)

    val events: Flow<BusEvent> = bus

    fun postEvent(event: BusEvent) {
        bus.tryEmit(event)
    }
}