import 'package:search_data/main.dart';
import 'package:flutter_test/flutter_test.dart';

void main(){
  SecondRoute restClient;

  setUp( () {
     restClient = SecondRoute("london", "10", "it");
  });

  test('SecondRoute test', () async {
    expect(restClient.q, isNotNull);
    expect(restClient.maxRows, isNotNull);
    expect(restClient.lang, isNotNull);
  });

  test('REST call to everything endpoint', () async {
    final response = await restClient.fetchGeonames();
    expect(response, isNotNull);
    expect(response.length, 10);
    response.forEach((geoName) {
      expect(geoName.title, isNotNull);
      expect(geoName.summary, isNotNull);
      expect(geoName.thumbnailImg, isNotNull);
    });
  });

}