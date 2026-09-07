import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

old_hero_pattern = re.compile(r'@Composable\nfun HeroNextJadwal.*?fun HeroRoleRow.*?\}', re.DOTALL)

# Let's find the exact block using string manipulation to be safe
start_hero = content.find('@Composable\nfun HeroNextJadwal')
if start_hero == -1:
    start_hero = content.find('fun HeroNextJadwal')

end_hero = content.find('fun AdminPage', start_hero)
if end_hero != -1:
    end_hero = content.rfind('@Composable', start_hero, end_hero)

if start_hero != -1 and end_hero != -1:
    old_code = content[start_hero:end_hero]
else:
    print("Could not find the block to replace")
    exit(1)

new_code = """@Composable
fun HeroNextJadwal(jadwal: Jadwal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GreenPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            // Background decoration
            Icon(
                painter = painterResource(id = R.drawable.logo_masjid),
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.05f),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 50.dp, y = 50.dp)
            )
            
            Column(modifier = Modifier.padding(24.dp)) {
                // Header (Current Time)
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
                    
                    Column {
                        Text(text = dateFormat.format(java.util.Date(currentTime)), color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                        Text(text = timeFormat.format(java.util.Date(currentTime)), color = GoldAccent, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    
                    Box(
                        modifier = Modifier
                            .background(GoldAccent.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "JUM'AT INI",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                
                // Date Title
                val pasaran = if (jadwal.jumat.isNotBlank()) " ${jadwal.jumat}" else ""
                Text(
                    text = "Jumat$pasaran",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = jadwal.tanggal,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(16.dp))
                
                // Roles
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HeroRoleRow("Khotib", jadwal.khotib, Icons.Default.Person)
                    HeroRoleRow("Imam", jadwal.imam, Icons.Default.Person)
                    HeroRoleRow("Bilal & Muadzin", "${jadwal.bilal} / ${jadwal.muadzin}", Icons.Default.Person)
                    HeroRoleRow("MC", jadwal.pembawaAcara, Icons.Default.Person)
                }
            }
        }
    }
}

@Composable
fun HeroRoleRow(role: String, name: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = role, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
            Text(text = name, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

"""

content = content.replace(old_code, new_code)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

