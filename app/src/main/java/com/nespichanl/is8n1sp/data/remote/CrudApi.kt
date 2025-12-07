package com.nespichanl.is8n1sp.data.remote

import com.nespichanl.is8n1sp.data.remote.dto.CrudRequest
import com.nespichanl.is8n1sp.data.remote.dto.CrudResponse
import com.nespichanl.is8n1sp.data.remote.dto.PersonDto
import retrofit2.http.Body
import retrofit2.http.POST

interface CrudApi {

    // https://apis.nespichanl.com/wiener/iot/crud
    // Asumimos siempre HTTP 200 como dijiste.
    @POST("wiener/iot/crud")
    suspend fun execute(
        @Body body: CrudRequest
    ): CrudResponse<List<PersonDto>>
}
