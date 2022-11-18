package com.luanrodrigsr.controlelicitacoes.ui.main.processes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.luanrodrigsr.controlelicitacoes.MainApplication
import com.luanrodrigsr.controlelicitacoes.adapter.ProcessListAdapter
import com.luanrodrigsr.controlelicitacoes.databinding.FragmentProcessesBinding

class ProcessesFragment : Fragment() {

    private val viewModel: ProcessesViewModel by activityViewModels {
        ProcessesViewModelFactory(
            ((activity?.application) as MainApplication).database
        )
    }

    private var _binding: FragmentProcessesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProcessesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProcessListAdapter {
            val action = ProcessesFragmentDirections
                .actionProcessesFragmentToEditProcessFragment()
            this.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.allProcess.observe(this.viewLifecycleOwner) { processes ->
            adapter.submitList(processes)
        }

        viewModel.processesListState.observe(this.viewLifecycleOwner) { state ->
            updateScreen(state)
        }

        binding.fab.setOnClickListener {
            val action = ProcessesFragmentDirections
                .actionProcessesFragmentToAddProcessFragment()
            this.findNavController().navigate(action)
        }

        viewModel.retrieveProcesses()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateScreen(state: ProcessesListState?) {
        when (state) {
            ProcessesListState.LOADING -> {
                binding.recyclerView.visibility = GONE
                binding.layoutEmpty.visibility = GONE
                binding.layoutLoading.visibility = VISIBLE
            }
            ProcessesListState.LOADED -> {
                binding.recyclerView.visibility = VISIBLE
                binding.layoutEmpty.visibility = GONE
                binding.layoutLoading.visibility = GONE
            }
            ProcessesListState.EMPTY -> {
                binding.recyclerView.visibility = GONE
                binding.layoutEmpty.visibility = VISIBLE
                binding.layoutLoading.visibility = GONE
            }
            else -> {
                binding.recyclerView.visibility = GONE
                binding.layoutEmpty.visibility = GONE
                binding.layoutLoading.visibility = GONE
            }
        }
    }
}