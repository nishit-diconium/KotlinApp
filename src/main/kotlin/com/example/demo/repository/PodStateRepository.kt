package com.example.demo.repository

import com.example.demo.model.PodState
import com.example.demo.ApiConstants
import com.example.demo.ApiInterface
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PodStateRepository : JpaRepository<PodState, Long> {
    suspend fun findByPodName(pageNumber: String): PodState {
        val url =  ApiConstants.API_URL_BY_POD_NAME +"?page=$pageNumber&limit=10"
        return ApiInterface.create().getPodData(url)
    }

}
