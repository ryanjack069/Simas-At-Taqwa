import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

admin_col_old = """                AppScreen.Admin -> {
                    // HALAMAN PENGATURAN ADMIN
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        AdminPage(viewModel, jadwalList, uniqueNames)
                    }
                }"""

admin_col_new = """                AppScreen.Admin -> {
                    // HALAMAN PENGATURAN ADMIN
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        AdminPage(viewModel, jadwalList, uniqueNames)
                    }
                }"""

if "verticalScroll(rememberScrollState())" not in admin_col_old:
    content = content.replace(admin_col_old, admin_col_new)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

