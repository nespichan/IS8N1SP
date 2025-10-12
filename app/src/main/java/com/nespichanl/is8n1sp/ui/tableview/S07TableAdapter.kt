package com.nespichanl.is8n1sp.ui.tableview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nespichanl.is8n1sp.R

class S07TableAdapter(
    private val data: List<S07RowModel>,
    private val onRowClick: (S07RowModel) -> Unit = {}
) : RecyclerView.Adapter<S07TableAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val col1: TextView = v.findViewById(R.id.tvCol1)
        val col2: TextView = v.findViewById(R.id.tvCol2)
        val col3: TextView = v.findViewById(R.id.tvCol3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_table_row, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val row = data[position]
        holder.col1.text = row.nombre
        holder.col2.text = row.categoria
        holder.col3.text = String.format("S/ %.2f", row.precio)
        holder.itemView.setOnClickListener { onRowClick(row) }
    }

    override fun getItemCount(): Int = data.size
}
