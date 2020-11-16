import 'package:capstone_app/activitySensor.dart';
import 'package:capstone_app/alarmReceive.dart';
import 'package:capstone_app/alarmRegister.dart';
import 'package:capstone_app/falldownSensor.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class mainScreen extends StatefulWidget {
  @override
  _mainScreenState createState() => _mainScreenState();
}
class _mainScreenState extends State<mainScreen> {
  bool _switch = true;
  bool _switch2 = true;
  bool _switch3 = true;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.amber[50],
      appBar: AppBar(
        title: Text('╰(*´︶`*)╯괜찮아요',
          style: TextStyle(fontSize: 30,
          ),
      ),
        elevation: 20.0,
        centerTitle: true,
        backgroundColor: Colors.yellow[800],
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.messenger),
            onPressed: () {
              Navigator.push(context,
                  MaterialPageRoute<void>(builder: (BuildContext context) {
                    return alarmReceive();
                  })
              );// 기능 추가
            },
          )
        ],
      ),
      drawer: Drawer(
        child: Container(color: Colors.white,
          child: ListView(
            padding: EdgeInsets.zero,
            children: <Widget>[
              DrawerHeader(
                child: Text('감지 설정',
                  style: TextStyle(fontSize: 30
                  ),
                ),
                decoration: BoxDecoration(color: Colors.brown[200]),
                padding: EdgeInsets.fromLTRB(100, 50, 0, 0),
              ),
              ListTile(
                title: Text('활동 감지',
                  style: TextStyle(fontSize: 25
                  ),
                ),
                contentPadding: EdgeInsets.fromLTRB(100, 20, 0, 0),
              ),
              ListTile(
                title: CupertinoSwitch(
                  value: _switch ,
                  onChanged: (bool value) {
                    setState(() {
                      _switch = value;
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
                contentPadding: EdgeInsets.fromLTRB(100, 20, 0, 0),
              ),
              ListTile(
                title: CupertinoSwitch(
                  value: _switch2 ,
                  onChanged: (bool value) {
                    setState(() {
                      _switch2 = value;
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
                contentPadding: EdgeInsets.fromLTRB(100, 20, 0, 0),
              ),
              ListTile(
                title: CupertinoSwitch(
                  value: _switch3 ,
                  onChanged: (bool value) {
                    setState(() {
                      _switch3 = value;
                    });
                  },
                ),
                contentPadding: EdgeInsets.fromLTRB(0, 0, 0, 0),
              ),
              ListTile(
                title: RaisedButton(
                  shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                  padding: EdgeInsets.fromLTRB(5, 5, 5, 5),
                  child: Container(child :Text("알림 대상 등록",
                    style: TextStyle(fontSize: 25, color: Colors.black,
                    ),
                  ),
                    width: 300,
                    padding: EdgeInsets.fromLTRB(60, 10, 0, 10),

                  ),
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute<void>(builder: (BuildContext context) {
                          return alarmRegister();
                        })
                    );
                  },
                  color: Colors.amber[50],
                ),
                contentPadding: EdgeInsets.fromLTRB(0, 20, 0, 0),
              ),
            ],
          ),
        ),
      ),
      body: Center(
        child: new Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            RaisedButton(
              elevation: 20.0,
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
              padding: EdgeInsets.fromLTRB(5, 5, 5, 5),
              child: Container(child :Text("활동 감지 목록 보기",
                style: TextStyle(fontSize: 30, color: Colors.black,
                ),
              ),
                padding: EdgeInsets.all(15.0),
                decoration: BoxDecoration(
                    gradient: LinearGradient(
                        colors: <Color>[
                          Colors.red[300], Colors.yellow[600],
                        ]
                    )
                ),
              ),
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute<void>(builder: (BuildContext context) {
                      return activitySensor();
                    })
                );

              },
              color: Colors.white,
            ),
            SizedBox(height: 50.0, width: 30.0,
            ),
            RaisedButton(
              elevation: 20.0,
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
              padding: EdgeInsets.fromLTRB(5, 5, 5, 5),
              child: Container(child :Text("활동 감지 목록 보기",
                style: TextStyle(fontSize: 30, color: Colors.black,
                ),
              ),
                padding: EdgeInsets.all(15.0),
                decoration: BoxDecoration(
                    gradient: LinearGradient(
                        colors: <Color>[
                          Colors.yellow[600], Colors.red[300],
                        ]
                    )
                ),
              ),
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute<void>(builder: (BuildContext context) {
                      return falldownSensor();
                    })
                );
              },
              color: Colors.white,
            ),
          ],
        ),
      ),
    );
  }
}


