package com.hyunbindev.article.cluster.adapter.outbound

import com.hyunbindev.article.article.data.ArticleKeywordDto
import com.hyunbindev.article.cluster.data.ClusterKeywordDto
import com.hyunbindev.article.cluster.domain.persist.UserClusterEntity
import com.hyunbindev.article.cluster.domain.persist.UserClusterSnapshotEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserClusterRepository : JpaRepository<UserClusterEntity, Long> {
    fun findAllBySnapshot(snapshot: UserClusterSnapshotEntity): List<UserClusterEntity>

    @Query("""
        SELECT clusterId,
           keyword,
           similarity
        FROM (SELECT a.cluster_id           AS clusterId,
                     akw.keyword            AS keyword,
                     AVG(akw.similarity)    AS similarity,
                     ROW_NUMBER() OVER (PARTITION BY a.cluster_id ORDER BY AVG(akw.similarity) DESC) AS rn
              FROM article_key_word_entity akw
                       JOIN cluster_article_entity a ON a.article_id = akw.article_id
                       JOIN user_cluster_entity uc ON uc.id = a.cluster_id
              WHERE uc.snapshot_id = :snapShotId
              GROUP BY a.cluster_id, akw.keyword) ranked
        WHERE rn <= :limit
        ORDER BY clusterId, similarity DESC
    """, nativeQuery = true)
    fun findKeyWordBySnapShotIdWithClusterGroup(snapShotId:Long, limit:Int=5):List<ClusterKeywordDto>
}
