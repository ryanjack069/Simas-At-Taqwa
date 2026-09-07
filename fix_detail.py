import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# 1. Add back the "JADWAL PETUGAS SHOLAT JUM'AT" text
old_hero_section = """                // Date Title
                val pasaran = if (jadwal.jumat.isNotBlank()) " ${jadwal.jumat}" else ""
                Text(
                    text = "Jumat$pasaran",
                    style = MaterialTheme.typography.headlineMedium,"""

new_hero_section = """                // Date Title
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
                    text = "Jumat$pasaran",
                    style = MaterialTheme.typography.headlineMedium,"""

content = content.replace(old_hero_section, new_hero_section)


# 2. Fix Ust. M. Husnan to Ust. H.M. Husnan
old_husnan = 'TakmirCard(title = "DAKWAH & PHBI", members = listOf("1. Ust. M. Husnan", "2. Ust. Imam Safi\'i"'
new_husnan = 'TakmirCard(title = "DAKWAH & PHBI", members = listOf("1. Ust. H.M. Husnan", "2. Ust. Imam Safi\'i"'
content = content.replace(old_husnan, new_husnan)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

