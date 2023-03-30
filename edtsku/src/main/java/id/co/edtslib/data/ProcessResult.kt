package id.co.edtslib.data

open class ProcessResult<T>(result: Result<T>, delegate: ProcessResultDelegate<T>?) {
    init {
        when(result.status) {
            Result.Status.LOADING -> delegate?.loading()
            Result.Status.ERROR -> {
                when (result.code) {
                    "ConnectionError" -> {
                        delegate?.errorConnection()
                    }
                    "SystemError" -> {
                        delegate?.errorSystem()
                    }
                    else -> {
                        delegate?.error(result.code, result.message, result.data)
                    }
                }
            }
            Result.Status.UNAUTHORIZED -> delegate?.unAuthorize(result.message)
            Result.Status.SUCCESS -> delegate?.success(result.data)
        }
    }
}