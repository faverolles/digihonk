from socket import *
serverName ='192.168.1.169'
serverPort = 12001
fhand = open('sasa.txt')
for txt in fhand:
    clientSocket = socket(AF_INET, SOCK_STREAM)
    clientSocket.connect((serverName,serverPort))
    clientSocket.send(txt.encode())
    modifiedSentence = clientSocket.recv(1024)
    print(modifiedSentence.decode())
clientSocket.close()
