import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class PrimaryPage extends StatefulWidget {
  PrimaryPage({
    Key key,
    @required this.socket,
  }) : super(key: key);

  final Socket socket;

  static const turnTileList = <TurnTile>[
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/9.png", turnCode: "9"),
    TurnTile(uri: "assets/img/8.png", turnCode: "8"),
    TurnTile(uri: "assets/img/7.png", turnCode: "7"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/10.png", turnCode: "10"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/6.png", turnCode: "6"),
    TurnTile(uri: "assets/img/11.png", turnCode: "11"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/5.png", turnCode: "5"),
    TurnTile(uri: "assets/img/12.png", turnCode: "12"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/4.png", turnCode: "4"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
    TurnTile(uri: "assets/img/1.png", turnCode: "1"),
    TurnTile(uri: "assets/img/2.png", turnCode: "2"),
    TurnTile(uri: "assets/img/3.png", turnCode: "3"),
    TurnTile(uri: "assets/img/empty.png", turnCode: "e"),
  ];

  @override
  _PrimaryPageState createState() => _PrimaryPageState();
}

class _PrimaryPageState extends State<PrimaryPage> {
  var _displayString = "Ready";
  var _selectedTurningTile;

  void _turnTileTap(String turnCode) {
    setState(() {
      this._selectedTurningTile = turnCode;
      widget.socket?.write(turnCode);
    });
    print("--> $turnCode");
  }

  @override
  Widget build(BuildContext context) {
    return Center(
//      child: Card(
//        elevation: 0,
//        shape: RoundedRectangleBorder(
//            borderRadius: new BorderRadius.all(Radius.circular(16))),
//        margin: EdgeInsets.all(18),
      child: Container(
        margin: EdgeInsets.all(16),
        padding: EdgeInsets.all(8),
        decoration: BoxDecoration(
            color: Colors.blue,
            borderRadius: new BorderRadius.all(Radius.circular(16))),
        alignment: Alignment.center,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            Expanded(
              flex: 9,
              child: Container(
                alignment: Alignment.center,
                child: StreamBuilder(
                  stream: widget.socket,
                  builder: (context, snapshot) {
                    return Text(
                      snapshot.hasData
                          ? "${String.fromCharCodes(snapshot.data)}"
                          : this._displayString,
                      style: TextStyle(color: Colors.white),
                      textScaleFactor: 3,
                    );
                  },
                ),
              ),
            ),
            Expanded(
              flex: 10,
              child: Container(
                child: GridView.count(
                  crossAxisCount: 5,
                  childAspectRatio: 1.0,
                  padding: const EdgeInsets.all(4.0),
                  mainAxisSpacing: 4.0,
                  crossAxisSpacing: 4.0,
                  children: PrimaryPage.turnTileList.map((TurnTile tt) {
                    return tt.turnCode != "e"
                        ? Container(
                            padding: EdgeInsets.all(3),
                            child: Material(
                              elevation:
                                  tt.turnCode == this._selectedTurningTile
                                      ? 5
                                      : 0,
                              type: MaterialType.card,
                              child: Ink.image(
                                image: AssetImage(tt.uri),
                                fit: BoxFit.fill,
                                child: InkWell(
                                  onTap: () => this._turnTileTap(tt.turnCode),
                                ),
                              ),
                            ),
                          )
                        : Container();
                  }).toList(),
                ),
              ),
            ),
          ],
        ),
      ),
//      ),
    );
  }
}

class TurnTile {
  final String uri;
  final String turnCode;
  const TurnTile({@required this.uri, @required this.turnCode});
}
