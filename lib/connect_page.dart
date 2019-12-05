import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class ConnectPage extends StatefulWidget {
  ConnectPage({
    Key key,
    @required this.connectCallback,
  }) : super(key: key);
  final void Function(String ip) connectCallback;

  @override
  _ConnectPageState createState() => _ConnectPageState();
}

class _ConnectPageState extends State<ConnectPage> {
  TextEditingController _ipTxtCtrl = TextEditingController();
  var _enableConnectBtn = false;
  var _titleText = "DGHonk";

  @override
  void initState() {
    super.initState();
    this._ipTxtCtrl.addListener(this._checkEnableConnectBtn);
    this._ipTxtCtrl.text = "192.168.1.15";
  }

  Future<void> _onConnectBtnPress() async {
    widget.connectCallback(this._ipTxtCtrl.text);
  }

  void _checkEnableConnectBtn() {
    var ip = this._ipTxtCtrl.text;
    if (ip.length > 0 && ip.contains(".")) {
      setState(() {
        this._enableConnectBtn = true;
      });
    } else {
      setState(() {
        this._enableConnectBtn = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          margin: EdgeInsets.all(24),
          alignment: Alignment.center,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              Text(
                this._titleText,
                textAlign: TextAlign.center,
                textScaleFactor: 2.5,
                style: TextStyle(color: Theme.of(context).primaryColor),
              ),
              TextField(
                decoration: InputDecoration(labelText: "Ip Address"),
                controller: this._ipTxtCtrl,
                keyboardType: TextInputType.number,
              ),
              Padding(
                padding: const EdgeInsets.only(top: 16.0),
                child: RaisedButton(
                  child: Text("Connect"),
                  onPressed:
                      this._enableConnectBtn ? this._onConnectBtnPress : null,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
