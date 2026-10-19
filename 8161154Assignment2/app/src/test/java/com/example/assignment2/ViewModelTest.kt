package com.example.assignment2
import com.example.assignment2.data.*
import com.example.assignment2.ui.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.io.IOException
@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {
 private val dispatcher = StandardTestDispatcher()
 private lateinit var repository: AssignmentRepository
 @Before fun setup() { Dispatchers.setMain(dispatcher); repository = mockk() }
 @After fun cleanup() { Dispatchers.resetMain() }
 @Test fun `blank credentials do not call repository`() = runTest {
  val vm = LoginViewModel(repository)
  vm.login("", "")
  assertNotNull(vm.state.value.error)
  coVerify(exactly = 0) { repository.login(any(), any()) }
 }
 @Test fun `student ID with s is rejected`() = runTest {
  val vm = LoginViewModel(repository)
  vm.login("s8161154", "Name")
  assertNotNull(vm.state.value.error)
  coVerify(exactly = 0) { repository.login(any(), any()) }
 }
 @Test fun `successful login exposes server keypass`() = runTest {
  coEvery { repository.login("8161154", "Name") } returns "example"
  val vm = LoginViewModel(repository)
  vm.login("8161154", "Name")
  assertTrue(vm.state.value.loading)
  advanceUntilIdle()
  assertEquals("example", vm.state.value.keypass)
  assertFalse(vm.state.value.loading)
  vm.navigationHandled()
  assertNull(vm.state.value.keypass)
 }
 @Test fun `network failure shows error and permits retry`() = runTest {
  coEvery { repository.login(any(), any()) } throws IOException()
  val vm = LoginViewModel(repository)
  vm.login("8161154", "Name")
  advanceUntilIdle()
  assertNotNull(vm.state.value.error)
  assertFalse(vm.state.value.loading)
  coEvery { repository.login(any(), any()) } returns "retry"
  vm.login("8161154", "Name")
  advanceUntilIdle()
  assertEquals("retry", vm.state.value.keypass)
 }
 @Test fun `duplicate login taps make one request`() = runTest {
  coEvery { repository.login(any(), any()) } returns "example"
  val vm = LoginViewModel(repository)
  vm.login("8161154", "Name"); vm.login("8161154", "Name")
  advanceUntilIdle()
  coVerify(exactly = 1) { repository.login(any(), any()) }
 }
 @Test fun `dashboard loads using login keypass and avoids duplicate fetch`() = runTest {
  val entities = listOf(Entity(mapOf("title" to "Example", "description" to "Full description")))
  coEvery { repository.dashboard("example") } returns entities
  val vm = DashboardViewModel(repository)
  vm.load("example"); advanceUntilIdle()
  assertEquals(entities, vm.state.value.entities)
  vm.load("example"); advanceUntilIdle()
  coVerify(exactly = 1) { repository.dashboard("example") }
 }
 @Test fun `dashboard failure can be retried`() = runTest {
  coEvery { repository.dashboard("example") } throws IOException()
  val vm = DashboardViewModel(repository)
  vm.load("example"); advanceUntilIdle()
  assertNotNull(vm.state.value.error)
  coEvery { repository.dashboard("example") } returns emptyList()
  vm.load("example", true); advanceUntilIdle()
  assertNull(vm.state.value.error)
  assertTrue(vm.state.value.entities.isEmpty())
  assertFalse(vm.state.value.loading)
 }
}
