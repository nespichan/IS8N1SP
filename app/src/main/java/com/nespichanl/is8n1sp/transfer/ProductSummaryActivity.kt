package com.nespichanl.is8n1sp.transfer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nespichanl.is8n1sp.databinding.ActivityProductSummaryBinding

class ProductSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductSummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getStringExtra("cliente").orEmpty()
        val dni = intent.getStringExtra("dni").orEmpty()
        val servicio = intent.getStringExtra("servicio").orEmpty()
        val costoServicio = intent.getDoubleExtra("costoServicio", 0.0)
        val costoInstalacion = intent.getDoubleExtra("costoInstalacion", 0.0)
        val descuento = intent.getDoubleExtra("descuento", 0.0)
        val total = intent.getDoubleExtra("totalPagar", 0.0)

        binding.tvCliente.text = cliente
        binding.tvDni.text = dni
        binding.tvServicio.text = servicio
        binding.tvCostoServicio.text = costoServicio.toCurrency()
        binding.tvCostoInstalacion.text = costoInstalacion.toCurrency()
        binding.tvDescuento.text = descuento.toCurrency()
        binding.tvTotalPagar.text = total.toCurrency()

        binding.btnVolver.setOnClickListener { finish() }
    }

    private fun Double.toCurrency() = "S/ " + String.format("%.2f", this)
}
