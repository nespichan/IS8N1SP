package com.nespichanl.is8n1sp.model.usercase

import com.nespichanl.is8n1sp.model.s10.data.local.entity.Elector
import com.nespichanl.is8n1sp.model.s10.data.repo.ElectorRepository

class AltaElector(private val repo: ElectorRepository) {
    suspend operator fun invoke(e: Elector) = repo.alta(e)
}