import base64

# Let's write a generic transparent image again so the build passes
png_b64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg=="

with open('/app/applet/app/src/main/res/drawable/logo_masjid.png', 'wb') as out_f:
    out_f.write(base64.b64decode(png_b64))

