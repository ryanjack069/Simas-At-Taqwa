import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# Make sure R is imported
if 'import com.example.R' not in content:
    content = content.replace('import com.example.viewmodel.JadwalViewModel', 'import com.example.viewmodel.JadwalViewModel\nimport androidx.compose.ui.res.painterResource\nimport androidx.compose.foundation.Image\nimport com.example.R')

pattern = re.compile(r'Icon\(\s*imageVector = Icons\.Default\.AccountBalance,\s*contentDescription = "Logo",\s*modifier = Modifier\.size\(36\.dp\),\s*tint = GreenPrimary\s*\)', re.DOTALL)

replacement = """Image(
                            painter = painterResource(id = R.drawable.logo_masjid),
                            contentDescription = "Logo",
                            modifier = Modifier.size(36.dp)
                        )"""

new_content = pattern.sub(replacement, content)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(new_content)

