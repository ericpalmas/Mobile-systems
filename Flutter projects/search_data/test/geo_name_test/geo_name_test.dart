import 'package:search_data/main.dart';
import 'package:flutter_test/flutter_test.dart';

void main(){

  GeoName geoName;

  setUp( () {
    geoName = GeoName("london", "brief description of london", "https://www.google.com/search?q=london&tbm=isch&ved=2ahUKEwi3z_PJ4oDxAhWH7qQKHeRAADoQ2-cCegQIABAA&oq=london&gs_lcp=CgNpbWcQAzIECCMQJzIECAAQQzIFCAAQsQMyBwgAELEDEEMyAggAMgIIADICCAAyBAgAEEMyBAgAEEMyBAgAEAM6CAgAELEDEIMBULgEWNUKYKANaABwAHgAgAFMiAGwA5IBATaYAQCgAQGqAQtnd3Mtd2l6LWltZ8ABAQ&sclient=img&ei=GJO7YLfjD4fdkwXkgYHQAw&bih=969&biw=1920#imgrc=0luBZbozBd_t0M");
  });

  test('GeoName test', () async {
    expect(geoName, isNotNull);
    expect(geoName.title, "london");
    expect(geoName.summary, "brief description of london");
    expect(geoName.thumbnailImg, "https://www.google.com/search?q=london&tbm=isch&ved=2ahUKEwi3z_PJ4oDxAhWH7qQKHeRAADoQ2-cCegQIABAA&oq=london&gs_lcp=CgNpbWcQAzIECCMQJzIECAAQQzIFCAAQsQMyBwgAELEDEEMyAggAMgIIADICCAAyBAgAEEMyBAgAEEMyBAgAEAM6CAgAELEDEIMBULgEWNUKYKANaABwAHgAgAFMiAGwA5IBATaYAQCgAQGqAQtnd3Mtd2l6LWltZ8ABAQ&sclient=img&ei=GJO7YLfjD4fdkwXkgYHQAw&bih=969&biw=1920#imgrc=0luBZbozBd_t0M");
  });
}