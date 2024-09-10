package anhddph36155.fpoly.searchaddress

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HereApi {
    @GET("geocode")
    suspend fun searchAddress(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = "ERhrRkdwBIAAbOtZWmCVHDfH-_PGxvBlGXvNFXfb6tY"
    ): Response<SearchResponse>
}