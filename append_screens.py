import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

screens_code = """
@Composable
fun PetugasSholatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
                    text = "JADWAL PETUGAS SHOLAT LIMA WAKTU TAHUN 2026",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "MASJID ATTAQWA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        val petugasList = listOf(
            listOf("1", "SHUBUH", "Ust. IMAM SAFI'I", "BUSAR/IHWAN"),
            listOf("2", "DHUHUR", "M ABDURROHIM", "BUSAR/IHWAN"),
            listOf("3", "ASYAR", "Ust. H. M HUSNAN", "AHMAD SUWOTO"),
            listOf("4", "MAGHRIB", "Ust. M JAWAHIR", "TOLAHRI"),
            listOf("5", "ISYAK", "Ust. IMAM MASRURI", "DIO")
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth().background(GoldAccent).padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("NO", fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(0.1f))
                    Text("SHOLAT", fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(0.25f))
                    Text("NAMA IMAM", fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(0.35f))
                    Text("NAMA MUADZIN", fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(0.3f))
                }
                HorizontalDivider(color = Color.Gray, thickness = 1.dp)

                // Rows
                petugasList.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(row[0], fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(0.1f))
                        Text(row[1], fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(0.25f))
                        Text(row[2], color = Color.Black, modifier = Modifier.weight(0.35f))
                        Text(row[3], color = Color.Black, modifier = Modifier.weight(0.3f))
                    }
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
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
"""

if "fun TakmirScreen" not in content:
    content += screens_code

imports_to_add = [
    "import androidx.compose.foundation.background",
    "import androidx.compose.material3.HorizontalDivider",
    "import androidx.compose.foundation.verticalScroll",
    "import androidx.compose.foundation.rememberScrollState"
]
for imp in imports_to_add:
    if imp not in content:
        content = content.replace("import com.example.R", f"import com.example.R\n{imp}")

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

