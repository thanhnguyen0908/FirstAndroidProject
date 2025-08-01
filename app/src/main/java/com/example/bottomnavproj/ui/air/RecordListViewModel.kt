package com.example.bottomnavproj.ui.air

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavproj.data.model.Record

class RecordListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Record>>()
    val items: LiveData<List<Record>> get() = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        _items.value = listOf(
            Record("Location 1", "2 km", "2025-07-31"),
            Record("Location 2", "5 km", "2025-08-01"),
            Record("Location 3", "3 km", "2025-08-02")
        )
    }

    fun updateRecord(updated: Record) {
        _items.value = _items.value?.map {
            if (it.title == updated.title) updated else it
        }
    }

    fun addNewRecord() {
        val currentList = _items.value?.toMutableList() ?: mutableListOf()
        val newRecord = Record(
            title = "Location ${currentList.size + 1}",
            distance = "",
            date = ""
        )
        currentList.add(newRecord)
        _items.value = currentList
    }

    fun removeRecord(record: Record) {
        val currentList = _items.value?.toMutableList() ?: return
        currentList.remove(record)
        _items.value = currentList
    }
}
