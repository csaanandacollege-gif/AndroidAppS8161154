package com.example.assignment2.ui
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assignment2.R
class DetailsFragment : Fragment(R.layout.fragment_details) {
 private val args: DetailsFragmentArgs by navArgs()
 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  view.findViewById<TextView>(R.id.details).text = args.entity.details()
  view.findViewById<Button>(R.id.backButton).setOnClickListener { findNavController().navigateUp() }
 }
}
