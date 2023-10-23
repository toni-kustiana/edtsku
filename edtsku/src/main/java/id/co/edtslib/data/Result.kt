package id.co.edtslib.data

data class Result<out T>(val status: Status, val data: T?, val code: String?,
                         val message: String?, val url: String? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        UNAUTHORIZED
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null, null)
        }

        fun <T> error(code: String?, message: String?, data: T? = null, url: String? = null): Result<T> {
            return Result(Status.ERROR, data, code, message, url)
        }

        fun <T> unauthorized(message: String?, url: String? = null): Result<T> {
            return Result(Status.UNAUTHORIZED, null, null, message, url)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null, null)
        }
    }
}