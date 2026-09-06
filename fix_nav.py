import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# Find the bottomBar section
pattern = re.compile(r'bottomBar = \{.*?\},\s*containerColor = BackgroundCream', re.DOTALL)

replacement = """bottomBar = {
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
        containerColor = BackgroundCream"""

new_content = pattern.sub(replacement, content)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(new_content)
