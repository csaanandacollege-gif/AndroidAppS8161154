package com.example.assignment2.ui
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.assignment2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
 private val viewModel: LoginViewModel by viewModels()
 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  val button = view.findViewById<Button>(R.id.loginButton)
  val username = view.findViewById<EditText>(R.id.username)
  val password = view.findViewById<EditText>(R.id.password)
  button.setOnClickListener { viewModel.login(username.text.toString().trim(), password.text.toString()) }
  viewLifecycleOwner.lifecycleScope.launch {
   viewModel.state.collect { state ->
    button.isEnabled = !state.loading
    view.findViewById<ProgressBar>(R.id.progress).visibility = if (state.loading) View.VISIBLE else View.GONE
    view.findViewById<TextView>(R.id.error).text = state.error ?: ""
    state.keypass?.let { keypass ->
     viewModel.navigationHandled()
     findNavController().navigate(LoginFragmentDirections.actionLoginToDashboard(keypass))
    }
   }
  }
 }
}
