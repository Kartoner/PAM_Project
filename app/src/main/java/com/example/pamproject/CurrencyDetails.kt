package com.example.pamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.pamproject.db.AppDatabase
import com.example.pamproject.entity.Currency

class CurrencyDetails : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var item: Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_details)
        db = AppDatabase.getDatabase(applicationContext)
        initFields()
        initListeners()
    }

    private val buttonRemoveListener = View.OnClickListener { callRemove() }
    private val buttonEditListener = View.OnClickListener { callEdit() }
    private val buttonBackListener = View.OnClickListener { callBack() }

    private fun initFields() {
        val intent = intent
        val id = intent.getLongExtra("ID", 0)
        item = db?.currencyDAO()?.getById(id)

        val idTextView = findViewById<TextView>(R.id.idValueDetails)
        idTextView.text = id.toString()

        val codeTextView = findViewById<TextView>(R.id.codeValueDetails)
        codeTextView.text = item?.code.toString()

        val nameTextView = findViewById<TextView>(R.id.nameValueDetails)
        nameTextView.text = item?.name.toString()

        val valueTextView = findViewById<TextView>(R.id.valueValueDetails)
        valueTextView.text = item?.value.toString()
    }

    private fun initListeners() {
        val buttonRemove = findViewById<Button>(R.id.buttonRemove)
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val buttonBack = findViewById<Button>(R.id.buttonBackCurrencyDetails)

        buttonRemove.setOnClickListener(buttonRemoveListener)
        buttonEdit.setOnClickListener(buttonEditListener)
        buttonBack.setOnClickListener(buttonBackListener)
    }

    private fun callRemove() {
        item?.let { db?.currencyDAO()?.delete(it) }
        callBack()
    }

    private fun callEdit() {
        val intent = Intent(this, CurrencyEdit::class.java)
        intent.putExtra("CURR_ID", item?.id)
        intent.putExtra("CURR_CODE", item?.code)
        intent.putExtra("CURR_NAME", item?.name)
        intent.putExtra("CURR_VALUE", item?.value)
        startActivity(intent)
    }

    private fun callBack() {
        val intent = Intent(this, CurrencyList::class.java)
        startActivity(intent)
    }
}