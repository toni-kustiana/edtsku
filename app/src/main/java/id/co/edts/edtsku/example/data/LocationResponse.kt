package id.co.edts.edtsku.example.data

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String? = null
)
