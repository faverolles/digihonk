import subprocess


def scan_wifi():
    res = subprocess.check_output(["netsh", "wlan", "show", "network"])
    res = res.decode("ascii")
    res = res.replace("\r", "")
    lst = res.split("\n")
    lst = lst[4:]
    ssid_lst = []
    for i in lst:
        if (i % 5) == 0:
            ssid_lst.append(lst[i])
    print(ssid_lst)


if __name__ == '__main__':
    scan_wifi()
