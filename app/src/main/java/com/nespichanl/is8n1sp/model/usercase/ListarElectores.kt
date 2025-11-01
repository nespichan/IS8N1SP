package com.nespichanl.is8n1sp.model.usercase

import com.nespichanl.is8n1sp.model.s10.data.repo.ElectorRepository

class ListarElectores(private val repo: ElectorRepository) {
    fun stream() = repo.stream()
}
