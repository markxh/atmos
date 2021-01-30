package za.co.dotmark.atmos.model

data class City (
    val id: Long,
    val name: String,
    val coord: Coordinates,
    val country: String
)