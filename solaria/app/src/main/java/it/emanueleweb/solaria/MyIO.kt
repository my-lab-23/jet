package it.emanueleweb.solaria

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import java.io.*

object MyIO {

    object LocalIO {

        fun saveFile(inputText: String, fileName: String, mode: Int) {

            val fileContext = MyApplication.appContext
            try {
                val output = fileContext.openFileOutput(fileName, mode)
                val writer = BufferedWriter(OutputStreamWriter(output))
                writer.use {
                    it.write(inputText)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun loadFile(s: String): String {

            val fileContext = MyApplication.appContext
            val content = StringBuilder()
            try {
                val input = fileContext.openFileInput(s)
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        content.append(it)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return content.toString()
        }
    }

    //

    object NetworkIO {

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        }

        suspend fun getFirebaseUrl(path: String): String {

            val storage = Firebase.storage
            val storageRef = storage.reference

            val c = storageRef.child(path).downloadUrl
            val r = c.await()
            return r.toString()
        }

        private suspend fun downloadDataFile() {

            val url = getFirebaseUrl("data.json")
            val client = HttpClient(CIO)
            val scope = CoroutineScope(Dispatchers.IO)
            val c = scope.async { client.get(url).body<String>() }
            val s = c.await()
            LocalIO.saveFile(s, "data.json", 0)
        }

        suspend fun loadData() {

            val context = MyApplication.appContext

            if(isNetworkAvailable(context) && MyData.luoghi.isEmpty()) {

                val scope = CoroutineScope(Dispatchers.IO)
                val c = scope.async { downloadDataFile() }
                c.await()

                MyData.deserialize()
                MyData.populateAll()
            }
        }
    }
}
