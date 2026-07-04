package com.hyunbindev.user.port.event.outbound

import java.util.UUID

interface ClusterCreateEventPublishPort {
    fun publishCreateEvent(userId: UUID)
}