package id.co.edtslib.data

interface ProcessResultDelegate<T> {
    fun loading()
    fun error(code: String?, message: String?, data: T? = null, url: String? = null)
    fun unAuthorize(message: String?, url: String? = null)
    fun success(data: T?)
    fun errorConnection(url: String? = null)
    fun errorSystem(url: String? = null)
}