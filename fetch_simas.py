import urllib.request
from html.parser import HTMLParser

class MyHTMLParser(HTMLParser):
    def __init__(self):
        super().__init__()
        self.text = []
    def handle_data(self, data):
        if data.strip():
            self.text.append(data.strip())

req = urllib.request.Request("https://simas.kemenag.go.id/profil/masjid/01.4.16.09.12.000074", headers={'User-Agent': 'Mozilla/5.0'})
try:
    html = urllib.request.urlopen(req).read().decode('utf-8')
    parser = MyHTMLParser()
    parser.feed(html)
    print("\n".join(parser.text[:100])) # Just print the first 100 non-empty text elements
except Exception as e:
    print("Error:", e)
