import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class PrimaryPage extends StatefulWidget {
  PrimaryPage({
    Key key,
    @required this.socket,
  }) : super(key: key);

  final Socket socket;

  @override
  _PrimaryPageState createState() => _PrimaryPageState();
}

class _PrimaryPageState extends State<PrimaryPage> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        margin: EdgeInsets.all(24),
        alignment: Alignment.center,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            StreamBuilder(
              stream: widget.socket,
              builder: (context, snapshot) {
                return Padding(
                    padding: const EdgeInsets.symmetric(vertical: 24.0),
                    child: Text(snapshot.hasData
                        ? "${String.fromCharCodes(snapshot.data)}"
                        : ""));
              },
            ),
            Container(
              color: Colors.red,
              width: 200,
              height: 200,
            ),
          ],
        ),
      ),
    );
  }
}
