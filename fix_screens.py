import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

pattern = re.compile(r'if \(currentScreen == AppScreen\.Home\) \{.*?HeroNextJadwal\(nextJadwal!!\)\n\s+\}\n\s+// SEMUA JADWAL DI BAWAH DASHBOARD\n\s+SearchAndListWidget\(jadwalList\)\n\s+\}\n\s+\} else \{.*?AdminPage\(viewModel, jadwalList, uniqueNames\)\n\s+\}\n\s+\}', re.DOTALL)

replacement = """when (currentScreen) {
                AppScreen.Home -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // HERO CARD
                        if (isLoading && nextJadwal == null) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = GreenPrimary)
                        } else if (nextJadwal != null) {
                            HeroNextJadwal(nextJadwal!!)
                        }
                    }
                }
                AppScreen.Jadwal -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        SearchAndListWidget(jadwalList)
                    }
                }
                AppScreen.Admin -> {
                    // HALAMAN PENGATURAN ADMIN
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        AdminPage(viewModel, jadwalList, uniqueNames)
                    }
                }
            }"""

new_content = pattern.sub(replacement, content)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(new_content)

