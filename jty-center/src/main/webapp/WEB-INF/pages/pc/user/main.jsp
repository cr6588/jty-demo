<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="/resources/css/ext4.2.1/ext-theme-gray/ext-theme-gray-all.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/ext4.2.1/ext-theme-classic/ext-theme-classic-all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/js/jquery/jquery-1.10.2.js"></script>
<script src="/resources/js/ext4.2.1/ext-all.js" type="text/javascript" ></script>
<script src="/resources/js/ext4.2.1/ext-lang-zh_CN.js" type="text/javascript" ></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/resources/js/jquery/jquery-1.10.2.js"></script>
<title>首页</title>
</head>
<body>
<div id="main"></div>
<script type="text/javascript">
// $.get("/system/getUser", {id: 1}, function (result) {
//     console.log("id=" + result.id);
// });
// var tabs = new Ext.TabPanel({
//     region:'center',
//     activeTab: 0,
//     defaults:{autoScroll:true},
//     items : [ {
//             title : '默认',
//             html : '内容'
//         }, {
//             title : '标签',
//             html : '内容'
//         }, {
//             title : '可关闭',
//             html : '内容',
//             closable:true
//         } ]
//     });

// var nav = new Ext.Panel({
//     title:'导航',
//     region:'west',
//     split:true,
//     width:200,
//     collapsible:true
// });
// var win = new Ext.Window({
//     title:'复杂布局',
//     closeable:true,
//     width:600,
//     height:350,
//     border:false,
//     layout:'border',
//     items:[nav, tabs]
// })

Ext.onReady(function () { //所有都加上此处代码确保ExtJs加载完成
    var store = Ext.create('Ext.data.TreeStore', {
        root: {
            expanded: true,
            children: [
            	{ text: '用户列表', leaf: true, dataUrl:"/user/userList"},
                { text: '订单列表', leaf: true, dataUrl:"/order/orderList"},
                { text: '商品列表', leaf: true, dataUrl:"/order/goodsList"}
//                 { text: 'homework', expanded: true, children: [
//                     { text: 'book report', leaf: true },
//                     { text: 'algebra', leaf: true}
//                 ] },
//                 { text: 'buy lottery tickets', leaf: true }
            ]
        }
    });

    var tree = Ext.create('Ext.tree.Panel', {
//         title: 'Simple Tree',
//         width: 200,
//         height: 200,
		rootVisible: false,//根目录隐藏
        store: store,
        listeners:{
            itemclick:function(v, r) {
                if (r.raw.dataUrl) {
                    //获取节点的data的值
//                     alert(r.raw.dataUrl);
//                     tab.add({autoHeight:true, title:r.raw.text, closable : true, html:"<iframe src=\"" + r.raw.dataUrl + "\" style=\"width:100%;height:100%;border:none;\"></iframe>"});
                	document.getElementById('target').src = r.raw.dataUrl;
//                 	tab.getActiveTab().setTitle(r.raw.text);
                }
            }
        },
    });
    var viewport = Ext.create('Ext.Viewport', {
            layout : 'border',
            items : [ {
                region : 'north',
                height : 100,
                html : '<h1>Demo</h1>',
                title : 'north',
                collapsible : true,
                split : true
            }, {
                title : 'west',
                region : 'west',
                width : 200,
                html : '<p>111</p>',
                split : true,
                collapsible : true,
                minwWidth : 80,
                maxWidth : 300,
                layout: 'accordion',//下面的栏位是否可折叠
                items : [ {
                    title : '第一栏',
                    items : [tree]
                }, {
                    title : '第二栏',
                    html : '第二栏'
                } ]
            }, {
                region : 'center',
                collapsible : true,
                border : true,
                autoHeight:true,
//                 html:'<div id="tbar"></div>'
                html:"<iframe id='target' src=\"/user/userList\" style=\"width:100%;height:100%;border:none;\"></iframe>"
            }, {
                region : 'south',
                html : 'south',
                title : 'south',
                collapsible : true,
                split : true,
                height : 40,
            } ]
    });
    var i=0;
    var tab=new Ext.TabPanel({
// 		    renderTo:"tbar",
		//     width:500,
// 		    height:700,
// 			autoHeight:true,
// 		    enableTabScroll:true,
// 		    activeTab:0,
// 		    bbar:[{text:"添加",handler:function(){
// 		           tab.add({title:"新面板"+i++,closable : true}
// 		           );}
// 		    	}],
		    items:[
		            {autoHeight:true, title:"用户列表", closable : true, html:"<iframe id='target' src=\"/user/userList\" style=\"width:100%;height:100%;border:none;\"></iframe>"}
	            ]
  		});

//     var tb = new Ext.Toolbar();
//         tb.render('tbar');
//         //为工具添加四个按钮
//         tb.add({
//             text : '新建',
//             handler : function() {
//                 Ext.Msg.alert('提示', '新建');
//             }
//         }, '-', {
//             text : '修改',
//             handler : function() {
//                 Ext.Msg.alert('提示', '新建');
//             }
//         }, '-', {
//             text : '删除',
//             handler : function() {
//                 Ext.Msg.alert('提示', '新建');
//             }
//         }, '-', {
//             text : '显示',
//             handler : function() {
//                 Ext.Msg.alert('提示', '新建');
//             }
//         }, '-', {
//             text : '新建',
//             handler : function() {
//                 Ext.Msg.alert('提示', '新建');
//             }
//         }, '-', {
//             text : '日期',
//             menu : Ext.create('Ext.menu.DatePicker', {
//                 handler : function(dp, date) {
//                     Ext.Msg.alert('Date Selected', 'You selected ' + Ext.Date.format(date, 'M j, Y'));
//                 }
//             })
//         }, '-', {
//             text : '颜色',
//             menu : Ext.create('Ext.menu.ColorPicker', {
//                 handler : function(cm, color) {
//                     if (typeof color == 'string') {
//                         Ext.Msg.alert('选择颜色', '选择的颜色是' + color);
//                     }
//                 }
//             })
//         });
    });
    //     $(function() {

    //     });
    // Ext.onReady(function () {
    //     new Ext.Viewport({
    //         title: "Viewport",
    //         layout: "border",
    //         defaults: {
    //             bodyStyle: "background-color: #FFFFFF;",
    //             frame: true
    //         },
    //         items: [
    //                     { region: "west", width:90, title: 'north', collapsible: true },
    // //                     { region: "east", width: 90, title: 'north', collapsible: true },
    //                     { region: "north", height: 100, title:'north' , collapsible:true },
    //                     { region: "center", split: true, border: true, collapsible: true,title:'center' },
    //                     { region: "south", title:"south", split: true, border: true, collapsible: true, height: 100 },

    //                 ]
    //     });
    // });
//     var b1 = {
//         v : "this is b1"
//     };
//     var b2 = {
//         v : "this is b2"
//     };
//     function b() {
//         alert(this.v);
//     }
//     Ext.onReady(b, b1);
//     Ext.onReady(b, b2);
</script>
</body>
</html>