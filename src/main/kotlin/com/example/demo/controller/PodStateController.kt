package com.example.demo.controller

import com.example.demo.model.PodState
import com.example.demo.repository.PodStateRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import org.springframework.transaction.annotation.Transactional

@RestController
@RequestMapping("/pods")
class PodStateController(private val repository: PodStateRepository) {

    @GetMapping
    fun getAllPods(): List<PodState> = repository.findAll()

    @GetMapping("/{name}")
    suspend fun getPodByName(@PathVariable name: String): PodState? = repository.findByPodName(name)

    @PostMapping
    fun savePod(@RequestBody podState: PodState): PodState {
        podState.lastChecked = LocalDateTime.now()
        return repository.save(podState)
    }

    @PutMapping("/{name}/status")
    @Transactional
    suspend fun updatePodStatus(@PathVariable name: String, @RequestParam status: String): PodState {
        val pod = repository.findByPodName(name) ?: throw RuntimeException("Pod not found")
        pod.status = status
        pod.lastChecked = LocalDateTime.now()
        return repository.save(pod)
    }
}
