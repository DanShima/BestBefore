package com.ichimaya.androidhackathon.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ichimaya.androidhackathon.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*




/**
 * Created by Marika Driman on 2018-10-20.
 */

class NewFoodDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_dialog_new_food, container)

        dialog.setTitle("Add New Food Item")

        val myCalendar = Calendar.getInstance()

        val editText = rootView.findViewById(R.id.expiration_date) as EditText
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM/dd/yy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editText.setText(sdf.format(myCalendar.time))
        }

        editText.setOnClickListener() {
            DatePickerDialog(context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        rootView.findViewById<Button>(R.id.add_btn).setOnClickListener() {
            onClickAdd()
        }

        rootView.findViewById<Button>(R.id.cancel_btn).setOnClickListener() {
            this.dismiss()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.dialog.setTitle("Add New Food Item")
    }

    private fun onClickAdd() {
        Timber.d("ADD THIS ITEM")
    }
}
