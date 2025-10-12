package com.nespichanl.is8n1sp.transfer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nespichanl.is8n1sp.R
import com.nespichanl.is8n1sp.databinding.ActivityProductEntryBinding
import kotlin.math.round

class ProductEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductEntryBinding
    private var selected: ServiceOption = ServiceOption.DUO

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_IS8N1SP)
        super.onCreate(savedInstanceState)
        binding = ActivityProductEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // default
        binding.rbtnDuo.isChecked = true
        selected = ServiceOption.DUO
        updatePreview()

        // Listener centralizado en el RadioGroup
        binding.rgServicios.setOnCheckedChangeListener { _, checkedId ->
            selected = when (checkedId) {
                R.id.rbtnDuo       -> ServiceOption.DUO
                R.id.rbtnTrio      -> ServiceOption.TRIO
                R.id.rbtnCamaras   -> ServiceOption.CAMARAS
                R.id.rbtnARobos    -> ServiceOption.ROBOS
                R.id.rbtnAIncendio -> ServiceOption.INCENDIO
                R.id.rbtnCercos    -> ServiceOption.CERCOS
                else -> selected
            }
            updatePreview()
        }

        // Botón calcular
        binding.btnCalcular.setOnClickListener { updatePreview() }
        binding.btnFinalizar.setOnClickListener {
            finish()
        }
        // Botón imprimir
        binding.btnImprimir.setOnClickListener {
            val nombre = binding.edtCliente.text.toString().trim()
            val dni = binding.edtDni.text.toString().trim()

            if (nombre.isEmpty()) {
                toast("Ingresa el nombre del cliente"); return@setOnClickListener
            }
            if (dni.length != 8) {
                toast("DNI debe tener 8 dígitos"); return@setOnClickListener
            }

            val (subtotal, desc, total) = compute(selected)
            val i = Intent(this, ProductSummaryActivity::class.java).apply {
                putExtra("cliente", nombre)
                putExtra("dni", dni)
                putExtra("servicio", selected.title)
                putExtra("costoServicio", subtotal)
                putExtra("costoInstalacion", selected.installCost)
                putExtra("descuento", desc)
                putExtra("totalPagar", total)
            }
            startActivity(i)
        }
    }

    private fun updatePreview() {
        val (subtotal, desc, total) = compute(selected)
        binding.tvCostoServicio.text = subtotal.toCurrency()
        binding.tvCostoInstalacion.text = selected.installCost.toCurrency()
        binding.tvDescuento.text = desc.toCurrency()
        binding.tvTotalPagar.text = total.toCurrency()
    }

    // Descuento aplica sobre el costo del servicio
    private fun compute(opt: ServiceOption): Triple<Double, Double, Double> {
        val subtotal = opt.serviceCost
        val descuento = round2(subtotal * opt.discountPct)
        val total = round2(subtotal + opt.installCost - descuento)
        return Triple(subtotal, descuento, total)
    }

    private fun Double.toCurrency() = "S/ " + String.format("%.2f", this)
    private fun round2(x: Double) = round(x * 100) / 100.0
    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
