package com.example.assignment2.data
import javax.inject.Inject
class AssignmentRepository @Inject constructor(private val api: AssignmentApi) {
 suspend fun login(username: String, password: String): String {
  val keypass = api.login(LoginRequest(username, password)).keypass
  check(keypass.isNotBlank()) { "The server returned an empty keypass." }
  return keypass
 }
 suspend fun dashboard(keypass: String): List<Entity> = api.dashboard(keypass).entities.map { item ->
  Entity(item.mapValues { it.value?.toString() ?: "Not provided" })
 }
}
