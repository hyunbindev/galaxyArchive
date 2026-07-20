package com.hyunbindev.article.cluster.port.usecase.inbound

import com.hyunbindev.article.cluster.data.UserClusterStatusDto
import java.util.UUID

interface UserClusterStatusQueryUseCase {
    fun getUserClusterStatus(userId: UUID): UserClusterStatusDto
}