import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';


import 'package:search_data/main.dart';

void main() {
  testWidgets('MyWidget has a title', (WidgetTester tester) async {
    await tester.pumpWidget(MyApp());
    final titleFinder = find.text('Search locations');
    expect(titleFinder, findsOneWidget);
  });

  // group('App test', (){
  //   testWidgets('test input fields', (WidgetTester tester) async {
  //
  //     final inputFieldPlace = find.byKey(Key("place"));
  //     final inputFieldLanguage = find.byKey(Key("language"));
  //     final inputFieldRows = find.byKey(Key("rowNumber"));
  //     final button = find.byKey(Key("floatingButton"));
  //
  //     await tester.enterText(inputFieldPlace, "london");
  //     await tester.enterText(inputFieldLanguage, "it");
  //     await tester.enterText(inputFieldRows, "10");
  //     await tester.pumpAndSettle();
  //
  //     await tester.press(button);
  //     await tester.pumpAndSettle();
  //   });
  // });
}

