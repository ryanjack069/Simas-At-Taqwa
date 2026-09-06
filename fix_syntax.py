import re

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'r') as f:
    content = f.read()

bad_str = 'Text("Ust. Jawahir (085334373660)\nUst. Hasan Abdilah (082237593751)\nUst. H.M Husnan (085258058360)",'
good_str = r'Text("Ust. Jawahir (085334373660)\nUst. Hasan Abdilah (082237593751)\nUst. H.M Husnan (085258058360)",'
content = content.replace(bad_str, good_str)

bad_str_2 = 'Text("Ust. Jawahir (085334373660)\nUst. Hasan Abdilah (082237593751)",'
good_str_2 = r'Text("Ust. Jawahir (085334373660)\nUst. Hasan Abdilah (082237593751)",'
content = content.replace(bad_str_2, good_str_2)

bad_str_3 = 'Text("Hadi Isnaeni (082143211902)\nBusar (Muadzin)",'
good_str_3 = r'Text("Hadi Isnaeni (082143211902)\nBusar (Muadzin)",'
content = content.replace(bad_str_3, good_str_3)

with open('/app/applet/app/src/main/java/com/example/ui/screens/HomeScreen.kt', 'w') as f:
    f.write(content)

