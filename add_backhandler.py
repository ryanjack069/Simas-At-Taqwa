import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

old_state = "    var currentScreen by remember { mutableStateOf(AppScreen.Home) }"
new_state = """    var currentScreen by remember { mutableStateOf(AppScreen.Home) }

    BackHandler(enabled = currentScreen != AppScreen.Home) {
        currentScreen = AppScreen.Home
    }"""

content = content.replace(old_state, new_state)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)
