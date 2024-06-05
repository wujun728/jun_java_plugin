[中文](./README.md) &nbsp; | &nbsp; English

<p align="center">
  <a href="http://www.layui.com">
    <img src="https://sentsin.gitee.io/res/images/layui/layui.png" alt="layui" width="360">
  </a>
</p>
<p align="center">
  Classic modular front-end UI framework
</p>

---

<p align="center">
    <a href="https://www.npmjs.com/package/layui"><img src="https://img.shields.io/npm/v/layui.svg?sanitize=true" alt="Version"></a>
    <a href="./LICENSE"><img src="https://img.shields.io/badge/License-MIT-blue" alt="License MIT"></a>
    <a href="https://gitee.com/ayq947/ayq-layui-form-designer"><img src="https://gitee.com/ayq947/ayq-layui-form-designer/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/ayq947/ayq-layui-form-designer"><img src="https://gitee.com/ayq947/ayq-layui-form-designer/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>

<p align="center">
    <a href="https://gitee.com/ayq947/ayq-layui-form-designer">Gitee</a> &nbsp; | &nbsp; 
    <a href="http://116.62.237.101:8009/" target="_blank">Online Demo</a>
</p>

# ayq-layui-form-designer

####Introduction

The preliminary design of the form designer based on layui is based on FWR layui FormDesigner.

####Statement: the basic code has been uploaded. You can play on it and test the bug. I hope you can ask questions and participate in providing some interesting components. Now the first version v1.0.0 is released. It is not easy to develop and write documents, and there are not many requirements. It needs some development power to support a star. Hey hey

####Demo address

