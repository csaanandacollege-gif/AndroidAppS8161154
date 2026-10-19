package com.example.assignment2.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.data.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginState(val loading: Boolean = false, val error: String? = null, val keypass: String? = null)
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AssignmentRepository) : ViewModel() {
 private val mutableState = MutableStateFlow(LoginState())
 val state: StateFlow<LoginState> = mutableState
 fun login(username: String, password: String) {
  if (mutableState.value.loading) return
  if (username.isBlank() || password.isBlank()) {
   mutableState.value = LoginState(error = "Enter your student ID and first name."); return
  }
  if (username.any { !it.isDigit() }) {
   mutableState.value = LoginState(error = "Enter your student ID using digits only, without s."); return
  }
  mutableState.value = LoginState(loading = true)
  viewModelScope.launch {
   try { mutableState.value = LoginState(keypass = repository.login(username, password)) }
   catch (error: CancellationException) { throw error }
   catch (error: Exception) { mutableState.value = LoginState(error = errorMessage(error)) }
  }
 }
 fun navigationHandled() { mutableState.value = LoginState() }
}
