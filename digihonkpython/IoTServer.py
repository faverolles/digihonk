import socket


class IoTServer:
    connection = None
    server_socket = None

    def __init__(self) -> None:
        try:
            port = 65432
            listen_address = '0.0.0.0'

            print('--> Starting Server')
            self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.server_socket.bind((listen_address, port))
            self.server_socket.listen()
            print(f'--> Server Listening On [{listen_address}] [{port}]')
            self.connection, addr = self.server_socket.accept()
            print(f'--> Server Connected With [{addr}]')
        except:
            if self.server_socket is not None:
                self.server_socket.close()
            if self.connection is not None:
                self.connection.close()

    def __del__(self):
        try:
            if self.server_socket is not None:
                self.server_socket.close()
            if self.connection is not None:
                self.connection.close()
            print('--> Socket And Connection Closed')
        except:
            print('--> Exception Closing Socket Or Connection')

    def send(self, data: str) -> None:
        print(f'--> Sending Data [{data}]')
        self.connection.sendall(bytes(data, 'utf-8'))

    def recv(self):
        print('--> Listening For Data')
        data = self.connection.recv(1024)
        if not data:
            print(f'--> Received Bad Data')
        else:
            print(f'--> Received [{data}]')


if __name__ == '__main__':
    server = IoTServer()
    server.recv()
    server.send("Hello")

# for i in range(0, 100):
#     data = f'From Server {i}'
#     conn.sendall(bytes(data, 'utf-8'))
#     time.sleep(1)
# while True:
#     data = conn.recv(1024)
#     if not data:
#         break
#     conn.sendall(data)