[http://www.anyongqiang.com/](http://116.62.237.101:8009/)

####Note: the basic components have been written almost. Later, the overall function will be optimized to improve the sense of experience. Later, some display effects will be optimized, mainly for the acquisition and echo of form data. The handwritten signature component is not open source for the time being. The handwritten signature is adaptive to the PC and mobile terminal, but you can experience some and put forward useful opinions.

####Handwritten signature presentation address

[ http://www.anyongqiang.com/HandwrittenSignature/index.html ]( http://116.62.237.101:8009/HandwrittenSignature/index.html )

####Instructions for use

1. The project is based on layui, jQuery and sortable

2. The project has basically realized drag layout and parent-child layout

3. The project implements the layout of most form form controls based on layui, including input box, editor, drop-down, radio, radio group, multiple selection group, date, slider, scoring, rotation, picture, color selection, picture upload, file upload, date range, sorting text box, icon selector, cron expression and handwritten signature component

####Development plan

1. Support layui form components

2. Extension components supporting layui

3. Support the method of FormDesigner object to obtain the data filled in the form or echo the form data

4. Support automatic code generation

5. Support to obtain remote data dynamic display components through URL (such as pull box, editor, picture, etc.)

6. Support customized layout and background

![ [enter picture description]（ https://images.gitee.com/uploads/images/2021/0826/172937_ c2c1adb8_ 4776207.png "3.PNG")

![ [enter picture description]（ https://images.gitee.com/uploads/images/2021/0826/172950_ 5c3d7bfe_ 4776207.png "4.PNG")

![ [enter picture description]（ https://images.gitee.com/uploads/images/2021/0826/173003_ 55605516_ 4776207.png "5.PNG")

![ [enter picture description]（ https://images.gitee.com/uploads/images/2021/0826/173143_ ce481242_ 4776207.png "6.PNG")

####Introductory case (design page)

```

var render = formDesigner.render({

Data: [], / / form design data

elem:'#formdesigner'

});

//Re render data

render.reload(options)

//Get relevant configuration information

render.getOptions()

//Get form design data

render.getData()

//Get external editor object

render.geticeEditorObjects()

```

####Getting started cases (view page)

```

var render = formPreview.render({

elem: '#testdemo',

Data: [], / / form design data

formData: {"textarea_ 1":"123",

"input_ 2":"123",

"password_ 3 ":" 123 "} / / form data to echo

});

//Re render data

render.reload(options)

//Get relevant configuration information

render.getOptions()

//Get form design data

render.getData()

//Get external editor object

render.geticeEditorObjects()

//Get the ID and upload path of the uploaded picture

render.getImages()

//The ID of the file object corresponding to the data case select uploadurl corresponds to the upload path of the file

[{select: "imageimage_ 2",uploadUrl: ""}]

//Get the ID and upload path of the uploaded file

render.getFiles()

//The ID of the file object corresponding to the data case select uploadurl corresponds to the upload path of the file

[{select: ""filefile_ 1"",uploadUrl: ""}]

//Get form data

**

Note: the current method will avoid the verification rules, and it is best to put it in the form submission

form.on('submit(demo1)', function(data){}）；

**

render.getFormData()

//Echo form data

render.setFormData(json)

//Disable forms globally

render.globalDisable()

//Global enable form

render.globalNoDisable()

**

Note: two components of these methods are not controlled (file component and picture component),

I propose these two components separately through methods, because there are many ways to upload files,

It is proposed that users define and implement their own file upload methods,

Specific cases have been written in preview.html for your own reference

**

```

####Basic parameters

|Parameter | type | description | example value|

|---|---|---|---|

|Elem | string | specifies the selector of the original table container. The method rendering method is required | "#elem"|

|Data | array | directly assign data | [{}, {},...]|

|Formdata | array | echoed form data | [{}, {},...]|

####Component parameters

|Parameter | type | description | example value|

|---|---|---|---|

|ID | string | specifies the component ID (unique), and the name value of the form submission field | "field"|

|Label | string | text box title | "name"|

|Tag | string | form type | "input"|

|Placeholder | string | placeholder | "please enter"|

|DefaultValue | object | component default value | name|

|Width | string | component width | "100%"|

|Labelwidth | string | text box width | "250px"|

|Readonly | Boolean | read only | true, false|

|Disabled | Boolean | disable | true, false|

|Required | Boolean | required | true, false|

|Columns | number | number of grid layout columns | true, false|

|Maxvalue | object | maximum | ""|

|MinValue | object | minimum | ""|

|Expression | string | validation | "email"|

|Stepvalue | number | slider step | 2|

|Isinput | Boolean | slider displays the input box | true, false|

|Datetype | string | date type | time selector|

|Dateformat | string | date format | "yyyy MM DD"|

|Ratelength | number | number of stars | 5|

|Interval | number | rotation interval MS | 3000|

|Autoplay | Boolean | rotation automatic switching | true, false|

|Anim | object | switching mode {text: 'left and right switching', value: 'default'}|

|Arrow | object | switching arrow {text: 'hover display', value: 'hover'}|

####Update log

- 2021-06-15

1. Add the basic verification rules provided by the input box layui

2. Disabled display effect optimization

3. Optimize the form display slider, score and color selector, and the field value cannot be obtained when submitting

- 2021-06-17

1. Add time range component (code not submitted yet)

2. Page adaptive optimization

- 2021-06-22

1. Add time range component

2. Presentation page submission parameter optimization

- 2021-06-24

1. Import iceeditor rich text editing component

- 2021-06-30

1. Add iceeditor rich text editing component

2. Solve the problem of abnormal style of one row and multiple columns

3. One row and multiple columns nesting problem

4. The optimized rich text parameters cannot be obtained

- 2021-07-01

1. Add sorting text box component

2. Add Icon selector component

3. Add cron expression component

4. Optimize the rich text editor (if you edit the menu, the local direct access will cause cross domain problems, and it will be normal to put it into nginx, Tomcat and other containers)

5. Release v1.0.0

- 2021-07-23

1. Update version v1.0.1

2. Add label components

3. Add button assembly

4. Optimizing the internal dragging of the fence is a problem of style confusion

5. Optimize the components that have been put into multiple, and the drag sorting is invalid

6. There are too many optimized component labels, and the component sorting style is misaligned

- 2021-08-03

1. Add handwritten signature component

- 2021-08-11

1. Optimize the interaction between components

2. Acquisition and echo of new form data in form view

3. Add disabled form and enabled form in form view

4. Update version V1.1.0

- 2021-08-26

1. Drop down, multi-choice, single choice, rotation configuration, multiple options to add drag function

2. Optimize the drop-down, multi-choice, single choice and rotation default options (the default values can be set on the configuration and design pages)

3. Optimize the width experience and change it to slider

4. Add and modify the text box length attribute, and the slider operation

5. Add required items to display*

6. Optimize code generation function

7. Add a new window to open the display page

8. Update version v1.1.5

- 2021-10-11

1. Simplify the style introduced and return to the simplicity of layui

2. Optimize some related details

8. Update version v1.1.6

####Stunt

1. Use Readme\_ 30. MD to support different languages, such as readme\_ en.md, Readme\_ zh.md

2. Gitee official blog [blog. Gitee. Com]（ https://blog.gitee.com )

3. You can[ https://gitee.com/explore ]( https://gitee.com/explore ）Use this address to learn about excellent open source projects on gitee

4. [GVP]( https://gitee.com