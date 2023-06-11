package com.example.investforecast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investforecast.App
import com.example.investforecast.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val model: HomeViewModel by viewModels{HomeViewModelFactory(App.repository)}
    private val adapter = NewsAdapter{
        openInBrowser(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initRecyclerView()
        observeChipGroup()
        binding.chipGroupView.isHorizontalScrollBarEnabled = false
        val categories =
            listOf("All", "Business", "Technology", "Finance", "Politics", "Industrials")
        for (category in categories) {
            val chip = Chip(binding.chipGroup.context)
            chip.text = category
            chip.isClickable = true
            chip.isCheckable = true
            binding.chipGroup.addView(chip)
        }
    }

    private fun observeChipGroup(){
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedIndustry = chip?.text.toString()
            model.loadNews(selectedIndustry, 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        model.news.observe(viewLifecycleOwner) { news ->
            adapter.submitList(news)
        }
    }

    private fun initRecyclerView() {
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(context)
    }

    private fun openInBrowser(url: String) {

    }
}