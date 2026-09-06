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
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.example.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebViewClient
import android.webkit.WebView
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.activity.compose.BackHandler
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import android.content.Intent

val GreenPrimary = Color(0xFF1B5E20)
val GreenSecondary = Color(0xFF2E7D32)
val GoldAccent = Color(0xFFFFB300)
val BackgroundCream = Color(0xFFF1F8E9)

enum class AppScreen { Home, Jadwal, Sholat, Takmir, Profil, Admin }

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
                navigationIcon = {
                    if (currentScreen != AppScreen.Home) {
                        IconButton(onClick = { currentScreen = AppScreen.Home }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = GreenPrimary)
                        }
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_masjid),
                            contentDescription = "Logo",
                            modifier = Modifier.size(36.dp)
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
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Jadwal") },
                    label = { Text("Jadwal", color = if (currentScreen == AppScreen.Jadwal) GreenPrimary else Color.Gray) },
                    selected = currentScreen == AppScreen.Jadwal,
                    onClick = { currentScreen = AppScreen.Jadwal },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GreenPrimary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = BackgroundCream
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Petugas") },
                    label = { Text("Sholat", color = if (currentScreen == AppScreen.Sholat) GreenPrimary else Color.Gray) },
                    selected = currentScreen == AppScreen.Sholat,
                    onClick = { currentScreen = AppScreen.Sholat },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GreenPrimary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = BackgroundCream
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Takmir") },
                    label = { Text("Takmir", color = if (currentScreen == AppScreen.Takmir) GreenPrimary else Color.Gray) },
                    selected = currentScreen == AppScreen.Takmir,
                    onClick = { currentScreen = AppScreen.Takmir },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GreenPrimary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = BackgroundCream
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Profil") },
                    label = { Text("Profil", color = if (currentScreen == AppScreen.Profil) GreenPrimary else Color.Gray) },
                    selected = currentScreen == AppScreen.Profil,
                    onClick = { currentScreen = AppScreen.Profil },
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
            
            when (currentScreen) {
                AppScreen.Home -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // HERO CARD
                        if (isLoading && nextJadwal == null) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = GreenPrimary)
                        } else if (nextJadwal != null) {
                            HeroNextJadwal(nextJadwal!!)
                            CatatanCard()
                        }
                        LocationCard()
                        WebsiteCard()
                    }
                }
                AppScreen.Jadwal -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        SearchAndListWidget(jadwalList)
                    }
                }
                AppScreen.Sholat -> {
                    PetugasSholatScreen()
                }
                AppScreen.Takmir -> {
                    TakmirScreen()
                }
                AppScreen.Profil -> {
                    ProfilWebView()
                }
                AppScreen.Admin -> {
                    // HALAMAN PENGATURAN ADMIN
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        AdminPage(viewModel, jadwalList, uniqueNames)
                    }
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(1000)
                        currentTime = System.currentTimeMillis()
                    }
                }
                val dateFormat = java.text.SimpleDateFormat("EEEE, dd MMM yyyy", java.util.Locale("id", "ID"))
                val timeFormat = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale("id", "ID"))
                
                Text(text = dateFormat.format(java.util.Date(currentTime)), color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelSmall)
                Text(text = timeFormat.format(java.util.Date(currentTime)), color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))

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

