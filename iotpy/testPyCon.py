import sys
import serial


def pycon_test_function():
    print("It Works")
    if sys.platform.startswith('win'):
        port = 'COM3'
        espwrite = serial.Serial(port, 115200)
        print(f'Is COM3 Open? {espwrite.is_open}')
        espwrite.close()


if __name__ == '__main__':
    pycon_test_function()
