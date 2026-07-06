package com.hyunbindev.article.cluster.port.usecase.inbound

import com.hyunbindev.article.cluster.data.UserClusterSnapShot
import java.util.UUID

interface ClusterQueryUseCase {
    fun getUserRecentCompletedCluster(userId: UUID): UserClusterSnapShot
}
