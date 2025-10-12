package com.nespichanl.is8n1sp.ui.tableview

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nespichanl.is8n1sp.R

class S07TableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_IS8N1SP) // por si acaso (mismo tema que usas)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s07_table)

        val rv = findViewById<RecyclerView>(R.id.rvTabla)
        val btnEnd = findViewById<Button>(R.id.btnFinalizarS07)

        // Datos mock (puedes cambiar por lista dinámica)
        val items = listOf(
            S07RowModel("Cámara Domo", "Seguridad", 149.90),
            S07RowModel("Alarma Hogar", "Robos", 199.50),
            S07RowModel("Sensor Humo", "Incendio", 89.00),
            S07RowModel("Cerca Eléctrica", "Perímetro", 399.99),
            S07RowModel("Kit Trío", "Paquete", 289.70),
        )

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = S07TableAdapter(items) { row ->
            Toast.makeText(this, "${row.nombre} - ${row.categoria}", Toast.LENGTH_SHORT).show()
        }
        rv.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        btnEnd.setOnClickListener { finish() }
    }
}
