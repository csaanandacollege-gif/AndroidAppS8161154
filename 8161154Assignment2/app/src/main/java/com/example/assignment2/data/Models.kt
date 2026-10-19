package com.example.assignment2.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val keypass: String)
data class DashboardResponse(val entities: List<Map<String, Any?>>, val entityTotal: Int)

@Parcelize
data class Entity(val properties: Map<String, String>) : Parcelable {
 fun summary(): String = properties.filter { it.key != "description" }
  .map { "${it.key}: ${it.value}" }.joinToString("\n")
 fun details(): String = properties.map { "${it.key}\n${it.value}" }.joinToString("\n\n")
}
