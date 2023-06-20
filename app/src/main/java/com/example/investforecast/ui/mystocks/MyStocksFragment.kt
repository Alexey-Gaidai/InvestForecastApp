package com.example.investforecast.ui.mystocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investforecast.R
import com.example.investforecast.databinding.FragmentMystocksBinding
import com.example.investforecast.domain.model.Portfolio
import com.example.investforecast.ui.currentstock.format
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStocksFragment : Fragment() {

    private var _binding: FragmentMystocksBinding? = null
    private val binding get() = _binding!!
    private val aaChartModel: AAChartModel = AAChartModel()
    private val adapter = MyStocksAdapter{
        navigateToCurrentStock(it)
    }
    private val myStocksModel: MyStocksViewModel by viewModels()

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
            initChart()
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            myStocksModel.loadPortfolio()
        }
    }

    private fun initTotal(portfolio: Portfolio) {
        binding.tvCosts.text = portfolio.investmentPortfolio.total.format(2) + "₽"
        binding.tvReturns.text = portfolio.investmentPortfolio.returns.monetaryReturn.toString()
        binding.tvPercentsValue.text = portfolio.investmentPortfolio.returns.percentageReturn.toString()
        if (portfolio.investmentPortfolio.returns.percentageReturn > 0)
        {
            binding.tvReturns.setTextColor(resources.getColor(R.color.green_returns))
            binding.tvPercentsValue.setTextColor(resources.getColor(R.color.green_returns))
        }
        else
        {
            binding.tvReturns.setTextColor(resources.getColor(R.color.red_returns))
            binding.tvPercentsValue.setTextColor(resources.getColor(R.color.red_returns))
        }
    }

    private fun initChart(){
        aaChartModel
            .chartType(AAChartType.Pie)
            .title("Портфель акций")
            .subtitle("Соотношение акций в портфеле")
            .backgroundColor("#ffffff")
            .dataLabelsEnabled(true)
            .legendEnabled(true)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Акции")
                        .innerSize("50%") // Внутренний радиус для создания doughnut-стиля
                        .data(getStocksData()) // Получение данных акций для графика
                )
            )

        binding.aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun getStocksData(): Array<Any> {
        val stocksData = mutableListOf<Any>()
        val portfolio = myStocksModel.portfolio.value?.investmentPortfolio?.stocks
        portfolio?.forEach { stock ->
            val stockData = arrayOf(stock.name, stock.total)
            stocksData.add(stockData)
        }
        return stocksData.toTypedArray()
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