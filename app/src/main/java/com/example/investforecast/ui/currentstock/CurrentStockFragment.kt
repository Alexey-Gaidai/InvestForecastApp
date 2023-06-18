package com.example.investforecast.ui.currentstock

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.investforecast.App
import com.example.investforecast.R
import com.example.investforecast.databinding.FragmentCurrentStockBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartZoomType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

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
    lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            CurrentStockFragmentArgs.fromBundle(requireArguments()).stockName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAlertLoading()
        observeViewModel()
        initNumberPicker()
        addToPortfolioClicked()
    }

    private fun initAlertLoading() {
        val customLayout = LayoutInflater.from(requireContext())
            .inflate(R.layout.custom_loading, null)

        loadingDialog = AlertDialog.Builder(requireContext())
            .setView(customLayout)
            .setCancelable(false)
            .create()

        // Показать AlertDialog с ProgressBar при начале загрузки
        loadingDialog.show()
    }

    private fun initForecastData() {
        val forecast = stockPricesModel.stockForecast.value!!.takeLast(30).map { it.close }
        binding.tvForecastPriceValue.text = forecast.average().format(2).toString()
        binding.tvForecastRangeValue.text =
            "${forecast.min().format(2)}-${forecast.max().format(2)}"
    }

    private fun initMarketData() {
        val lastData = stockPricesModel.stockPrices.value!!.last()
        binding.tvOpenValue.text = lastData.open.format(2)
        binding.tvCloseValue.text = lastData.close.format(2)
        binding.tvVolumeValue.text = lastData.volume.toString()
        binding.tvRangeValue.text = "${lastData.low.format(2)}-${lastData.high.format(2)}"
    }

    private fun addToPortfolioClicked() {
        binding.btAddToPortfolio.setOnClickListener {
            stockPricesModel.addStock(binding.npCount.value)
            Log.d("current", "clicked")
        }
    }


    private fun initNumberPicker() {
        binding.npCount.value = 1
        binding.npCount.maxValue = 10000
        binding.npCount.minValue = 1
        binding.npCount.isReadOnly = true
        binding.npCount.buttonAnimationEnabled = false

    }

    private fun observeViewModel() {
        stockPricesModel.stockForecast.observe(viewLifecycleOwner) { stockForecast ->
            if (stockForecast != null && stockForecast.isNotEmpty()) {
                initChart()
                loadingDialog.dismiss()
                initForecastData()
                initMarketData()
            }
        }
    }

    private fun initChart() {
        val data = stockPricesModel.stockPrices.value!!.map { stockPrice ->
            arrayOf(stockPrice.close)
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

fun Double.format(digits: Int) = "%.${digits}f".format(this)