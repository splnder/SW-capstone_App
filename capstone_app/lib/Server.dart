import 'package:dio/dio.dart';

const _API_fall= "http://101.101.217.202:9000/fall/user/19/alarm";
const _API_nonactive= "http://101.101.217.202:9000/nonactive/user/19/alarm";

class Server {
  Future<void> postFall() async{
    var now = DateTime.now();
    Response response;
    Dio dio = new Dio();
    response = await dio.post(_API_fall, data:
    {"latitude": 0,
      "longitude": 0,
      "timestamp": "2020-11-16T02:47:14.831Z",
      "userId": 19});
    print(response.data.toString());
  }

  Future<void> postNonactive() async{
    var now = DateTime.now();
    Response response;
    Dio dio = new Dio();
    response = await dio.post(_API_nonactive, data:
    {"latitude": 0,
      "longitude": 0,
      "timestamp": "2020-11-16T02:47:14.831Z",
      "userId": 19});
    print(response.data.toString());
  }
}

Server server = Server();