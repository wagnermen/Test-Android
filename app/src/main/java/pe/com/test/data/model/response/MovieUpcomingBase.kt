package pe.com.test.data.model.response

import com.google.gson.annotations.SerializedName

class MovieUpcomingBase(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<MovieUpcoming>
)