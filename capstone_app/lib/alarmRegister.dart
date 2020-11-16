import 'package:capstone_app/mainScreen.dart';
import 'package:capstone_app/Server.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class alarmRegister extends StatefulWidget {
  @override
  _alarmRegisterState createState() => _alarmRegisterState();
}
class _alarmRegisterState extends State<alarmRegister> {
  bool _checkbox1 = true;
  bool _checkbox2 = true;
  bool _checkbox3 = true;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.brown[50],
      body: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          ListTile(
            title: Text('등록할 상대 ID',
              style: TextStyle(fontSize: 30
              ),
            ),
            contentPadding: EdgeInsets.fromLTRB(120, 50, 0, 0),
          ),
          ListTile(
            title: TextField(
                    decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'ID',
              ),
            ),
            contentPadding: EdgeInsets.fromLTRB(50, 20, 50, 0),
          ),
          ListTile(
            title: Text('활동 감지',
              style: TextStyle(fontSize: 25
              ),
            ),
            contentPadding: EdgeInsets.fromLTRB(150, 20, 0, 0),
          ),
          ListTile(
            title: Checkbox(
              value: _checkbox1 ,
              onChanged: (bool value) {
                setState(() {
                  _checkbox1 = value;
                });
              },
            ),
            contentPadding: EdgeInsets.fromLTRB(0, 0, 0, 0),
          ),
          ListTile(
            title: Text('쓰러짐 감지',
              style: TextStyle(fontSize: 25
              ),
            ),
            contentPadding: EdgeInsets.fromLTRB(150, 20, 0, 0),
          ),
          ListTile(
            title: Checkbox(
              value: _checkbox2 ,
              onChanged: (bool value) {
                setState(() {
                  _checkbox2 = value;
                });
              },
            ),
            contentPadding: EdgeInsets.fromLTRB(0, 0, 0, 0),
          ),
          ListTile(
            title: Text('GPS 감지',
              style: TextStyle(fontSize: 25
              ),
            ),
            contentPadding: EdgeInsets.fromLTRB(150, 20, 0, 0),
          ),
          ListTile(
            title: Checkbox(
              value: _checkbox3 ,
              onChanged: (bool value) {
                setState(() {
                  _checkbox3 = value;
                });
              },
            ),
            contentPadding: EdgeInsets.fromLTRB(0, 0, 0, 0),
          ),
          ListTile(
            title: RaisedButton(
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
              padding: EdgeInsets.fromLTRB(5, 5, 5, 5),
              child: Container(child :Text("등록 요청",
                style: TextStyle(fontSize: 25, color: Colors.black,
                ),
              ),
                width: 300,
                padding: EdgeInsets.fromLTRB(100, 10, 0, 10),

              ),
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute<void>(builder: (BuildContext context) {
                      return mainScreen();
                    })
                );
              },
              color: Colors.grey[250],
            ),
            contentPadding: EdgeInsets.fromLTRB(0, 20, 0, 0),
          ),
        ],
      ),
    );
  }
}