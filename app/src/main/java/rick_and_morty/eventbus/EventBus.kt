package rick_and_morty.eventbus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus @Inject constructor() {

    private val _event = MutableSharedFlow<BusEvent>(extraBufferCapacity = 64)

    val events: Flow<BusEvent> = _event

    fun postEvent(event: BusEvent) {
        _event.tryEmit(event)
    }
}