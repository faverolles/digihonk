import re
import subprocess


def scan_wifi():
    
    res = subprocess.check_output(["netsh", "wlan", "show", "network"])
    res = res.decode("ascii")
    res = res.replace("\r", "")
    lst = res.split("\n")
    lst = lst[4:]

    ssid_lst_x = filter(lambda lmb_x: "DGHonk" in lmb_x, lst)
    # ssid_lst_x = filter(lambda lmb_x: "SSID" in lmb_x, lst)

    ssid_lst_y = list(ssid_lst_x)
    for i in ssid_lst_y:
        tmp = re.sub('SSID [0-9]* : ', '', i)
        if not tmp == "":
            print(tmp)


if __name__ == '__main__':
    scan_wifi()
