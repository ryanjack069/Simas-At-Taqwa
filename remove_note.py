import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

bad_text = """        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "*Catatan: Tombol simpan ada di bagian ini. Jika ingin mengubah PIN Admin (standar: 1234), ini perlu disesuaikan bersama dengan sistem database Kemenag/Google Script Anda.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )"""

if bad_text in content:
    content = content.replace(bad_text, "")

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

