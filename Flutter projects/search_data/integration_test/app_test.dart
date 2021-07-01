import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:search_data/main.dart' as app;
import 'package:flutter/material.dart';
import 'dart:ui' as ui;

void main(){
  group('App test', (){
    IntegrationTestWidgetsFlutterBinding.ensureInitialized();

      testWidgets('Test interface', (WidgetTester tester) async {
        app.main();
        tester.pumpAndSettle();

        final inputFieldPlace = find.byKey(Key("place"));
        final inputFieldLanguage = find.byKey(Key("language"));
        final inputFieldRows = find.byKey(Key("rowNumber"));
        final button = find.byKey(Key("floatingButton"));

        await tester.enterText(inputFieldPlace, "london");
        await tester.enterText(inputFieldLanguage, "it");
        await tester.enterText(inputFieldRows, "10");
        await tester.pumpAndSettle();

        await tester.press(button);
        await tester.pumpAndSettle();
       });
  });
}