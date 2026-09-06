@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.model.Jadwal
import com.example.viewmodel.JadwalViewModel

val GreenPrimary = Color(0xFF1B5E20)
val GreenSecondary = Color(0xFF2E7D32)
val GoldAccent = Color(0xFFFFB300)
val BackgroundCream = Color(0xFFF1F8E9)

enum class AppScreen { Home, Admin }

@Composable
fun HomeScreen(viewModel: JadwalViewModel) {
    val jadwalList by viewModel.jadwalList.collectAsState()
    val nextJadwal by viewModel.nextJadwal.collectAsState()
    val uniqueNames by viewModel.uniqueNames.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val updateStatus by viewModel.updateStatus.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var currentScreen by remember { mutableStateOf(AppScreen.Home) }

    LaunchedEffect(updateStatus) {
        updateStatus?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearUpdateStatus()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccountBalance,
                            contentDescription = "Logo",
                            modifier = Modifier.size(36.dp),
                            tint = GreenPrimary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "SIMAS ATTAQWA",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = GreenPrimary
                            )
                            Text(
                                text = "Sistem Informasi Masjid Attaqwa | v1.0",
                                style = MaterialTheme.typography.labelSmall,
                                color = GreenSecondary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", color = if (currentScreen == AppScreen.Home) GreenPrimary else Color.Gray) },
                    selected = currentScreen == AppScreen.Home,
                    onClick = { currentScreen = AppScreen.Home },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GreenPrimary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = BackgroundCream
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Admin") },
                    label = { Text("Admin", color = if (currentScreen == AppScreen.Admin) GreenPrimary else Color.Gray) },
                    selected = currentScreen == AppScreen.Admin,
                    onClick = { currentScreen = AppScreen.Admin },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GreenPrimary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = BackgroundCream
                    )
                )
            }
        },
        containerColor = BackgroundCream
    ) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            
            if (currentScreen == AppScreen.Home) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // HERO CARD
                    if (isLoading && nextJadwal == null) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = GreenPrimary)
                    } else if (nextJadwal != null) {
                        HeroNextJadwal(nextJadwal!!)
                    }

                    // SEMUA JADWAL DI BAWAH DASHBOARD
                    SearchAndListWidget(jadwalList)
                }
            } else {
                // HALAMAN PENGATURAN ADMIN
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    AdminPage(viewModel, jadwalList, uniqueNames)
                }
            }
        }
    }
}

@Composable
fun HeroNextJadwal(jadwal: Jadwal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GreenPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Event, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "JADWAL PETUGAS SHOLAT JUM'AT",
                    style = MaterialTheme.typography.labelLarge,
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            val pasaran = if (jadwal.jumat.isNotBlank()) " ${jadwal.jumat}" else ""
            Text(
                text = "Jumat$pasaran, ${jadwal.tanggal}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                HeroRoleRow("Khotib", jadwal.khotib)
                HeroRoleRow("Imam", jadwal.imam)
                HeroRoleRow("Bilal", jadwal.bilal)
                HeroRoleRow("Muadzin", jadwal.muadzin)
                HeroRoleRow("MC", jadwal.pembawaAcara)
            }
        }
    }
}

@Composable
fun HeroRoleRow(role: String, name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = role, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
        Text(text = name, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AdminPage(viewModel: JadwalViewModel, jadwalList: List<Jadwal>, uniqueNames: List<String>) {
    var isUnlocked by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Pengaturan Admin", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = GreenPrimary)
            }
            
            Spacer(modifier = Modifier.height(20.dp))

            if (!isUnlocked) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("PIN Admin", color = Color.DarkGray) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (password == "1234") { 
                            isUnlocked = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    Text("Buka Akses", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }
            } else {
                AdminEditForm(viewModel, jadwalList, uniqueNames)
            }
        }
    }
}

