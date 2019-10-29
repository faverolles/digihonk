import sys
from argparse import ArgumentParser
from os import walk
import subprocess

import serial


def get_device(defL="/dev/"):
    for rool, dirs, files in walk(defL):
        for file in files:
            if 'ttyUSB' in file:
                picon = defL + file

    try:
        picon
        return picon
        device_available = True
    except NameError:
        print('ESP not connected\nClosing script')
        quit()


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('-n', dest='SSID', action='store', help='Get new SSID', default='newSSID')
    results = parser.parse_args()

    if sys.platform.startswith('linux'):
        espwrite = serial.Serial(get_device(), 115200)
        espwrite.write(results.SSID.encode())
        espwrite.close()
    elif sys.platform.startswith('win'):
        espwrite = serial.Serial('COM5', 115200)
        espwrite.write(results.SSID.encode())
        espwrite.close()
