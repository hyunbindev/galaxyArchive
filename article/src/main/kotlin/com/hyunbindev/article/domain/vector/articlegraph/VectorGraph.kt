package com.hyunbindev.article.domain.vector.articlegraph

class VectorGraph(val edges:List<VectorEdge>) {
    private val parent = mutableMapOf<Long,Long>()
    fun getMinimumSpanningTree():List<VectorEdge>{
        val sortedEdges = isSorted(edges)
        return sortedEdges.asSequence()
            .filter {
                val rootV = find(it.v)
                val rootU = find(it.u)
                if(rootU != rootV){
                    union(rootV,rootU)
                    true
                }else{
                    false
                }
            }.toList()
    }

    private fun find(n:Long): Long {
        parent[n] = parent[n] ?: n
        if (parent[n] == n) return n

        parent[n] = find(parent[n]!!)

        return parent[n]?:n
    }

    private fun union(u:Long,v:Long){
        val rootU = find(u)
        val rootV = find(v)
        if(rootU != rootV) parent[rootU] = rootV
    }

    private fun isSorted(e:List<VectorEdge>):List<VectorEdge>{
        for (i in 0 until e.size-1){
            if(e[i] > e[i+1]) return e.sorted()
        }
        return e
    }
}