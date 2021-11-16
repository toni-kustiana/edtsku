package id.co.edts.edtsku.example.di

data class LoginRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean
)