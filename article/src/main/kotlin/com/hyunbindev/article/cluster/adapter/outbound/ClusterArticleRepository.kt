package com.hyunbindev.article.cluster.adapter.outbound

import com.hyunbindev.article.cluster.domain.persist.ClusterArticleEntity
import com.hyunbindev.article.cluster.domain.persist.UserClusterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClusterArticleRepository : JpaRepository<ClusterArticleEntity, Long> {
    fun findAllByUserClusterIn(userClusters: List<UserClusterEntity>): List<ClusterArticleEntity>

    @Query(
        """
        SELECT clusterArticle
        FROM ClusterArticleEntity clusterArticle
        JOIN FETCH clusterArticle.article
        WHERE clusterArticle.userCluster IN :userClusters
        """
    )
    fun findAllByUserClusterInFetchArticle(
        @Param("userClusters") userClusters: List<UserClusterEntity>,
    ): List<ClusterArticleEntity>
}
