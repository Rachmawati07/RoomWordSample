package com.rachma.roomwordsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */
// Untuk membuat model yang bernama WordViewModel yang mendapatkan WordRepository sebagai parameter dan memperluas ViewModel
class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Menggunakan LiveData dan menyimpan apa yang dikembalikan oleh allWords memiliki beberapa manfaat :
    // - Dapat menempatkan pengamat pada data (bukan polling untuk perubahan) dan hanya memperbarui UI saat data benar-benar berubah.
    // - Repositori benar-benar terpisah dari UI melalui ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    // Untuk membuat metode insert() wrapper yang memanggil metode insert() Repositori
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}
// Untuk membuat ViewModel dan mengimplementasikan ViewModelProvider.Factory yang mendapatkan dependensi yang diperlukan sebagai parameter untuk membuat WordViewModel: WordRepository
class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}