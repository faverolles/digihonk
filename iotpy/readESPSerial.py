import serial
from os import walk
from writeESPSerial import get_device

espread = serial.Serial(get_device(), 115200)
print('Waiting for bytes from device')
while True:
    try:
        print(espread.readline().decode('utf-8'), end='')
    except KeyboardInterrupt:
        break
    except UnicodeDecodeError:
        print('ESP RESET')

print('Closing connection')
espread.close()
print('Goodbye')
