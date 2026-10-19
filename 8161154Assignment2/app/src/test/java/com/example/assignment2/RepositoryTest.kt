package com.example.assignment2
import com.example.assignment2.data.*
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
class RepositoryTest {
 private val api: AssignmentApi = mockk()
 private val repository = AssignmentRepository(api)
 @Test fun `login forwards credentials without changing password case`() = runTest {
  coEvery { api.login(LoginRequest("123", "FirstName")) } returns LoginResponse("topic")
  assertEquals("topic", repository.login("123", "FirstName"))
  coVerify { api.login(LoginRequest("123", "FirstName")) }
 }
 @Test fun `dashboard preserves dynamic properties and description`() = runTest {
  coEvery { api.dashboard("topic") } returns DashboardResponse(listOf(mapOf("name" to "Place", "year" to 2024, "description" to "Long text", "optional" to null)), 1)
  val entity = repository.dashboard("topic").first()
  assertEquals("Place", entity.properties["name"])
  assertEquals("2024", entity.properties["year"])
  assertEquals("Long text", entity.properties["description"])
  assertEquals("Not provided", entity.properties["optional"])
 }
 @Test fun `blank keypass is rejected`() = runTest {
  coEvery { api.login(any()) } returns LoginResponse("")
  try { repository.login("123", "Name"); fail("Expected failure") }
  catch (expected: IllegalStateException) { assertNotNull(expected.message) }
 }
}
