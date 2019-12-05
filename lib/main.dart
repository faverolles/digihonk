import 'dart:io';

import 'package:digihonkapp/primary_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

import 'connect_page.dart';

const debug = true;

void main() {
  SystemChrome.setSystemUIOverlayStyle(new SystemUiOverlayStyle(
    statusBarColor: Colors.white, //top bar color
    statusBarIconBrightness: Brightness.dark, //top bar icons
    systemNavigationBarColor: Colors.white, //bottom bar color
    systemNavigationBarIconBrightness: Brightness.dark, //bottom bar icons
  ));
  runApp(DigiHonkApp());
}

class DigiHonkApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primaryColor: Colors.blue,
        scaffoldBackgroundColor: Colors.white,
      ),
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

  @override
  void dispose() {
    this._socket.flush();
    this._socket.close();
    this._socket.destroy();
    super.dispose();
  }

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
    if (debug) {
      return PrimaryPage(
        socket: this._socket,
      );
    }

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
  Widget build(BuildContext context) {
    return new SafeArea(
      child: new Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: new PreferredSize(
          preferredSize: const Size.fromHeight(kToolbarHeight + 16.0),
          child: new Padding(
            padding: const EdgeInsets.only(
                left: 16.0, right: 16.0, top: 8.0, bottom: 8.0),
            child: new AppBar(
              shape: RoundedRectangleBorder(
                borderRadius: const BorderRadius.all(Radius.circular(16.0)),
              ),
              backgroundColor: Colors.white,
              title: new Text(
                "DigiHonk",
                style: new TextStyle().copyWith(color: Colors.black),
              ),
            ),
          ),
        ),
        body: this._getBody(),
      ),
    );

    return Scaffold(
      appBar: AppBar(
        title: Text("DigiHonk"),
      ),
      body: this._getBody(),
    );
  }
}