@Composable
fun LocationCard() {
    val context = LocalContext.current
    val mapsUrl = "https://www.google.com/maps/place/8%C2%B020'23.1%22S+113%C2%B036'32.6%22E/@-8.33975,113.6090556,17z/data=!3m1!4b1!4m4!3m3!8m2!3d-8.33975!4d113.6090556?entry=ttu&g_ep=EgoyMDI2MDkwMi4wIKXMDSoASAFQAw%3D%3D"
    
    Card(
        modifier = Modifier.fillMaxWidth().clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
            context.startActivity(intent)
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Lokasi Masjid",
                tint = GreenPrimary,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Lokasi Masjid At-Taqwa", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GreenPrimary)
                Text("Lihat petunjuk arah di Google Maps", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}

@Composable
fun CatatanCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCream),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = GoldAccent, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Informasi & Tata Tertib Petugas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = GreenSecondary.copy(alpha = 0.3f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
            
            val ruleStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray, fontWeight = FontWeight.SemiBold)
            val detailStyle = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = GreenSecondary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kehadiran: Petugas diharap hadir 15 menit sebelum pelaksanaan dimulai.", style = ruleStyle)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.Person, contentDescription = null, tint = GreenSecondary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Halangan: Apabila berhalangan hadir, harap segera menghubungi tim Full Timer di bawah ini.", style = ruleStyle)
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Kontak Full Timer", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = GreenPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("• Khotib:", style = ruleStyle)
                    Text("Ust. Jawahir (085334373660)\nUst. Hasan Abdilah (082237593751)\nUst. H.M Husnan (085258058360)", style = detailStyle, modifier = Modifier.padding(start = 12.dp, bottom = 4.dp))
                    
                    Text("• Imam:", style = ruleStyle)
                    Text("Ust. Jawahir (085334373660)\nUst. Hasan Abdilah (082237593751)", style = detailStyle, modifier = Modifier.padding(start = 12.dp, bottom = 4.dp))
                    
                    Text("• Bilal & Muadzin:", style = ruleStyle)
                    Text("Hadi Isnaeni (082143211902)\nBusar (Muadzin)", style = detailStyle, modifier = Modifier.padding(start = 12.dp, bottom = 4.dp))
                    
                    Text("• Pembawa Acara:", style = ruleStyle)
                    Text("Ky. Ahmadi (082131542070)", style = detailStyle, modifier = Modifier.padding(start = 12.dp))
                }
            }
        }
    }
}

