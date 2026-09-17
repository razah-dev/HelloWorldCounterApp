package com.example.counterapp.data.cloud

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.counterapp.data.CounterData
import com.example.counterapp.data.CounterDataRepository
import okhttp3.OkHttpClient
import javax.inject.Inject
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.BufferedSink
import okio.FileSystem
import okio.Okio
import okio.Path
import okio.Sink
import okio.buffer
import okio.sink
import okio.source
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

private const val LOG_TAG: String = "CloudCounterDataRepository"

class CloudCounterDataRepository @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val contentResolver: ContentResolver,
    private val cloudCounterDataApiService: CloudCounterDataApiService,
) : CounterDataRepository {
    override suspend fun saveCounterData(counterData: CounterData): CounterData {
        // Prepare input
        val cloudCounterData: CloudCounterData = CloudCounterData(
            username = counterData.username,
            timestamp8601ISOFormat = counterData.timestamp8601ISOFormat,
            counterValue = counterData.counterValue
        )
        // Make API call
        val result: CloudCounterData =
            cloudCounterDataApiService.saveManualCounterData(
                cloudCounterData = cloudCounterData)
        // Prepare and return output
        return CounterData(
            username = result.username,
            timestamp8601ISOFormat = result.timestamp8601ISOFormat,
            counterValue = result.counterValue
        )
    }

    override suspend fun fetchCounterData(): List<CounterData> {
        // Make API call
        val result: List<CloudCounterData> =
            cloudCounterDataApiService.fetchCounterDataList()
        // Prepare and return output
        return result.map {
            CounterData(
                username = it.username,
                timestamp8601ISOFormat = it.timestamp8601ISOFormat,
                counterValue = it.counterValue
            )
        }
    }

    override suspend fun fetchAllFileIdsDataList(): List<CloudFileData> {
        return cloudCounterDataApiService.fetchAllFileIdsDataList(deviceId = 120)
    }

    @Throws(IOException::class)
    private fun writeEnv(path: Path) {
        FileSystem.SYSTEM.write(path) {
            writeUtf8("name")
            writeUtf8("=")
            writeUtf8("raza hussain")
            writeUtf8("\n")
        }
    }

    // This is for Android SDK 29+
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun androidFileWriterHelper(): Uri? {
        val destinationUri: Uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val contentValues: ContentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "hello_world.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        return contentResolver.insert(destinationUri, contentValues)
    }

//    // READING AND WRITING PDF FILES
//    override suspend fun downloadFileForFileId(fileId: Int): Boolean {
//        // FETCH THE FILE CONTENTS FROM FILE STORED ON CLOUD
//        val filePreSignedUrl =
//            "https://a7df74963846fbabdabf1ed8267e71b4.r2.cloudflarestorage.com/kiosk-videos-sept14th-2026/Cover%20letter%20-%20Teaching%20at%20RISE%20Academy.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=27945a4cc6319081127b1584d9611182%2F20260916%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20260916T160253Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=2858842eb9571924c65205a9208729adc43b94bfa1d6363c05949e75581e3274"
//
//        val request = Request.Builder()
//            .url(filePreSignedUrl)
//            .build()
//
//        val byteArrayToWrite: ByteArray
//
//        okHttpClient.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) throw IOException("Unexpected code response $response")
//
//            for ((name, value) in response.headers) {
//                Log.i(LOG_TAG, "DownloadFileForFileId headers $name: $value")
//            }
//
//            // Log.i(LOG_TAG, "DownloadFileForFileId response ${response.body.string()}")
//
//            byteArrayToWrite = response.body.bytes()
//
//            Log.i(LOG_TAG, "DownloadFileForFileId ByteArray To Write ${byteArrayToWrite}")
//        }
//
//        // CREATE FILE IN DOWNLOADS/ TO WRITE THE CONTENTS TO
//        val itemUri: Uri? = androidFileWriterHelper()
//        if (itemUri == null) {
//            Log.i(LOG_TAG, "DownloadFileForFileId Item URI NULL")
//            return false
//        }
//        Log.i(LOG_TAG, "DownloadFileForFileId Item URI $itemUri")
//
//        val fileOutputStream: OutputStream? = contentResolver.openOutputStream(itemUri)
//        if (fileOutputStream == null) {
//            Log.i(LOG_TAG, "DownloadFileForFileId OutputStream NULL")
//            return false
//        }
//        Log.i(LOG_TAG, "DownloadFileForFileId OutputStream $fileOutputStream")
//        val bufferedSink: BufferedSink = fileOutputStream.sink().buffer()
//        bufferedSink.write(byteArrayToWrite)
//        bufferedSink.close()
//
//        return true
//    }

    // This is for Android SDK 29+
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun androidFileWriterHelperForVideo(): Uri? {
        val destinationUri: Uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val contentValues: ContentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "my-video.mp4")
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
        return contentResolver.insert(destinationUri, contentValues)
    }

    // This is for Android SDK 30+
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun androidFileWriteCompleteInContentResolver(fileUri: Uri): Int {
        /// This is for Android SDK 29+
        val contentValues: ContentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.IS_PENDING, 0)
        }
        return contentResolver.update(fileUri, contentValues, null, null)
    }

    // VIDEO FILES
    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun downloadFileForFileId(fileId: Int): Boolean {
        // FETCH THE FILE CONTENTS FROM FILE STORED ON CLOUD
        val filePreSignedUrl =
            //"https://a7df74963846fbabdabf1ed8267e71b4.r2.cloudflarestorage.com/kiosk-videos-sept14th-2026/Post-data-repository-changes-demo.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=27945a4cc6319081127b1584d9611182%2F20260917%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20260917T135622Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=b2b59649af24c7e859749b03972b425d67acc6e16c1797bb74ab537babd280d6"
            "https://a7df74963846fbabdabf1ed8267e71b4.r2.cloudflarestorage.com/kiosk-videos-sept14th-2026/Remember%20Saveable%20Short%20demo.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=27945a4cc6319081127b1584d9611182%2F20260917%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20260917T155150Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=8b77aae3142169055c225f570e1640d82fbf1cf35b742ed41ffb808f04cf5d97"

        val request = Request.Builder()
            .url(filePreSignedUrl)
            .build()

        // Get the URI of the (local) file that needs to be written
        val itemUri: Uri = androidFileWriterHelperForVideo()
            ?: throw IOException("Item URI is NULL")
        Log.i(LOG_TAG, "DownloadFileForFileId Item URI is $itemUri")

        // Make the network call to fetch the contents of the remote file
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected network call response $response")

            for ((name, value) in response.headers) {
                Log.i(LOG_TAG, "DownloadFileForFileId headers $name: $value")
            }

            // Write the contents of the response body to the output file
            val writeFileOutputStream: OutputStream = contentResolver.openOutputStream(itemUri)
                ?: throw IOException("OutputStream for Writing File is NULL")
            writeFileOutputStream.use { outputStream ->
                outputStream.sink().buffer().use { outputStreamFileSink ->
                    outputStreamFileSink.writeAll(response.body.source())
                }
            }
        }
        val fileWriteMarkedCompleteInContentResolver: Int =
            androidFileWriteCompleteInContentResolver(itemUri)
        if (fileWriteMarkedCompleteInContentResolver == 0) {
            throw IOException("File Not Marked as Write Complete in Content Resolver")
        }

        return true
    }
}
