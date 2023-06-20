package com.example.investforecast.ui.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investforecast.databinding.FragmentStocksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StocksFragment : Fragment() {

    private var _binding: FragmentStocksBinding? = null
    private val binding get() = _binding!!

    private val adapter = StocksAdapter{
        navigateToCurrentStock(it)
    }
    private val stocksModel: StocksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initRecyclerView()
    }

    private fun observeViewModel() {
        stocksModel.stocks.observe(viewLifecycleOwner) { stocks ->
            adapter.submitList(stocks)
        }
    }

    private fun initRecyclerView() {
        binding.rvStocks.adapter = adapter
        binding.rvStocks.layoutManager = LinearLayoutManager(context)
    }

    private fun navigateToCurrentStock(ticker: String){
        val action = StocksFragmentDirections.actionNavigationStocksToCurrentStockFragment(ticker)
        findNavController().navigate(action)
    }
}