@Composable
fun AdminEditForm(viewModel: JadwalViewModel, jadwalList: List<Jadwal>, uniqueNames: List<String>) {
    var selectedJadwal by remember { mutableStateOf<Jadwal?>(null) }
    
    var muadzin by remember { mutableStateOf("") }
    var mc by remember { mutableStateOf("") }
    var bilal by remember { mutableStateOf("") }
    var khotib by remember { mutableStateOf("") }
    var imam by remember { mutableStateOf("") }

    var dateDropdownExpanded by remember { mutableStateOf(false) }
    
    Box {
        OutlinedTextField(
            value = selectedJadwal?.tanggal ?: "Pilih Tanggal Jadwal",
            onValueChange = {},
            readOnly = true,
            label = { Text("Tanggal Jadwal", color = Color.DarkGray) },
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.DarkGray
            ),
            enabled = false 
        )
        Box(modifier = Modifier
            .matchParentSize()
            .clickable { dateDropdownExpanded = true })
        
        DropdownMenu(
            expanded = dateDropdownExpanded,
            onDismissRequest = { dateDropdownExpanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(max = 300.dp)
                .background(Color.White)
        ) {
            jadwalList.forEach { jadwal ->
                DropdownMenuItem(
                    text = { Text(jadwal.tanggal, color = Color.Black) },
                    onClick = {
                        selectedJadwal = jadwal
                        muadzin = jadwal.muadzin
                        mc = jadwal.pembawaAcara
                        bilal = jadwal.bilal
                        khotib = jadwal.khotib
                        imam = jadwal.imam
                        dateDropdownExpanded = false
                    }
                )
            }
        }
    }

    if (selectedJadwal != null) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("Pilih / Ketik Nama Pengganti:", style = MaterialTheme.typography.titleMedium, color = GreenSecondary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        
        SearchableDropdown("Khotib", uniqueNames, khotib) { khotib = it }
        Spacer(modifier = Modifier.height(8.dp))
        SearchableDropdown("Imam", uniqueNames, imam) { imam = it }
        Spacer(modifier = Modifier.height(8.dp))
        SearchableDropdown("Bilal", uniqueNames, bilal) { bilal = it }
        Spacer(modifier = Modifier.height(8.dp))
        SearchableDropdown("Muadzin", uniqueNames, muadzin) { muadzin = it }
        Spacer(modifier = Modifier.height(8.dp))
        SearchableDropdown("Pembawa Acara (MC)", uniqueNames, mc) { mc = it }
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.updateJadwal(
                    no = selectedJadwal!!.no,
                    muadzin = muadzin,
                    mc = mc,
                    bilal = bilal,
                    khotib = khotib,
                    imam = imam,
                    pin = "1234" 
                )
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text("Simpan Perubahan", fontSize = MaterialTheme.typography.titleMedium.fontSize)
        }
    }
}

@Composable
fun SearchableDropdown(label: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = onSelect,
            label = { Text(label, color = Color.DarkGray) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        
        val filteredOptions = options.filter { it.contains(selected, ignoreCase = true) }
        
        if (filteredOptions.isNotEmpty() && expanded) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                filteredOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, color = Color.Black) },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchAndListWidget(jadwalList: List<Jadwal>) {
    var searchQuery by remember { mutableStateOf("") }
    
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()) {
            Text("Daftar Seluruh Jadwal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GreenPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Cari tanggal atau nama...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val filteredList = jadwalList.filter {
                it.tanggal.contains(searchQuery, ignoreCase = true) ||
                it.khotib.contains(searchQuery, ignoreCase = true) ||
                it.imam.contains(searchQuery, ignoreCase = true) ||
                it.muadzin.contains(searchQuery, ignoreCase = true) ||
                it.pembawaAcara.contains(searchQuery, ignoreCase = true)
            }
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList) { jadwal ->
                    JadwalCard(jadwal = jadwal)
                }
            }
        }
    }
}

@Composable
fun JadwalCard(jadwal: Jadwal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCream),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val pasaran = if (jadwal.jumat.isNotBlank()) " ${jadwal.jumat}" else ""
            Text(
                text = "Jumat$pasaran, ${jadwal.tanggal}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = GreenPrimary.copy(alpha = 0.2f))
            JadwalRow("Khotib", jadwal.khotib)
            JadwalRow("Imam", jadwal.imam)
            JadwalRow("Bilal", jadwal.bilal)
            JadwalRow("Muadzin", jadwal.muadzin)
            JadwalRow("MC", jadwal.pembawaAcara)
        }
    }
}

@Composable
fun JadwalRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}
