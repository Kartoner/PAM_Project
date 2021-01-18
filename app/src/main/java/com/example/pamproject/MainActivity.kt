package com.example.pamproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
    }

    private val buttonCalculatorListener = View.OnClickListener { callCalculator() }
    private val buttonCurrenciesListener = View.OnClickListener { callCurrencies() }
    private val buttonAuthorListener = View.OnClickListener { callAuthor() }

    private fun initListeners() {
        val buttonCalculator = findViewById<Button>(R.id.buttonCalculator)
        val buttonCurrencies = findViewById<Button>(R.id.buttonCurrencies)
        val buttonAuthor = findViewById<Button>(R.id.buttonAuthor)

        buttonCalculator.setOnClickListener(buttonCalculatorListener)
        buttonCurrencies.setOnClickListener(buttonCurrenciesListener)
        buttonAuthor.setOnClickListener(buttonAuthorListener)
    }

    private fun callCalculator() {
        val intent = Intent(this, CurrencyCalculator::class.java)
        startActivity(intent)
    }

    private fun callCurrencies() {
        val intent = Intent(this, CurrencyList::class.java)
        startActivity(intent)
    }

    private fun callAuthor() {
        Toast.makeText(this, resources.getString(R.string.authorText), Toast.LENGTH_LONG).show()
    }
}