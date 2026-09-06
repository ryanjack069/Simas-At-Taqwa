import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# 1. Add CatatanCard and WebsiteCard
additional_components = """
@Composable
fun CatatanCard() {
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
                Text("a. Khotib dan Imam : Ust. H.M. Husnan (085258068360), Ust. Jawahir (085334373660), Ust Moh. Baedhowi(081358234181)", style = catatanStyle)
                Text("b. Bilal dan Muadzin : Hadi Isnaeni (082143211902), M Abdurrohim dan Busar", style = catatanStyle)
                Text("c. Pembawa Acara : Ky. Ahmadi (Full Timer)/Humas (082131542070)", style = catatanStyle)
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
"""

if "fun CatatanCard" not in content:
    content += additional_components

# Add necessary imports
imports = [
    "import kotlinx.coroutines.delay",
    "import androidx.compose.ui.unit.sp"
]
for imp in imports:
    if imp not in content:
        content = content.replace("import com.example.R", f"import com.example.R\n{imp}")

# 2. Modify AppScreen.Home Layout
pattern_layout = re.compile(r'(HeroNextJadwal\(nextJadwal!!\)\n\s+\}\n\s+LocationCard\(\))')
replacement_layout = r'HeroNextJadwal(nextJadwal!!)\n                            CatatanCard()\n                        }\n                        LocationCard()\n                        WebsiteCard()'
content = pattern_layout.sub(replacement_layout, content)

# 3. Modify HeroNextJadwal for Date and Timer
hero_original = """fun HeroNextJadwal(jadwal: Jadwal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GreenPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {"""

hero_new = """fun HeroNextJadwal(jadwal: Jadwal) {
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
"""
content = content.replace(hero_original, hero_new)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

