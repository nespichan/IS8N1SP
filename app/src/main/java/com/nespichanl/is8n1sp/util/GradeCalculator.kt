package com.nespichanl.is8n1sp.util

import kotlin.math.roundToInt

object GradeCalculator {
    fun promedio(n1: Double, n2: Double, n3: Double): Double {
        val p = (n1 + n2 + n3) / 3.0
        return (p * 100).roundToInt() / 100.0 // 2 decimales
    }
    fun condicion(prom: Double): String = if (prom >= 11.0) "Aprobado" else "Desaprobado"
}
