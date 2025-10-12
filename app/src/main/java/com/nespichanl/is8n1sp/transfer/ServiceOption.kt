package com.nespichanl.is8n1sp.transfer

enum class ServiceOption(
    val title: String,
    val serviceCost: Double,
    val installCost: Double,
    val discountPct: Double // en [0..1]
) {
    DUO("Dúo - Cámaras de Seguridad y Alarmas contra Robos", 119.30, 37.00, 0.07),
    TRIO("Trío - Cámaras, Alarmas contra Robos e Incendio", 169.80, 65.00, 0.12),
    CAMARAS("Solo Cámaras de Seguridad", 59.50, 21.00, 0.04),
    ROBOS("Solo Alarmas contra Robos", 49.20, 17.00, 0.04),
    INCENDIO("Solo Alarmas contra Incendio", 42.30, 15.00, 0.04),
    CERCOS("Solo Cercos Eléctricos", 125.70, 35.00, 0.05);
}