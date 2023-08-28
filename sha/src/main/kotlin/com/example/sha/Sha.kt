package com.example.sha

import java.io.File
import java.security.MessageDigest

object Sha {

    fun findShaByFilename(filePath: String, filename: String): String? {

        val file = File(filePath)
        val lines = file.readLines()

        for (line in lines) {

            val parts = line.trim().split(" ")
            if (parts.size == 3 && parts[2] == filename) return parts[0]
        }

        return null
    }

    fun calculateSHA512Sum(filePath: String): String {

        val file = File(filePath)
        val digest = MessageDigest.getInstance("SHA-512")

        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("Il percorso specificato non corrisponde a un file valido.")
        }

        file.inputStream().use { inputStream ->
            val buffer = ByteArray(8192)
            var bytesRead = inputStream.read(buffer)

            while (bytesRead != -1) {
                digest.update(buffer, 0, bytesRead)
                bytesRead = inputStream.read(buffer)
            }
        }

        val hashBytes = digest.digest()
        return bytesToHexString(hashBytes)
    }

    private fun bytesToHexString(bytes: ByteArray): String {

        val hexChars = "0123456789ABCDEF".toCharArray()
        val result = StringBuilder(bytes.size * 2)

        for (byte in bytes) {
            val index = byte.toInt() and 0xFF
            result.append(hexChars[index ushr 4])
            result.append(hexChars[index and 0x0F])
        }

        return result.toString()
    }
}
