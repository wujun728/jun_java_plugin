import 'package:captcha/captcha/click_word_captcha.dart';
import 'package:flutter/material.dart';

import 'package:captcha/captcha/block_puzzle_captcha.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {

  List<DropdownMenuItem> getListData(){
    List<DropdownMenuItem> items=new List();
    DropdownMenuItem dropdownMenuItem1=new DropdownMenuItem(
      child:new Text('滑动拼图'),
      value: 1,
    );
    items.add(dropdownMenuItem1);
    DropdownMenuItem dropdownMenuItem2=new DropdownMenuItem(
      child:new Text('文字点选'),
      value: 2,
    );
    items.add(dropdownMenuItem2);

    return items;
  }

  int value;


  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text('title'),
      ),
      body: Container(
        alignment: Alignment.center,
        child: Column(
          children: <Widget>[
            TextField(
              decoration: InputDecoration(
                  icon: Icon(Icons.person),
                  labelText: "用户名",
                  labelStyle: TextStyle(
                    color: Colors.red,
                    fontSize: 20,
                  )),
            ),
            TextField(
              decoration: InputDecoration(
                  icon: Icon(Icons.lock),
                  labelText: "密码",
                  labelStyle: TextStyle(
                    color: Colors.red,
                    fontSize: 20,
                  )),
            ),
            DropdownButton(
              items: getListData(),
              hint:new Text('登录'),//当没有默认值的时候可以设置的提示
              value: value,//下拉菜单选择完之后显示给用户的值
              onChanged: (v){//下拉菜单item点击之后的回调
                if(v == 1){
                  loadingBlockPuzzle(context);
                } else {
                  loadingClickWord(context);
                }
              },
              elevation: 24,//设置阴影的高度
              style: new TextStyle(//设置文本框里面文字的样式
                  color: Colors.red
              ),
//              isDense: false,//减少按钮的高度。默认情况下，此按钮的高度与其菜单项的高度相同。如果isDense为true，则按钮的高度减少约一半。 这个当按钮嵌入添加的容器中时，非常有用
              iconSize: 50.0,//设置三角标icon的大小
            )
          ],
        ),
      ),
    );
  }

  //点选拼图
  static void loadingClickWord(BuildContext context, {barrierDismissible = true}) {
    showDialog<Null>(
      context: context,
      barrierDismissible: barrierDismissible,
      builder: (BuildContext context) {
        return ClickWordCaptcha(
          onSuccess: (v){

          },
          onFail: (){

          },
        );
      },
    );
  }

  //滑动拼图
  static void loadingBlockPuzzle(BuildContext context, {barrierDismissible = true}) {
    showDialog<Null>(
      context: context,
      barrierDismissible: barrierDismissible,
      builder: (BuildContext context) {
        return BlockPuzzleCaptchaPage(
          onSuccess: (v){

          },
          onFail: (){

          },
        );
      },
    );
  }
}
