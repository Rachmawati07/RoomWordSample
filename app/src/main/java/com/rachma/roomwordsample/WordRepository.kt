package com.rachma.roomwordsample

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
// Untuk membuat kelas yang bernama WordRepository
class WordRepository(private val wordDao: WordDao) {

    // Room mengeksekusi semua query pada thread terpisah.
    // Flow yang diamati akan memberi tahu pengamat ketika data telah berubah.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // Secara default Room menjalankan queri penangguhan dari utas utama, oleh karena itu, kita tidak perlu
    // mengimplementasikan hal lain untuk memastikan kita tidak melakukan pekerjaan database yang berjalan lama
    // keluar dari utas utama.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}