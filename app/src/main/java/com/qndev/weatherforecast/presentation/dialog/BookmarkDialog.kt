package com.qndev.weatherforecast.presentation.dialog

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.qndev.weatherforecast.data.model.City

class BookmarkDialog(
    private val cityList: List<City>,
    private val onItemSelected: (City) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle("Saved cities")
            .setAdapter(
                ArrayAdapter(requireActivity(), R.layout.simple_list_item_1, cityList)
            ) { _, which ->
                val selectedCity = cityList[which]
                onItemSelected(selectedCity)
                dismiss()
            }

        return builder.create()
    }
}