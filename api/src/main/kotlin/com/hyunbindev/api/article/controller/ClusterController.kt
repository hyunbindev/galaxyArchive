package com.hyunbindev.api.article.controller

import com.hyunbindev.article.cluster.data.UserClusterSnapShot
import com.hyunbindev.article.cluster.port.usecase.inbound.ClusterQueryUseCase
import com.hyunbindev.common.auth.LoginUserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/clusters")
class ClusterController(
    private val clusterQueryUseCase: ClusterQueryUseCase,
) {
    @GetMapping("/users/{userId}")
    fun getUserRecentCompletedCluster(@PathVariable userId: UUID): UserClusterSnapShot {
        return clusterQueryUseCase.getUserRecentCompletedCluster(userId)
    }
}
