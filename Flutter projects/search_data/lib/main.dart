import 'package:flutter/material.dart';
import 'dart:developer';
import 'dart:convert';
import 'package:http/http.dart' as http;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Fulltext Search',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String q = 'london';
  String maxRows = '10';
  String lang = 'it';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset : false,
      appBar: AppBar(
        title: Text("Search locations"),
        leading: Icon(Icons.place),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: EdgeInsets.all(20.0),
              child: TextFormField(
                key: Key("place"),
                decoration: InputDecoration(
                    border: UnderlineInputBorder(), labelText: 'Place'),
                onChanged: (text) {
                  q = text;
                },
              ),
            ),
            Padding(
              padding: EdgeInsets.all(20.0),
              child: TextFormField(
                key: Key("language"),
                decoration: InputDecoration(
                    border: UnderlineInputBorder(), labelText: 'Language'),
                onChanged: (text) {
                  lang = text;
                },
              ),
            ),
            Padding(
              padding: EdgeInsets.all(20.0),
              child: TextFormField(
                key: Key("rowNumber"),
                decoration: InputDecoration(
                    border: UnderlineInputBorder(), labelText: 'Row number'),
                onChanged: (text) {
                  maxRows = text;
                },
              ),
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        key: Key("floatingButton"),
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => SecondRoute(
                      q,
                      maxRows,
                      lang,
                    )),
          );
        },
        child: const Icon(Icons.navigation),
      ),
    );
  }
}

class SecondRoute extends StatelessWidget {
  final q, maxRows, lang;

  SecondRoute(this.q, this.maxRows, this.lang);

  fetchGeonames() async {
    final response = await http.get(Uri.parse(
        'http://api.geonames.org/wikipediaSearchJSON?q=' +
            q +
            '&maxRows=' +
            maxRows +
            '&lang=' +
            lang +
            '&username=supsi'));

    if(response.statusCode == 200) {
      var jsonData = jsonDecode(response.body)["geonames"];
      List<GeoName> geonames = [];
      for (var u in jsonData) {
        GeoName geoname = GeoName(u["title"], u["summary"], u["thumbnailImg"]);
        geonames.add(geoname);
      }
      return geonames;
    } else {
      throw Exception('Can\'t load everything response');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset : false,
      appBar: AppBar(
        title: Text("Detail page"),
      ),
      body: Center(
          child: Container(
        child: Card(
          child: FutureBuilder(
            future: fetchGeonames(),
            builder: (context, snapshot) {
              if (snapshot.data == null) {
                return Container(
                  child: Center(
                    child: Text('Loading ...'),
                  ),
                );
              } else
                return ListView.builder(
                    itemCount: snapshot.data.length,
                    itemBuilder: (context, i) {
                      return Card(
                        child: ListTile(
                          leading: Image.network(snapshot.data[i].thumbnailImg),
                          title: Text(snapshot.data[i].title),
                          subtitle: Text(snapshot.data[i].summary),
                          trailing: Icon(Icons.more_vert),
                          isThreeLine: true,
                        ),
                      );
                    });
            },
          ),
        ),
      )),
    );
  }
}

class GeoName {
  final String title, summary, thumbnailImg;
  GeoName(this.title, this.summary, this.thumbnailImg);
}
