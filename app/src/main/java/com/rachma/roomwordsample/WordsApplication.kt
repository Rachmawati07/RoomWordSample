package com.rachma.roomwordsample

import android.app.Application
import com.rachma.roomwordssample.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// Membuat class baru bernama WordsApplication yang memperluas Application
// Membuat instance database.
// Membuat instance repositori berdasarkan DAO database
class WordsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Menggunakan oleh lazy sehingga database dan repositori hanya dibuat saat dibutuhkan daripada saat aplikasi dimulai
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}
