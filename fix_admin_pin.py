import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# Add note about password
admin_form_note = """        Button(
            onClick = {
                viewModel.updateJadwal(
                    no = selectedJadwal!!.no,
                    muadzin = muadzin,
                    mc = mc,
                    bilal = bilal,
                    khotib = khotib,
                    imam = imam,
                    pin = "1234" 
                )
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text("Simpan Perubahan", fontSize = MaterialTheme.typography.titleMedium.fontSize)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "*Catatan: Tombol simpan ada di bagian ini. Jika ingin mengubah PIN Admin (standar: 1234), ini perlu disesuaikan bersama dengan sistem database Kemenag/Google Script Anda.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )"""

content = re.sub(r'Button\(\s*onClick = \{\s*viewModel\.updateJadwal\([\s\S]*?Text\("Simpan Perubahan", fontSize = MaterialTheme\.typography\.titleMedium\.fontSize\)\s*\}', admin_form_note, content)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

