package com.nespichanl.is8n1sp.model.usercase

import com.nespichanl.is8n1sp.model.s10.data.repo.ElectorRepository

class BajaElector(private val repo: ElectorRepository) {
    suspend operator fun invoke(dni: String) = repo.baja(dni)
}