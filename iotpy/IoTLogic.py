import subprocess
import time
from subprocess import Popen, PIPE

from wifi import Cell
print(list(Cell.all('wlan0')))

s = time.time()
out = Popen('sudo iw dev wlan0 scan | grep SSID', stdout=PIPE, shell=True)
print(out.stdout.read())

print(time.time() - s)


def pycon_test_function():
    print("It Works")


def scan_wifi():
    res = subprocess.check_output(["netsh", "wlan", "show", "network"])
    res = res.decode("ascii")
    res = res.replace("\r", "")
    lst = res.split("\n")
    lst = lst[4:]
    ssid_lst = []
    for i in lst:
        if(i % 5) == 0:
            ssid_lst.append(lst[i])
    print(ssid_lst)

