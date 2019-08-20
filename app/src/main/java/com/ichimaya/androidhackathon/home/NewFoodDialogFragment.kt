package com.ichimaya.androidhackathon.home

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.FoodRepository
import com.ichimaya.androidhackathon.food.model.createFood
import com.ichimaya.androidhackathon.user.UserDetailsService
import java.text.SimpleDateFormat
import java.util.*

class NewFoodDialogFragment : DialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var categories: Array<String>
    private lateinit var selectedCategory: String

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        selectedCategory = categories[position]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_dialog_new_food, container)

        dialog?.setTitle("Add New Food Item")

        val myCalendar = Calendar.getInstance()

        val editText = rootView.findViewById(R.id.expiration_date) as EditText
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM/dd/yy"
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

        val spinner = rootView.findViewById<Spinner>(R.id.category_spinner)
        spinner.onItemSelectedListener = this
        categories = CategoryViewModel().getCategories()
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.dialog?.setTitle(getString(R.string.dialog_title_new_food))
    }

    private fun onClickAdd() {
        val name = this.dialog?.findViewById<TextInputEditText>(R.id.food_name)?.text.toString()
        val sdf = SimpleDateFormat("MM/dd/yy", Locale.US)
        val dateString = this.dialog?.findViewById<EditText>(R.id.expiration_date)?.text.toString()
        val expiraryDate = sdf.parse(dateString)
        val food = createFood(name, expiraryDate.time, selectedCategory)
        FoodRepository.getInstance(UserDetailsService().getUUID(context!!)).registerFood(context!!, food)
        Toast.makeText(context, getString(R.string.toast_new_food_added, name), Toast.LENGTH_SHORT).show()
        dismiss()
    }
}
