import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# 1. AppScreen.Petugas -> AppScreen.Sholat
content = content.replace("AppScreen.Petugas", "AppScreen.Sholat")
content = content.replace("Petugas, Takmir", "Sholat, Takmir")

# 2. Navigation Item Text "Petugas" -> "Sholat"
content = content.replace('label = { Text("Petugas"', 'label = { Text("Sholat"')

# 3. Add scroll to AppScreen.Home
# Find AppScreen.Home -> { Column(modifier = Modifier.fillMaxSize().padding(16.dp)
home_col_old = """                AppScreen.Home -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),"""
home_col_new = """                AppScreen.Home -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),"""
content = content.replace(home_col_old, home_col_new)

# 4. Redesign CatatanCard
catatan_old = """fun CatatanCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCream),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Catatan :",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = GreenPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            val catatanStyle = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray, fontSize = 11.sp, lineHeight = 16.sp)
            Text("1. Petugas hadir 15 menit sebelum pelaksanaan", style = catatanStyle)
            Text("2. Apabila berhalangan hadir harap menghubungi full timer", style = catatanStyle)
            Text("3. Full Timer", style = catatanStyle)
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text("a. Khotib : Ust. Jawahir (085334373660), Ust Hasan Abdilah (082237593751), Ust. H. M Husnan (085258058360)", style = catatanStyle)
                Text("b. Imam : Ust. Jawahir (085334373660), Ust Hasan Abdilah (082237593751)", style = catatanStyle)
                Text("c. Bilal : Hadi Isnaeni (082143211902)", style = catatanStyle)
                Text("d. Muadzin : Hadi Isnaeni (082143211902) dan Busar", style = catatanStyle)
                Text("e. Pembawa Acara : Ky. Ahmadi (082131542070)", style = catatanStyle)
            }
        }
    }
}"""

catatan_new = """fun CatatanCard() {
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
}"""
content = content.replace(catatan_old, catatan_new)

# 5. Redesign PetugasSholatScreen (Now as SholatScreen/PetugasSholatScreen but refactored to cards)
petugas_regex = re.compile(r'@Composable\nfun PetugasSholatScreen\(\) \{.*?\n\}\n\n@Composable\nfun TakmirScreen\(\)', re.DOTALL)

petugas_new = """@Composable
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
fun TakmirScreen()"""

content = petugas_regex.sub(petugas_new, content)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

