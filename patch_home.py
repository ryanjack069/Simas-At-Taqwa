import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# 1. Update AppScreen enum
content = content.replace("enum class AppScreen { Home, Jadwal, Admin }", "enum class AppScreen { Home, Jadwal, Profil, Admin }")

# 2. Add imports
imports_to_add = [
    "import androidx.activity.compose.BackHandler",
    "import androidx.compose.material.icons.automirrored.filled.ArrowBack",
    "import android.webkit.WebView",
    "import android.webkit.WebViewClient",
    "import androidx.compose.ui.viewinterop.AndroidView",
    "import androidx.compose.material.icons.filled.Info"
]
for imp in imports_to_add:
    if imp not in content:
        content = content.replace("import com.example.R", f"import com.example.R\n{imp}")

# 3. Add BackHandler
if "BackHandler" not in content:
    backhandler_code = """    var currentScreen by remember { mutableStateOf(AppScreen.Home) }

    BackHandler(enabled = currentScreen != AppScreen.Home) {
        currentScreen = AppScreen.Home
    }
"""
    content = content.replace("    var currentScreen by remember { mutableStateOf(AppScreen.Home) }", backhandler_code)

# 4. Add TopAppBar navigationIcon
topappbar_original = """        topBar = {
            TopAppBar(
                title = {"""
topappbar_new = """        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (currentScreen != AppScreen.Home) {
                        IconButton(onClick = { currentScreen = AppScreen.Home }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = GreenPrimary)
                        }
                    }
                },
                title = {"""
if "navigationIcon = {" not in content:
    content = content.replace(topappbar_original, topappbar_new)

# 5. Add Profil NavigationBarItem
nav_item_profil = """                NavigationBarItem(
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
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Admin") }"""

if "AppScreen.Profil" not in content.split("NavigationBarItem")[2] and "AppScreen.Profil" not in content.split("NavigationBarItem")[3]:
    content = content.replace("""                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Admin") }""", nav_item_profil)

# 6. Add AppScreen.Profil to when (currentScreen)
when_profil = """                AppScreen.Profil -> {
                    ProfilWebView()
                }
                AppScreen.Admin -> {"""
if "AppScreen.Profil -> {" not in content:
    content = content.replace("                AppScreen.Admin -> {", when_profil)

# 7. Add ProfilWebView Composable
webview_composable = """
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
"""
if "fun ProfilWebView" not in content:
    content += webview_composable

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