@Composable
fun WebsiteCard() {
    val context = LocalContext.current
    val websiteUrl = "https://sites.google.com/guru.smk.belajar.id/masjidattaqwa/home"
    
    Card(
        modifier = Modifier.fillMaxWidth().clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            context.startActivity(intent)
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = "Website Masjid",
                tint = GreenPrimary,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Website Masjid At-Taqwa", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GreenPrimary)
                Text("Kunjungi situs resmi kami", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ProfilWebView() {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                webViewClient = WebViewClient()
                loadUrl("https://simas.kemenag.go.id/profil/masjid/01.4.16.09.12.000074")
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun PetugasSholatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GreenPrimary),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(36.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "JADWAL PETUGAS SHOLAT 2026",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "MASJID ATTAQWA",
                    style = MaterialTheme.typography.titleSmall,
                    color = GoldAccent,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        val petugasList = listOf(
            Triple("SHUBUH", "Ust. IMAM SAFI'I", "BUSAR / IHWAN"),
            Triple("DHUHUR", "M ABDURROHIM", "BUSAR / IHWAN"),
            Triple("ASYAR", "Ust. H.M HUSNAN", "AHMAD SUWOTO"),
            Triple("MAGHRIB", "Ust. M JAWAHIR", "TOLAHRI"),
            Triple("ISYAK", "Ust. IMAM MASRURI", "DIO")
        )

        petugasList.forEach { (sholat, imam, muadzin) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(GreenPrimary)
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                            .width(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = sholat,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Column(
                        modifier = Modifier.padding(16.dp).weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = GreenSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Imam", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text(imam, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                        HorizontalDivider(color = Color.LightGray.copy(alpha=0.5f))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = GreenSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Muadzin", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text(muadzin, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TakmirScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GreenPrimary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "STRUKTUR TAKMIR MASJID AT TAQWA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "SUMBERAN - AMBULU",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "PERIODE : 2025 - 2030",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        // Pengurus Inti
        TakmirCard(title = "PEMBINA", members = listOf("PEMERINTAH DESA AMBULU"))
        TakmirCard(title = "KETUA", members = listOf("Ust. Moh. Baedowi"))
        TakmirCard(title = "WAKIL KETUA", members = listOf("Didik Krisdianto"))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                TakmirCard(title = "SEKRETARIS", members = listOf("Subur Hariyanto", "WAKIL: Ahmad Hambaqis"))
            }
            Box(modifier = Modifier.weight(1f)) {
                TakmirCard(title = "BENDAHARA", members = listOf("H. Hadi Subiyantoro", "WAKIL: Devi Purwanto"))
            }
        }
        
        TakmirCard(title = "PENASEHAT", members = listOf("1. H. Mahbub Junaedi", "2. H. Shokib", "3. H. Undang Taufik", "4. H. Aris Prasetya", "5. H. Dzikron Sofyan", "6. Ust. Achmad Zuhri Syam", "7. Nanang Supriyanto", "8. Erfan Hanaki", "9. Ust. Imam Masruri"))

        // Bidang-bidang
        Text("BIDANG - BIDANG", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GreenPrimary)
        
        TakmirCard(title = "PENDIDIKAN", members = listOf("1. Ust. M. Asy'ari", "2. Ust. M. Jiwandono", "3. Ismail", "4. Afifatum Munawaroh", "5. Nur Ida"))
        TakmirCard(title = "DAKWAH & PHBI", members = listOf("1. Ust. M. Rosyid F.", "2. Ust. M. Husnan", "3. Ust. Imam Saeroji", "4. Ust. M. Qomari Miftakhul Munir", "5. Hasan Abdillah"))
        TakmirCard(title = "BAZNAS DAN SOSIAL", members = listOf("1. Adi Purwanto", "2. Khoirul Anam", "3. Imam Agus Wahid", "4. Anggota Baznas"))
        TakmirCard(title = "KEAMANAN DAN KETERTIBAN", members = listOf("1. Nan Sugiono", "2. Junaedi", "3. So'im", "4. Dullah", "5. M. Rofik", "6. Mukhlis", "7. Budi Wahyono", "8. Ali Sutejo", "9. Elok"))
        TakmirCard(title = "HUMAS", members = listOf("1. Ahmadi", "2. Imron Hanafi", "3. Romli", "4. Asmat", "5. Ketua RW 20", "6. Ketua RW 19", "7. Ketua RW 23", "8. Ketua RW 22"))
        TakmirCard(title = "KEPEMUDAAN REMAS DAN SENI", members = listOf("1. Ahmad Suwoto", "2. Abdul Qodir", "3. M. Rifqi Fauzi", "4. M. Arjun Naja"))
        TakmirCard(title = "REHABILITASI BANGUNAN", members = listOf("1. H. Suharto", "2. M. Abdurrohim", "3. Sagi", "4. Masihi"))
        TakmirCard(title = "PEMELIHARAAN DAN PERAWATAN", members = listOf("1. Hadi Isnaeni", "2. Heru Purwoso", "3. Heru Purwoko", "4. Samin", "5. Tolahri"))
        TakmirCard(title = "PENDANAAN", members = listOf("1. M. Ijul Arifianto", "2. M. Nur Habibi", "3. Solikin", "4. M. Guntur Bahtiar"))
        TakmirCard(title = "PEMBINAAN WANITA", members = listOf("1. Bu Mausufa", "2. Bu Ribut Eko Mujiati", "3. Anggota Relawan"))
        TakmirCard(title = "DKM DEWAN KEBERSIHAN", members = listOf("1. Jawahir", "2. Sholeh", "3. Ikhwan", "4. Busar", "5. Ahmad Nurkholis"))
        TakmirCard(title = "MASYARAKAT", members = listOf())
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TakmirCard(title: String, members: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.fillMaxWidth().background(GreenPrimary).padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title, 
                    fontWeight = FontWeight.Bold, 
                    color = GoldAccent, 
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            if (members.isNotEmpty()) {
                Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                    members.forEach { member ->
                        Text(
                            text = member, 
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
