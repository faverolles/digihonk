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
  var rgex = RegExp(
    r"^(([0-9]{1,4})\.){3}([0-9]{1,4})$",
    caseSensitive: false,
    multiLine: false,
  );

  @override
  void initState() {
    super.initState();
    this._ipTxtCtrl.addListener(this._checkEnableConnectBtn);
    this._ipTxtCtrl.text = "10.205.3.168";
  }

  Future<void> _onConnectBtnPress() async {
    widget.connectCallback(this._ipTxtCtrl.text);
  }

  void _checkEnableConnectBtn() {
    if (this.rgex.hasMatch(this._ipTxtCtrl.text)) {
      print(this.rgex.hasMatch(this._ipTxtCtrl.text));
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
