import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# 1. Update AppScreen enum
content = content.replace("enum class AppScreen { Home, Jadwal, Profil, Admin }", "enum class AppScreen { Home, Jadwal, Petugas, Takmir, Profil, Admin }")

# 2. Update CatatanCard
old_catatan = """                Text("a. Khotib dan Imam : Ust. H.M. Husnan (085258068360), Ust. Jawahir (085334373660), Ust Moh. Baedhowi(081358234181)", style = catatanStyle)
                Text("b. Bilal dan Muadzin : Hadi Isnaeni (082143211902), M Abdurrohim dan Busar", style = catatanStyle)
                Text("c. Pembawa Acara : Ky. Ahmadi (Full Timer)/Humas (082131542070)", style = catatanStyle)"""

new_catatan = """                Text("a. Khotib : Ust. Jawahir (085334373660), Ust Hasan Abdilah (082237593751), Ust. H. M Husnan (085258058360)", style = catatanStyle)
                Text("b. Imam : Ust. Jawahir (085334373660), Ust Hasan Abdilah (082237593751)", style = catatanStyle)
                Text("c. Bilal : Hadi Isnaeni (082143211902)", style = catatanStyle)
                Text("d. Muadzin : Hadi Isnaeni (082143211902) dan Busar", style = catatanStyle)
                Text("e. Pembawa Acara : Ky. Ahmadi (082131542070)", style = catatanStyle)"""

content = content.replace(old_catatan, new_catatan)

# 3. Add to NavigationBar
nav_item_profil = """                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Profil") }"""

nav_items_new = """                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Petugas") },
                    label = { Text("Petugas", color = if (currentScreen == AppScreen.Petugas) GreenPrimary else Color.Gray) },
                    selected = currentScreen == AppScreen.Petugas,
                    onClick = { currentScreen = AppScreen.Petugas },
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
                    icon = { Icon(Icons.Default.Info, contentDescription = "Profil") }"""

if "AppScreen.Petugas" not in content.split("NavigationBarItem")[2]:
    content = content.replace(nav_item_profil, nav_items_new)

# 4. Add to when (currentScreen)
when_profil = """                AppScreen.Profil -> {
                    ProfilWebView()
                }"""

when_new = """                AppScreen.Petugas -> {
                    PetugasSholatScreen()
                }
                AppScreen.Takmir -> {
                    TakmirScreen()
                }
                AppScreen.Profil -> {
                    ProfilWebView()
                }"""
if "AppScreen.Petugas -> {" not in content:
    content = content.replace(when_profil, when_new)

# 5. Add new Icons to import
imports_to_add = [
    "import androidx.compose.material.icons.filled.Person",
    "import androidx.compose.material.icons.filled.AccountBox",
    "import androidx.compose.foundation.lazy.grid.LazyVerticalGrid",
    "import androidx.compose.foundation.lazy.grid.GridCells",
    "import androidx.compose.foundation.lazy.grid.items"
]
for imp in imports_to_add:
    if imp not in content:
        content = content.replace("import com.example.R", f"import com.example.R\n{imp}")

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

