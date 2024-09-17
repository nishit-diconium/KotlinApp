package com.example.demo.service

import com.example.demo.repository.PodStateRepository
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import jakarta.annotation.PostConstruct

@Service
class PodCheckService(private val podStateRepository: PodStateRepository) {

    @PostConstruct
    fun schedulePodCheck() {
        GlobalScope.launch {
            while (true) {
                checkPods()
                delay(10000)  // Run every 10 seconds
            }
        }
    }

    private fun checkPods() {
        val pods = podStateRepository.findAll()
        pods.forEach { pod ->
            pod.lastChecked = LocalDateTime.now()
            pod.status = "Healthy"  // Assuming the pod is healthy (implement logic to check actual status)
            podStateRepository.save(pod)
        }
    }
}
