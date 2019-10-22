import sys

import serial
from os import walk
from argparse import ArgumentParser


def get_device(path="/dev/"):
    for rool, dirs, files in walk(path):
        for file in files:
            if 'ttyUSB' in file:
                picon = path + file

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

    if sys.platform.startswith('win'):
        port = 'COM3'
        espwrite = serial.Serial(port, 115200)
        print(f'Is COM3 Open? {espwrite.is_open()}')
        espwrite.close()
    elif sys.platform.startswith('linux'):
        espwrite = serial.Serial(get_device(), 115200)
        espwrite.write(results.SSID.encode())
        espwrite.close()
