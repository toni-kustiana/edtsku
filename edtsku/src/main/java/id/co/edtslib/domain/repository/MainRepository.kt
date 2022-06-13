package id.co.edtslib.domain.repository

import android.content.Context
import android.os.Environment
import android.webkit.URLUtil
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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
        val path = createFile(url, context)?.absolutePath
        if (path != null) {
            val result = saveFile(response.body(), path)
            emit(result)
        }
        else {
            emit(null)
        }
    }

    private fun saveFile(body: ResponseBody?, path: String):String? {
        if (body==null)
            return null

        var input: InputStream? = null
        try {
            input = body.byteStream()
            val fos = FileOutputStream(path)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return path
        }catch (e:Exception){
            // ignore it
        }
        finally {
            input?.close()
        }
        return null
    }

    private fun createFile(url: String, context: Context): File? {
        try {

            val dotIndex = url.lastIndexOf('.')
            val extension = if (dotIndex < 0) "" else url.substring(dotIndex)

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
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