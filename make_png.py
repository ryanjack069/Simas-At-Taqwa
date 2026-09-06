import base64
import os

png_b64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII="

drawable_dir = "/app/applet/app/src/main/res/drawable"
os.makedirs(drawable_dir, exist_ok=True)

with open(f"{drawable_dir}/logo_masjid.png", "wb") as f:
    f.write(base64.b64decode(png_b64))

# Let's also do it for the app icon, just overriding the default ic_launcher
mipmap_any = "/app/applet/app/src/main/res/mipmap-anydpi-v26"
os.makedirs(mipmap_any, exist_ok=True)

with open(f"{mipmap_any}/ic_launcher.xml", "w") as f:
    f.write('''<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@drawable/logo_masjid"/>
</adaptive-icon>
''')
    
with open(f"{mipmap_any}/ic_launcher_round.xml", "w") as f:
    f.write('''<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@drawable/logo_masjid"/>
</adaptive-icon>
''')

# Also delete default raster launcher icons so they don't conflict
os.system("rm -f /app/applet/app/src/main/res/mipmap-*/ic_launcher.png")
os.system("rm -f /app/applet/app/src/main/res/mipmap-*/ic_launcher_round.png")

