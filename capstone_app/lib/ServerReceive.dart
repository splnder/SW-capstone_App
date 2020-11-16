import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart';

class ServerReceive {

  Future<String> PostRequest() async {
    final uri = 'http://101.101.217.202:9000/putAlarmType';
    final headers = {'Content-Type': 'application/json'};
    Map<String, dynamic> body = {'alarmName': 'string'};
    String jsonBody = json.encode(body);
    final encoding = Encoding.getByName('utf-8');
    Response response = await post(
      uri,
      headers: headers,
      body: jsonBody,
      encoding: encoding,
    );
    int statusCode = response.statusCode;
    String responseBody = response.body;
    print(responseBody);
    return responseBody;
  }
  @override
  Widget build(BuildContext context) {
    PostRequest();
    return new Center(
      child: new Container(
        child: Row(
          children: <Widget>[
            FutureBuilder<String>(
              future: PostRequest(),
              builder: (context, snapshot) {
                if(snapshot.hasData) {
                  return AlertDialog(
                    title: new Text("긴급 알림"),
                    content: new Text(snapshot.data),
                    actions: <Widget>[
                      new FlatButton(
                        child: new Text("확인"),
                        onPressed: () {
                          Navigator.pop(context);
                        },
                      ),
                    ],
                  );
               }
                return CircularProgressIndicator();
              },
            )
          ],
        ),
      ),
    );
  }
}

ServerReceive serverReceive = ServerReceive();

