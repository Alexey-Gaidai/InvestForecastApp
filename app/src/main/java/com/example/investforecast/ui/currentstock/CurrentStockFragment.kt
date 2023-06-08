package com.example.investforecast.ui.currentstock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.investforecast.App
import com.example.investforecast.databinding.FragmentCurrentStockBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartZoomType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aachartcreator.aa_toAAOptions
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAXAxis
import com.google.android.material.snackbar.Snackbar

class CurrentStockFragment : Fragment() {
    private val stockPricesModel: CurrentStockViewModel by viewModels {
        CurrentStockViewModelFactory(
            App.repository,
            CurrentStockFragmentArgs.fromBundle(requireArguments()).stockName
        )
    }
    private var _binding: FragmentCurrentStockBinding? = null
    private val binding get() = _binding!!
    private val aaChartModel: AAChartModel = AAChartModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockPricesModel.stockForecast.observe(viewLifecycleOwner) { stockForecast ->
            if (stockForecast != null && stockForecast.isNotEmpty()) {
                // Данные доступны, можно инициализировать график
                initChart()
                requireActivity().title = stockPricesModel.ticker
            }
        }
        binding.npCount.value = 1
        binding.npCount.maxValue = 10000
        binding.npCount.minValue = 1
        binding.btAddToPortfolio.setOnClickListener{
            stockPricesModel.addStock(binding.npCount.value)
            Log.d("current", "clicked")
        }
    }

    private fun initChart() {
        val data = stockPricesModel.stockPrices.value!!.map { stockPrice ->
            arrayOf(stockPrice.close)
        }.toMutableList()

        val categories: MutableList<String> =
            stockPricesModel.stockPrices.value!!.map { stockPrice ->
                stockPrice.date.split('T').first()
            }.toMutableList()

        val dataPred = stockPricesModel.stockForecast.value!!.map { stockPrice ->
            arrayOf(stockPrice.close)
        }

        val categoriesPred: List<String> =
            stockPricesModel.stockForecast.value!!.map { stockPrice ->
                stockPrice.date.split('T').first()
            }

        // Создание серий данных
        val seriesElementActual = AASeriesElement()
            .data(data.toTypedArray())
            .name("actual")

        val seriesElementPred = AASeriesElement()
            .data(dataPred.toTypedArray())
            .name("predicted")

        // Добавление серий данных в модель графика
        aaChartModel
            .chartType(AAChartType.Line)
            .title(stockPricesModel.ticker)
            .zoomType(AAChartZoomType.XY)
            .dataLabelsEnabled(false)
            .categories(categoriesPred.toTypedArray())
            .backgroundColor("#FFFFFF")
            .series(arrayOf(seriesElementActual, seriesElementPred))

        binding.chartStockPrices.aa_drawChartWithChartModel(aaChartModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}