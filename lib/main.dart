import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _State();
}

class _State extends State<MyHomePage> {
  static const methodChannel = MethodChannel('com.example.forwardit/method');
  static const presssureChannel = EventChannel('com.example.forwardit/pressure');


  String _telephonyAvailable = 'Unknown';

  Future<void> _checkAvailability() async{
    try{
      var available = await methodChannel.invokeMethod('isTelephonyAvailable');
      setState(() {
        _telephonyAvailable = available.toString();
      });
    } on PlatformException catch (e){
      print(e);
    }
    
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text("Telephony Avaialable?: $_telephonyAvailable"),
          ElevatedButton(onPressed: ()=>_checkAvailability(), child: Text("check sim availability"))
        ],
      ),
    );
  }
}


