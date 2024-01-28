package com.qndev.weatherforecast.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qndev.weatherforecast.R
import com.qndev.weatherforecast.data.model.City
import com.qndev.weatherforecast.databinding.ActivityMainBinding
import com.qndev.weatherforecast.domain.DateFormat
import com.qndev.weatherforecast.domain.getAssetJsonData
import com.qndev.weatherforecast.domain.toLocalTime
import com.qndev.weatherforecast.presentation.adapter.NextDayAdapter
import com.qndev.weatherforecast.presentation.dialog.BookmarkDialog
import com.qndev.weatherforecast.presentation.sharepref.CityManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var selectedCity: City
    private lateinit var cityList: List<City>
    private lateinit var nextDayAdapter: NextDayAdapter
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var cityManager: CityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setUpSpinner()
        initRecyclerView()
        observeWeatherState()
        handleEventListener()
    }


    private fun setUpSpinner() {
        val cityJson = getAssetJsonData(this)
        val cityListType = object : TypeToken<List<City>>() {}.type
        cityList = Gson().fromJson(cityJson, cityListType)

        val adapter = ArrayAdapter(this, R.layout.spinner_city, cityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityMainBinding.spnCity.adapter = adapter
        activityMainBinding.spnCity.setTitle("Search City")
    }

    private fun handleEventListener() {
        // Swipe to refresh
        activityMainBinding.parent.setOnRefreshListener {
            viewModel.getCurrentWeather(selectedCity.lat.toDouble(), selectedCity.lng.toDouble())
        }
        // Save city
        activityMainBinding.saveCb.setOnClickListener {
            cityManager.toggleFavorite(selectedCity)
        }
        // Select city
        activityMainBinding.spnCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCity = adapterView?.getItemAtPosition(position) as City
                val lat = selectedCity.lat.toDouble()
                val lng = selectedCity.lng.toDouble()
                viewModel.getCurrentWeather(lat, lng)

                // Check if city is saved or not
                checkSavedCity()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        // Open bookmark
        activityMainBinding.bookmark.setOnClickListener {
            val savedCity = cityManager.getFavorites()
            if (savedCity.isEmpty()) {
                Toast.makeText(this, "No favorite city saved", Toast.LENGTH_SHORT).show()
            } else {
                val dialogFragment = BookmarkDialog(savedCity) { city ->
                    viewModel.getCurrentWeather(city.lat.toDouble(), city.lng.toDouble())

                    //Set selected spinner item after click on bookmark
                    val selectedCityPosition = cityList.indexOf(city)
                    activityMainBinding.spnCity.setSelection(selectedCityPosition)
                }
                dialogFragment.show(supportFragmentManager, "BookmarkDialog")
            }
        }
    }

    private fun observeWeatherState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.weatherState
                    .distinctUntilChanged()
                    .collect {
                        when (it.state) {
                            State.SUCCESS -> updateUi(it)
                            State.FAIL -> showError()
                            State.LOADING -> activityMainBinding.parent.isRefreshing = true
                            else -> {}
                        }
                    }
            }
        }
    }

    private fun updateUi(weatherState: WeatherState) {
        val currentWeatherData = weatherState.weatherData?.current!!
        activityMainBinding.errorText.isVisible = false
        activityMainBinding.parent.isRefreshing = false
        activityMainBinding.dateTimeTv.text = currentWeatherData.time.toLocalTime(DateFormat.DATE_TIME)
        activityMainBinding.humidityTv.text = getString(R.string.humidity, currentWeatherData.humidity)
        activityMainBinding.tempTv.text = getString(R.string.temp, currentWeatherData.temp.roundToInt())
        activityMainBinding.windSpeedTv.text = getString(R.string.wind_speed, currentWeatherData.windSpeed)
        activityMainBinding.descriptionTv.text = currentWeatherData.description[0].description
        val iconId = currentWeatherData.description[0].icon
        val imageUrl = "https://openweathermap.org/img/wn/${iconId}@4x.png"
        activityMainBinding.weatherImg.load(imageUrl) {
            error(R.drawable.cloudy)
        }
        // Update next day data
        val nextDayWeatherData = weatherState.weatherData.daily
        nextDayAdapter.updateForecastList(nextDayWeatherData.subList(1, 4)) // Get next 3 days data
    }

    private fun showError() {
        activityMainBinding.errorText.isVisible = true
        activityMainBinding.parent.isRefreshing = false
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage("Can't get real time weather data!")
            setPositiveButton("Dismiss") { dialog, _ -> dialog.dismiss() }
            setNegativeButton("Retry") { _, _ ->
                viewModel.getCurrentWeather(
                    selectedCity.lat.toDouble(),
                    selectedCity.lng.toDouble()
                )
            }
            setCancelable(false)
            show()
        }
    }

    private fun initRecyclerView() {
        nextDayAdapter = NextDayAdapter()
        activityMainBinding.rcvForecast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activityMainBinding.rcvForecast.adapter = nextDayAdapter
    }

    private fun checkSavedCity() {
        val savedCity = cityManager.getFavorites()
        activityMainBinding.saveCb.isChecked = savedCity.contains(selectedCity)
    }
}