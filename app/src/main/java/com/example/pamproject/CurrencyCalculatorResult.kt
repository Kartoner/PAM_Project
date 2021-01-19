package com.example.pamproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pamproject.db.AppDatabase
import java.text.DecimalFormat


class CurrencyCalculatorResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_calculator_result)
        initListeners()
        setResults()
    }

    private val buttonBackListener = View.OnClickListener { callBack() }

    private fun initListeners() {
        val buttonBack = findViewById<Button>(R.id.buttonBackCalculatorResult)

        buttonBack.setOnClickListener(buttonBackListener)
    }

    private fun callBack() {
        val intent = Intent(this, CurrencyCalculator::class.java)
        startActivity(intent)
    }

    private fun setResults() {
        val sourceResultText: TextView = findViewById(R.id.sourceResultText)
        val destResultText: TextView = findViewById(R.id.destResultText)
        val intent = intent
        val currencyFormatter = DecimalFormat("###,###,##0.00")

        val sourceValue: Double? = intent.getDoubleExtra("VALUE", 0.0)
        val source: String? = intent.getStringExtra("SOURCE")
        val dest: String? = intent.getStringExtra("DEST")

        val calcResult = calculateResult(sourceValue, source, dest)

        sourceResultText.text = currencyFormatter.format(sourceValue) + " " + source + " ="
        destResultText.text = currencyFormatter.format(calcResult) + " " + dest
    }

    private fun calculateResult(sourceValue: Double?, source: String?, dest: String?): Double? {
        val dataBase = AppDatabase.getDatabase(applicationContext)
        val sourceCurr = source?.let { dataBase.currencyDAO().getByCode(it) }
        val destCurr = dest?.let { dataBase.currencyDAO().getByCode(it) }

        if (sourceValue != null) {
            return ((sourceValue * (sourceCurr?.value ?: 0.0)) / (destCurr?.value ?: 0.0)) * 1.0
        } else {
            return null
        }
    }
}