import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# Add imports if they don't exist
imports = """
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
"""
for imp in imports.strip().split('\n'):
    if imp not in content:
        content = content.replace('import com.example.R', f'import com.example.R\n{imp}')

# Insert LocationCard component at the end of the file
location_card_code = """
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
"""

if "fun LocationCard" not in content:
    content += location_card_code

# Inject LocationCard inside AppScreen.Home
pattern = re.compile(r'(HeroNextJadwal\(nextJadwal!!\)\n\s+)\}')
replacement = r'\1}\n                        LocationCard()'
content = pattern.sub(replacement, content)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

