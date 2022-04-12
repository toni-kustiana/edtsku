package id.co.edts.edtsku.example.data

data class LoginResponse (
    val id_token: String,
    val loginId: String,
    val refresh_token: String?
)
