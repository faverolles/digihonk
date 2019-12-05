import 'dart:io';

import 'package:digihonkapp/primary_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'connect_page.dart';

void main() => runApp(DigiHonkApp());

class DigiHonkApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primaryColor: Colors.blue),
      home: HomePage(),
    );
  }
}

enum ConnState {
  Connected,
  Connecting,
  None,
}

class HomePage extends StatefulWidget {
  HomePage({
    Key key,
  }) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  Socket _socket;

  ConnState _connState = ConnState.None;

  Future<bool> _connectToServer(String ip) async {
    setState(() {
      this._connState = ConnState.Connecting;
    });
    this._socket = await Socket.connect(ip, 65432);
    print("Connected -> ${this._socket.address}");
    setState(() {
      this._connState = ConnState.Connected;
    });
    return true;
  }

  Widget _getBody() {
    if (this._connState == ConnState.Connected) {
      return PrimaryPage(
        socket: this._socket,
      );
    } else if (this._connState == ConnState.Connecting) {
      return Center(
        child: Container(
          margin: EdgeInsets.all(24),
          alignment: Alignment.center,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              Text(
                "Connecting",
                textAlign: TextAlign.center,
                textScaleFactor: 2.5,
                style: TextStyle(color: Theme.of(context).primaryColor),
              ),
              Container(
                width: 50,
                height: 50,
                padding: EdgeInsets.all(8.0),
                child: Center(child: CircularProgressIndicator()),
              )
            ],
          ),
        ),
      );
    }
    return ConnectPage(connectCallback: this._connectToServer);
  }

  @override
  void dispose() {
    this._socket.flush();
    this._socket.close();
    this._socket.destroy();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("DigiHonkApp"),
      ),
      body: this._getBody(),
    );
  }
}
