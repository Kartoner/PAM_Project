package com.example.pamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pamproject.db.AppDatabase
import com.example.pamproject.entity.Currency

class CurrencyEdit : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var item: Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_edit)
        db = AppDatabase.getDatabase(applicationContext)
        initFields()
        initListeners()
    }

    private val buttonAcceptListener = View.OnClickListener { callAccept() }
    private val buttonCancelListener = View.OnClickListener { callCancel() }

    private fun initFields() {
        val intent = intent
        val id = intent.getLongExtra("CURR_ID", Long.MAX_VALUE)
        val code = intent.getStringExtra("CURR_CODE").toString()
        val name = intent.getStringExtra("CURR_NAME").toString()
        val value = intent.getDoubleExtra("CURR_VALUE", 0.0)
        val currency = Currency(code, name, value)
        if (id == Long.MAX_VALUE) {
            currency.id = null
        } else {
            currency.id = id
        }
        item = currency

        val codeEditText = findViewById<EditText>(R.id.codeValueEdit)
        codeEditText.setText(code)

        val nameEditText = findViewById<EditText>(R.id.nameValueEdit)
        nameEditText.setText(name)

        val valueEditText = findViewById<EditText>(R.id.valueValueEdit)
        valueEditText.setText(value.toString())
    }

    private fun initListeners() {
        val buttonAccept = findViewById<Button>(R.id.buttonAdd)
        val buttonCancel = findViewById<Button>(R.id.buttonCancelAdd)

        buttonAccept.setOnClickListener(buttonAcceptListener)
        buttonCancel.setOnClickListener(buttonCancelListener)
    }

    private fun callAccept() {
        var currency = item
        if (currency != null) {
            currency.code = findViewById<EditText>(R.id.codeValueEdit).text.toString()
            currency.name = findViewById<EditText>(R.id.nameValueEdit).text.toString()
            if (findViewById<EditText>(R.id.valueValueEdit).text.toString() == "") {
                currency.value = null
            } else {
                currency.value = findViewById<EditText>(R.id.valueValueEdit).text.toString().toDouble()
            }
        }
        var isValid = true
        if (currency != null) {
            if (currency.id != null) {
                isValid = editItem(currency)
            } else {
                isValid = addItem(currency)
            }
        }

        if (isValid) {
            val intent = Intent(applicationContext, CurrencyList::class.java)
            startActivity(intent)
        }
    }

    private fun editItem(currency: Currency): Boolean {
        val isValid = validate(currency)

        if (isValid) {
            db?.currencyDAO()?.update(currency)
        }

        return isValid
    }

    private fun addItem(currency: Currency): Boolean {
        var isValid = validate(currency)

        if (isValid) {
            val currencyInDB = currency.code?.let { db?.currencyDAO()?.getByCode(it) }

            if (currencyInDB != null) {
                Toast.makeText(applicationContext, resources.getString(R.string.codeExists), Toast.LENGTH_LONG).show()
                isValid = false
            } else {
                db?.currencyDAO()?.insertAll(currency)
            }
        }

        return isValid
    }

    private fun validate(currency: Currency): Boolean {
        var isValid = true

        if (currency.code == "") {
            Toast.makeText(applicationContext, resources.getString(R.string.codeNotNull), Toast.LENGTH_LONG).show()
            isValid = false
        }
        if (currency.name == "") {
            Toast.makeText(applicationContext, resources.getString(R.string.nameNotNull), Toast.LENGTH_LONG).show()
            isValid = false
        }
        if (currency.value == null) {
            Toast.makeText(applicationContext, resources.getString(R.string.valueNotNull), Toast.LENGTH_LONG).show()
            isValid = false
        }

        return isValid
    }

    private fun callCancel() {
        val intent = Intent(applicationContext, CurrencyList::class.java)
        startActivity(intent)
    }
}