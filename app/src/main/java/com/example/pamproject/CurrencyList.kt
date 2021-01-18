package com.example.pamproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pamproject.db.AppDatabase
import com.example.pamproject.entity.Currency
import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.listeners.TableDataClickListener
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter

class CurrencyList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)
        initTable()
        initListeners()
    }

    private val buttonAddListener = View.OnClickListener { callAdd() }
    private val buttonBackListener = View.OnClickListener { callBack() }

    private fun initListeners() {
        val buttonAdd = findViewById<Button>(R.id.buttonAddCurrencyList)
        val buttonBack = findViewById<Button>(R.id.buttonBackCurrencyList)

        buttonAdd.setOnClickListener(buttonAddListener)
        buttonBack.setOnClickListener(buttonBackListener)
    }

    private fun callAdd() {
        val intent = Intent(applicationContext, CurrencyEdit::class.java)
        startActivity(intent)
    }

    private fun callBack() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initTable() {
        val table: TableView<Array<String>> = findViewById(R.id.currenciesTable)
        table.columnCount = 4
        table.setHeaderBackgroundColor(resources.getColor(R.color.colorPrimary))

        table.headerAdapter = SimpleTableHeaderAdapter(applicationContext, resources.getString(R.string.id),
            resources.getString(R.string.code),
            resources.getString(R.string.name),
            resources.getString(R.string.value))

        val dataBase = AppDatabase.getDatabase(applicationContext)
        val currencies = dataBase.currencyDAO().getAll()
        val currenciesArray = Array(currencies.size) { _ -> Array(4) { _ -> ""} }

        for (i in currenciesArray.indices) {
            val currency: Currency = currencies[i]
            currenciesArray[i][0] = currency.id.toString()
            currenciesArray[i][1] = currency.code.toString()
            currenciesArray[i][2] = currency.name.toString()
            currenciesArray[i][3] = currency.value.toString()
        }

        table.dataAdapter = SimpleTableDataAdapter(applicationContext, currenciesArray)

        table.addDataClickListener { _, clickedData ->
            val intent = Intent(applicationContext, CurrencyDetails::class.java)
            intent.putExtra("ID", clickedData?.get(0)?.toLong())
            startActivity(intent)
        }
    }
}