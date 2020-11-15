import 'package:capstone_app/Server.dart';
import 'package:flutter/material.dart';

class alarmReceive extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.purple[50],
      appBar: AppBar(
        title: Text('알림 목록',
          style: TextStyle(fontSize: 30,
          ),
        ),
        elevation: 20.0,
        centerTitle: true,
        backgroundColor: Colors.pink[800],
      ),
      body: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
      ListTile(
      title: Text('',
        style: TextStyle(fontSize: 30
        ),
      ),
      contentPadding: EdgeInsets.fromLTRB(120, 50, 0, 0),
    ),
    ],
      ),
    );
  }
}
void _showDialog() {
  var context;
  showDialog(
    child: AlertDialog
    context: context,
    builder: (BuildContext context) {
      // return object of type Dialog
      return AlertDialog(
        title: new Text("Alert Dialog title"),
        content: new Text("Alert Dialog body"),
        actions: <Widget>[
          new FlatButton(
            child: new Text("Close"),
            onPressed: () {
              Navigator.pop(context);
            },
          ),
        ],
      );
    },
  );
}