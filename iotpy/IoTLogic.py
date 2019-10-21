import time
from subprocess import Popen, PIPE

from wifi import Cell
print(list(Cell.all('wlan0')))

s = time.time()
out = Popen('sudo iw dev wlan0 scan | grep SSID', stdout=PIPE, shell=True)
print(out.stdout.read())

print(time.time() - s)



