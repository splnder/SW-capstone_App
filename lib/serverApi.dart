import 'dart:convert';
import 'package:http/http.dart' as http;


class serverApi {
  Future<bool> get() async {
    var client = new http.Client();
    try {
      http.Response response = await client.get('http://127.0.0.1:9000/');
      if (response.statusCode == 200) {
        final jsonBody = json.decode(response.body);
        print(jsonBody);
        return true;

      } else { // 200 안뜨면 에러
        return false;
      }
    } catch (e) {
      Exception(e);
    } finally {
      client.close();
    }
    return false;
  }

  Future<bool> post() async {
    var client = new http.Client();
    try {
      http.Response response = await client.post(
          'http://127.0.0.1:9000/fall/user/11/alarm',
          body: {
            'fall' : '1',
            'user' : '2',
            '11' : '11',
            'alarm' : '3'
          }, headers: {
        'Accept': 'application/json' // json 형식만 데이터를 받겠다.
      }
      );
      if (response.statusCode == 200) {
        final jsonBody = json.decode(response.body);


        return true;
      } else {
        return false;
      }
    } catch (e) {
      Exception(e);
    } finally {
      client.close();
    }
    return false;
  }
}

serverApi api = serverApi();