package com.example.assignment2.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.data.AssignmentRepository
import com.example.assignment2.data.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DashboardState(val loading: Boolean = false, val entities: List<Entity> = emptyList(), val error: String? = null)
@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: AssignmentRepository) : ViewModel() {
 private val mutableState = MutableStateFlow(DashboardState())
 val state: StateFlow<DashboardState> = mutableState
 private var loadedKey: String? = null
 fun load(keypass: String, retry: Boolean = false) {
  if (mutableState.value.loading || (!retry && loadedKey == keypass)) return
  mutableState.value = DashboardState(loading = true)
  viewModelScope.launch {
   try {
    val entities = repository.dashboard(keypass)
    loadedKey = keypass
    mutableState.value = DashboardState(entities = entities)
   } catch (error: CancellationException) { throw error }
   catch (error: Exception) { mutableState.value = DashboardState(error = errorMessage(error)) }
  }
 }
}
