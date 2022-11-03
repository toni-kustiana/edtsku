package id.co.edtslib.data.source

import android.content.Context
import id.co.edtslib.domain.model.DownloadResult
import id.co.edtslib.domain.repository.IMainRepository
import id.co.edtslib.data.source.remote.MainRemoteDataSource
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class MainRepository(
    private val remoteDataSource: MainRemoteDataSource,
    private val context: Context
):
    IMainRepository {
    override fun download(url: String) = flow {
        val response = remoteDataSource.download(url)
        val result = saveFile(response.body())
        emit(result)
    }

    private fun saveFile(body: ResponseBody?): DownloadResult {
        if (body == null) {
            return DownloadResult(null, null)
        }

        val contentType = body.contentType()
        val extension = if (contentType?.subtype == null) "" else contentType.subtype

        val path = createFile(extension, context)?.absolutePath
        if (path != null) {

            var input: InputStream? = null
            try {
                input = body.byteStream()
                val fos = FileOutputStream(path)
                fos.use { output ->
                    val buffer = ByteArray(2 * 1024) // or other buffer size
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                }
                return DownloadResult(path, contentType?.toString())
            } catch (e: Exception) {
                // ignore it
            } finally {
                input?.close()
            }
        }
        return DownloadResult(null, null)
    }

    private fun createFile(extension: String, context: Context): File? {
        try {

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir: File? = context.cacheDir
            if (storageDir?.exists() != true) {
                storageDir?.mkdir()
            }

            return File.createTempFile(
                "edtsku_${timeStamp}",
                extension,
                storageDir
            )
        }
        catch (e: Exception) {
            return null
        }
    }
}