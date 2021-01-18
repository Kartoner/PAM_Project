package com.example.pamproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pamproject.db.AppDatabase
import com.example.pamproject.entity.Currency
import java.util.stream.Collectors


class CurrencyCalculator : AppCompatActivity() {

    private var sourceCurrency: String? = null
    private var destCurrency: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_calculator)
        initListeners()
    }

    private val buttonCalculateListener = View.OnClickListener { callCalculate() }
    private val buttonBackListener = View.OnClickListener { callBack() }

    private fun initListeners() {
        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val buttonBack = findViewById<Button>(R.id.buttonBackCalculator)

        buttonCalculate.setOnClickListener(buttonCalculateListener)
        buttonBack.setOnClickListener(buttonBackListener)

        initSpinners()
    }

    private fun initSpinners() {
        val sourceSpinner = findViewById<Spinner>(R.id.sourceCurrSpinner)
        val destSpinner = findViewById<Spinner>(R.id.destCurrSpinner)

        val dataBase = AppDatabase.getDatabase(applicationContext)

        val currencyList = dataBase.currencyDAO().getAll().stream().map(Currency::code).collect(
            Collectors.toList())

        val adapter: ArrayAdapter<String?> = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, currencyList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sourceSpinner.adapter = adapter
        destSpinner.adapter = adapter

        sourceSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    sourceCurrency = parent.getItemAtPosition(position) as String
                }
            }

        }

        destSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    destCurrency = parent.getItemAtPosition(position) as String
                }
            }

        }
    }

    private fun callCalculate() {
        val intent = Intent(this, CurrencyCalculatorResult::class.java)
        var inputValid = true

        if (findViewById<EditText>(R.id.valueInput).text.toString() == "") {
            Toast.makeText(this, resources.getString(R.string.inputValueNotNull), Toast.LENGTH_LONG).show()
            inputValid = false
        } else {
            val inputValue: Double = findViewById<EditText>(R.id.valueInput).text.toString().toDouble()
            if (inputValue < 0) {
                Toast.makeText(applicationContext, resources.getString(R.string.valueNotNegative), Toast.LENGTH_LONG).show()
                inputValid = false
            } else {
                intent.putExtra("VALUE", inputValue)
            }
        }

        if (sourceCurrency == null) {
            Toast.makeText(this, resources.getString(R.string.sourceCurrNotNull), Toast.LENGTH_LONG).show()
            inputValid = false
        } else {
            intent.putExtra("SOURCE", sourceCurrency)
        }

        if (destCurrency == null) {
            Toast.makeText(this, resources.getString(R.string.destCurrNotNull), Toast.LENGTH_LONG).show()
            inputValid = false
        } else {
            intent.putExtra("DEST", destCurrency)
        }

        if (inputValid) {
            startActivity(intent)
        }
    }

    private fun callBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}