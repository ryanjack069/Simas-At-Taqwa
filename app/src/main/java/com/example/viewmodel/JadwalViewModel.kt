package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Jadwal
import com.example.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class JadwalViewModel : ViewModel() {

    private val _jadwalList = MutableStateFlow<List<Jadwal>>(emptyList())
    val jadwalList: StateFlow<List<Jadwal>> = _jadwalList.asStateFlow()

    private val _nextJadwal = MutableStateFlow<Jadwal?>(null)
    val nextJadwal: StateFlow<Jadwal?> = _nextJadwal.asStateFlow()

    private val _uniqueNames = MutableStateFlow<List<String>>(emptyList())
    val uniqueNames: StateFlow<List<String>> = _uniqueNames.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _updateStatus = MutableStateFlow<String?>(null)
    val updateStatus: StateFlow<String?> = _updateStatus.asStateFlow()

    private val _allJadwal = mutableListOf<Jadwal>()

    private val scriptUrl = "https://script.google.com/macros/s/AKfycbx_wWaTVldC-9ClZsRIZgWFD1wHd-VpkWvsTHvtoEif-fVA5tad2LePcXvTl1a3_SlS/exec"

    init {
        fetchJadwal()
    }

    fun fetchJadwal() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.api.getJadwal(scriptUrl)
                _allJadwal.clear()
                _allJadwal.addAll(response)
                _jadwalList.value = response
                
                calculateNextJadwal()
                extractUniqueNames()
            } catch (e: Exception) {
                _updateStatus.value = "Gagal mengambil data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun calculateNextJadwal() {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        val todayMillis = today.timeInMillis

        val monthMap = mapOf(
            "JANUARI" to 0, "FEBRUARI" to 1, "MARET" to 2, "APRIL" to 3,
            "MEI" to 4, "JUNI" to 5, "JULI" to 6, "AGUSTUS" to 7,
            "SEPTEMBER" to 8, "OKTOBER" to 9, "NOVEMBER" to 10, "DESEMBER" to 11
        )

        val next = _allJadwal.firstOrNull { jadwal ->
            try {
                val parts = jadwal.tanggal.split(" ")
                if (parts.size >= 3) {
                    val day = parts[0].toInt()
                    val monthStr = parts[1].uppercase(Locale("id", "ID"))
                    val year = parts[2].toInt()
                    val month = monthMap[monthStr] ?: 0

                    val cal = Calendar.getInstance()
                    cal.set(year, month, day, 0, 0, 0)
                    cal.set(Calendar.MILLISECOND, 0)

                    cal.timeInMillis >= todayMillis
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        } ?: _allJadwal.lastOrNull()
        
        _nextJadwal.value = next
    }

    private fun extractUniqueNames() {
        val names = _allJadwal.flatMap {
            listOf(it.muadzin, it.pembawaAcara, it.bilal, it.khotib, it.imam)
        }.map { it.trim() }
         .filter { it.isNotBlank() && it.uppercase() != "NIHIL" && it != "-" }
         .distinct()
         .sorted()
        _uniqueNames.value = names
    }

    fun clearUpdateStatus() {
        _updateStatus.value = null
    }

    fun updateJadwal(no: Int, muadzin: String, mc: String, bilal: String, khotib: String, imam: String, pin: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = mapOf(
                    "action" to "update",
                    "no" to no.toString(),
                    "muadzin" to muadzin,
                    "mc" to mc,
                    "bilal" to bilal,
                    "khotib" to khotib,
                    "imam" to imam,
                    "pin" to pin
                )
                val response = RetrofitClient.api.updateJadwal(scriptUrl, request)
                if (response.status == "success") {
                    _updateStatus.value = "Berhasil: ${response.message}"
                    fetchJadwal() // Refresh data
                } else {
                    _updateStatus.value = "Gagal: ${response.message}"
                }
            } catch (e: Exception) {
                _updateStatus.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
