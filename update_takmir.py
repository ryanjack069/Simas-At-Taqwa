import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

# 1. Penasehat: Remove "2. H. Shokib"
old_penasehat = 'TakmirCard(title = "PENASEHAT", members = listOf("1. H. Mahbub Junaedi", "2. H. Shokib", "3. H. Undang Taufik", "4. H. Aris Prasetya", "5. H. Dzikron Sofyan", "6. Ust. Achmad Zuhri Syam", "7. Nanang Supriyanto", "8. Erfan Hanaki", "9. Ust. Imam Masruri"))'
new_penasehat = 'TakmirCard(title = "PENASEHAT", members = listOf("1. H. Mahbub Junaedi", "2. H. Undang Taufik", "3. H. Aris Prasetya", "4. H. Dzikron Sofyan", "5. Ust. Achmad Zuhri Syam", "6. Nanang Supriyanto", "7. Erfan Hanaki", "8. Ust. Imam Masruri"))'
content = content.replace(old_penasehat, new_penasehat)

# 2. Dakwah & PHBI
old_dakwah = 'TakmirCard(title = "DAKWAH & PHBI", members = listOf("1. Ust. M. Rosyid F.", "2. Ust. M. Husnan", "3. Ust. Imam Saeroji", "4. Ust. M. Qomari Miftakhul Munir", "5. Hasan Abdillah"))'
new_dakwah = 'TakmirCard(title = "DAKWAH & PHBI", members = listOf("1. Ust. M. Husnan", "2. Ust. Imam Safi\'i", "3. Ust. Imam Saeroji", "4. Ust. M. Qomari Miftakhul Munir", "5. Hasan Abdillah"))'
content = content.replace(old_dakwah, new_dakwah)

# 3. Pembinaan Wanita
old_wanita = 'TakmirCard(title = "PEMBINAAN WANITA", members = listOf("1. Bu Mausufa", "2. Bu Ribut Eko Mujiati", "3. Anggota Relawan"))'
new_wanita = 'TakmirCard(title = "PEMBINAAN WANITA", members = listOf("1. Ribut Eko Mujiati", "2. B. Purwati", "3. B. Ririn"))'
content = content.replace(old_wanita, new_wanita)

# 4. DKM
old_dkm = 'TakmirCard(title = "DKM DEWAN KEBERSIHAN", members = listOf("1. Jawahir", "2. Sholeh", "3. Ikhwan", "4. Busar", "5. Ahmad Nurkholis"))'
new_dkm = 'TakmirCard(title = "DKM DEWAN KEBERSIHAN", members = listOf("1. Ust. Jawahir", "2. Sholeh", "3. Ikhwan", "4. Busar", "5. Ahmad Nurkholis", "6. Rohmat"))'
content = content.replace(old_dkm, new_dkm)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

