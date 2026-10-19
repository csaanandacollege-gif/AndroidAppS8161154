package com.example.assignment2.ui
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
 private val viewModel: DashboardViewModel by viewModels()
 private val args: DashboardFragmentArgs by navArgs()
 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  val adapter = EntityAdapter { entity ->
   findNavController().navigate(DashboardFragmentDirections.actionDashboardToDetails(entity))
  }
  view.findViewById<RecyclerView>(R.id.entities).apply {
   layoutManager = LinearLayoutManager(requireContext())
   this.adapter = adapter
  }
  view.findViewById<Button>(R.id.retry).setOnClickListener { viewModel.load(args.keypass, true) }
  viewLifecycleOwner.lifecycleScope.launch {
   viewModel.state.collect { state ->
    adapter.setData(state.entities)
    view.findViewById<ProgressBar>(R.id.progress).visibility = if (state.loading) View.VISIBLE else View.GONE
    view.findViewById<TextView>(R.id.status).text = state.error ?: if (state.loading) "Loading entities…" else if (state.entities.isEmpty()) "No entities available." else "${state.entities.size} entities • Tap an item for details"
    view.findViewById<Button>(R.id.retry).visibility = if (state.error != null) View.VISIBLE else View.GONE
   }
  }
  viewModel.load(args.keypass)
 }
}
