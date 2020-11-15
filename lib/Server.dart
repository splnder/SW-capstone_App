import 'package:dio/dio.dart';


const _API = "http://101.101.217.202:9000/";

class Server {
  Future<void> getReq() async{
    Response response;
    Dio dio = new Dio();
    response = await dio.get("$_API");
    print(response.data.toString());
  }

  Future<void> postReq() async{
    Response response;
    Dio dio = new Dio();
    response = await dio.post(_API, data: {""});
    print(response.data.toString());
  }

  Future<void> getReqWzQuery() async{
    Response response;
    Dio dio = new Dio();
    response = await dio.post(_API, queryParameters:{"ID ": 1});
    print(response.data.toString());
  }
}

Server server = Server();