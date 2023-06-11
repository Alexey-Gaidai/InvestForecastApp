package com.example.investforecast.ui.mystocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investforecast.App
import com.example.investforecast.R
import com.example.investforecast.databinding.FragmentMystocksBinding
import com.example.investforecast.domain.model.Portfolio
import com.example.investforecast.ui.stocks.StocksAdapter
import com.example.investforecast.ui.stocks.StocksFragmentDirections
import com.example.investforecast.ui.stocks.StocksViewModel
import com.example.investforecast.ui.stocks.StocksViewModelFactory
import kotlin.math.round

class MyStocksFragment : Fragment() {

    private var _binding: FragmentMystocksBinding? = null
    private val binding get() = _binding!!

    private val adapter = MyStocksAdapter{
        navigateToCurrentStock(it)
    }
    private val myStocksModel: MyStocksViewModel by viewModels { MyStocksViewModelFactory(App.repository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMystocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefreshLayout()
        observeViewModel()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        myStocksModel.portfolio.observe(viewLifecycleOwner) { portfolio ->
            adapter.submitList(portfolio.investmentPortfolio.stocks)
            initTotal(portfolio)
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            myStocksModel.loadPortfolio()
        }
    }

    private fun initTotal(portfolio: Portfolio) {
        binding.tvCosts.text = portfolio.investmentPortfolio.total.toString() + "₽"
        val percents = round(portfolio.investmentPortfolio.returns / portfolio.investmentPortfolio.total * 100.00)
        binding.tvReturns.text = portfolio.investmentPortfolio.returns.toString()  + "(${percents})"
        if (portfolio.investmentPortfolio.returns > 0)
            binding.tvReturns.setTextColor(resources.getColor(R.color.green_returns))
        else
            binding.tvReturns.setTextColor(resources.getColor(R.color.red_returns))
    }

    private fun initRecyclerView() {
        binding.rvMyStocks.adapter = adapter
        binding.rvMyStocks.layoutManager = LinearLayoutManager(context)
    }

    private fun navigateToCurrentStock(ticker: String){
        val action = MyStocksFragmentDirections.actionNavigationMystocksToCurrentStockFragment(ticker)
        findNavController().navigate(action)
    }
}