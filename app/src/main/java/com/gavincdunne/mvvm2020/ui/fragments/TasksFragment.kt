package com.gavincdunne.mvvm2020.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gavincdunne.mvvm2020.R
import com.gavincdunne.mvvm2020.databinding.FragmentTasksBinding
import com.gavincdunne.mvvm2020.ui.adapters.TasksAdapter
import com.gavincdunne.mvvm2020.ui.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {
    private val viewModel: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)
        val taskAdapter = TasksAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }
    }
}