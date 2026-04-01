package com.daelim.springtest.main.controller

import com.daelim.springtest.main.api.model.dto.TestDto
import com.daelim.springtest.main.api.model.dto.TestDtoRequest
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import net.datafaker.Faker
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller {
    private val tests = mutableListOf<TestDto>()

    @PostMapping("/test")
    fun postTestDto(
        @RequestBody testDtoRequest: TestDtoRequest
    ): ResponseEntity<TestDto> {
        val faker = Faker(Locale.KOREA)
        val test = TestDto(
            id = testDtoRequest.id,
            address = faker.address().fullAddress(),
            email = faker.internet().emailAddress(),
            tel = faker.phoneNumber().phoneNumber(),
            age = Random().nextInt(100)
        )
        tests.add(test)
        return ResponseEntity.ok().body(test)
    }
    @GetMapping("/test")
    fun getAllTestDto(
    ): ResponseEntity<List<TestDto>> {
        val response = tests
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/test/{id}")
    fun getTestDto(
        @PathVariable("id") userId: String
    ): ResponseEntity<TestDto> {
        val response = tests.firstOrNull{it.id == userId}
        return ResponseEntity.ok().body(response)

    }
    @DeleteMapping("/test/{id}")
    fun deleteTestDto(
        @PathVariable("id") userId: String
    ): ResponseEntity<Unit> {
        // 조건에 맞는 요소를 삭제하고 삭제 여부를 반환 (MutableList 기준)
        val removed = tests.removeIf { it.id == userId }

        return if (removed) {
            // 삭제 성공 시 204 No Content
            ResponseEntity.noContent().build()
        } else {
            // 삭제할 대상을 찾지 못한 경우 404 Not Found
            ResponseEntity.notFound().build()
        }
    }
}