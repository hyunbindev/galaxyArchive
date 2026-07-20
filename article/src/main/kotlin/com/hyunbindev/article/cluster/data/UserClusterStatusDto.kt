package com.hyunbindev.article.cluster.data

data class UserClusterStatusDto(
    val articleCount: Int,
    val clusterCount: Int
){
    companion object {
        fun fallBack():UserClusterStatusDto{
            return UserClusterStatusDto(
                articleCount = 0,
                clusterCount = 0
            )
        }
    }
}
