package za.co.dotmark.atmos.model

import com.google.gson.annotations.SerializedName

data class Rain (
    @SerializedName("1h")
    val the1H: Double
)