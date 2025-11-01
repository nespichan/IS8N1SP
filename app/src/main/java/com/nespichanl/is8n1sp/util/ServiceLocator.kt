package com.nespichanl.is8n1sp.util

import android.content.Context
import com.nespichanl.is8n1sp.model.s10.data.local.S10Database
import com.nespichanl.is8n1sp.model.s10.data.repo.ElectorRepository
import com.nespichanl.is8n1sp.model.s10.data.repo.ElectorRepositoryImpl
import com.nespichanl.is8n1sp.model.usercase.*

object ServiceLocatorS10 {
    @Volatile private var repo: ElectorRepository? = null

    fun provideUseCases(context: Context): S10UseCases {
        val database = S10Database.build(context.applicationContext)
        val r = repo ?: ElectorRepositoryImpl(database.electorDao()).also { repo = it }
        return S10UseCases(
            alta = AltaElector(r),
            baja = BajaElector(r),
            modificar = ModificarElector(r),
            consultar = ConsultarPorDni(r),
            listar = ListarElectores(r)
        )
    }
}

data class S10UseCases(
    val alta: AltaElector,
    val baja: BajaElector,
    val modificar: ModificarElector,
    val consultar: ConsultarPorDni,
    val listar: ListarElectores
)
