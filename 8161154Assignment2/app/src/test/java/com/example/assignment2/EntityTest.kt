package com.example.assignment2
import com.example.assignment2.data.Entity
import org.junit.Test
import org.junit.Assert.*
class EntityTest {
 private val entity = Entity(mapOf("name" to "Example", "category" to "Travel", "description" to "Detailed explanation"))
 @Test fun `summary excludes description`() {
  assertTrue(entity.summary().contains("Example"))
  assertTrue(entity.summary().contains("Travel"))
  assertFalse(entity.summary().contains("Detailed explanation"))
 }
 @Test fun `details includes every property`() {
  entity.properties.forEach { (key, value) ->
   assertTrue(entity.details().contains(key)); assertTrue(entity.details().contains(value))
  }
 }
}
