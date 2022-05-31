package id.co.edtslib.util

open class ErrorMessage {
    open fun http503() = "Sedang ada gangguan sistem, coba lagi lain waktu."
    open fun connection() = "Koneksi sedang bermasalah, cek kembali koneksimu."
    open fun system(message: String?) = "Terjadi kesalahan. ($message)"
}