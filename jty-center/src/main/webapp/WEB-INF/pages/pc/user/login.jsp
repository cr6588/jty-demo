<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="/resources/css/ext4.2.1/ext-theme-classic/ext-theme-classic-all.css" rel="stylesheet" type="text/css" />
<!-- <link href="/resources/js/ext4.2.1/modern/theme-cupertino/resources/theme-cupertino-all.css" rel="stylesheet" type="text/css" /> -->
       <script type="text/javascript" src="/resources/js/jquery/jquery-1.10.2.js"></script>
       <script src="/resources/js/ext4.2.1/ext-all.js" type="text/javascript" ></script>
       <script src="/resources/js/ext4.2.1/ext-lang-zh_CN.js" type="text/javascript" ></script>
<!--        <script src="/resources/js/ext4.2.1/ext-modern-all.js" type="text/javascript" ></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
       <div id="data-grid"></div>
       <script src="/resources/js/user/register.js" type="text/javascript" ></script>
       <script type="text/javascript">
       var store, grid, win, form, selectedStoreIndex;
       Ext.onReady(function() {
           form = Ext.create('Ext.form.Panel', {
               renderTo : 'data-grid',
               frame:true,
               bodyPadding: 0,
               layout:'form',
               style:'border-width:0 0 0 0',
               items : [{
                   layout:'column',
                   frame:true,
                   style:'border-width:0 0 0 0',
                   padding: 0,
                   defaults :{
                       padding: 0
                   },
                   items:[{
                           defaults :{
                               labelWidth:60,
                               labelAlign:'right',
                           },
                           columnWidth: 1,
                           style:'border-width:0 0 0 0',
                           frame:true,
                           layout:'form',
                           defaultType:'textfield',
                           items:[ {
                                   fieldLabel :"username",
                                   name : "username",
                                   allowBlank : false
                               }, {
                                   fieldLabel : "password",
                                   name : 'password',
                                   allowBlank : false
                               }
                           ]
                       }]
               } /*, {
                   labelWidth: 60,
                   labelAlign:'right',
                   fieldLabel : 'remark',
                   xtype:'textarea',
                   name : 'remark'
               }*/],
               buttons : [ {
                   text : '注册',
                   handler : function() {
                       sp_add();
                   }
               }, {
                   text : '登录',
                   formBind : true, // only enabled once the form is valid
                   disabled : true,
                   handler : function () {
                       if (form.isValid()) {
//                            if(selectedStoreIndex != null) {
//                                var o = form.getValues();
//                                for(var i in o) {
//                                    store.getAt(selectedStoreIndex).set(i, o[i]);
//                                }
//                            } else {
//                                store.insert(store.getCount(), form.getValues());
//                            }
                            var o = form.getValues();
                            $.ajax({
                                    url : '/user/loginCheck',
                                    type : "post",
                                    contentType: "application/json",
                                    data : JSON.stringify(o),
                                    success : function(result) {
                                        if (result.code == 0 && result.body){
                                            Ext.Msg.alert("提示", "登录成功", function(result){
                                                if(result){
                                                    location.href = "/user/main";
                                                }
                                            });
                                        } else {
                                            Ext.Msg.alert("提示", "登录失败" + result.message);
                                        }
                                    }
                                });
                            win.close();
                       }
                   }
               } ],
           });
       });
       function sp_refresh() {
           //不宜用loadPage(1);
           store.reload();
       }
       function sp_add() {
           form.getForm().reset();
           win.setTitle("Add User");
           win.show();
       }
       function sp_update() {
           var no = null;
           var selection = grid.getView().getSelectionModel().getSelection()[0];
           if(selection){
               selectedStoreIndex = store.indexOf(selection);
               form.loadRecord(selection);
               win.setTitle("Update User");
               win.show();
           }else{
               Ext.Msg.alert("提示", "请选择用户");
           }
       }
       function sp_delete(){
           var id = null;
           var selection = grid.getView().getSelectionModel().getSelection()[0];
           if (selection != undefined) {
               id = selection.data.id;
               Ext.Msg.confirm("提示", "确认删除吗？", function(res) {
                   if(res=="yes") {
                       $.post('/i18n/deleteUser', [ {
                           name : 'id',
                           value : id
                       } ], function(result) {
                           if (result.code == 0) {
                               Ext.Msg.alert("提示", "删除成功", function() {
                                   sp_refresh();
                               });
                           } else {
                               Ext.Msg.alert("提示", result.message);
                           }
                       });
                   } else {
                   }
               });
           }else{
               Ext.MessageBox.alert("提示", "请选择行");
           }
       }
       </script>
</body>
</html>