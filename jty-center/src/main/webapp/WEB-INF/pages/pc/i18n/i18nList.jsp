<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>国际化列表</title>
</head>
<body>
       <div id="data-grid"></div>
       <spring:message code="language"></spring:message>
       <select id="language" onchange="changeLanguage()">
            <option value="">请选择</option>
            <option value="zh">中文</option>
            <option value="en">English</option>
       </select>
       <script type="text/javascript">
       function changeLanguage() {
           if($("#language").val() == "") {
               return;
           }
           $.post("/i18n/changeLanguage", {locale:$("#language").val()}, function (res) {
               location.reload();
           })
       }
       var store, grid, win, form, selectedStoreIndex;
       Ext.onReady(function() {
           Ext.define('roleModel', {
               extend : 'Ext.data.Model',
               fields : [ "id", "code", "value", "language", "active", "isDelete", "createDate", "updateDate" ],
               idProperty : 'id'
           });
           store = Ext.create('Ext.data.Store', {
           pageSize : 10,
           model : 'roleModel',
           remoteSort : false,
           autoLoad : true,
           proxy : {
               type : 'ajax',
               url : '/i18n/getI18nList',
               actionMethods:{
                   create: "POST", read: "POST", update: "POST", destroy: "POST"
               },
               reader : {
                   type : 'json',
                   root : 'rows',
                   totalProperty : 'total'
               }
           }
           });

           grid = Ext.create('Ext.grid.Panel', {
               store : store,
               multiSelect : false,
               height : 510,
               layout : 'fit',
               columns : [ {
                   text : 'id',
                   dataIndex : 'id',
                   minWidth:90,
                   align : 'center'
               }, {
                   text : "code",
                   dataIndex : 'code',
                   minWidth:150,
                   align : 'left'
               }, {
                   text : "value",
                   dataIndex : 'value',
                   minWidth: 150,
                   align : 'left'
               }, {
                   text : "language",
                   dataIndex : 'language',
                   minWidth: 90,
                   align : 'center'
               }, {
                   text : "active",
                   dataIndex : 'active',
                   minWidth: 90,
                   align : 'center',
                   renderer : function(value){
                       return value;
                   }
               }, {
                   text : "isDelete",
                   dataIndex : 'isDelete',
                   minWidth: 90,
                   align : 'center',
                   renderer : function(value){
                       return value;
                   }
               }, {
                   text : "createDate",
                   dataIndex : 'createDate',
                   minWidth: 90,
                   align : 'center',
                   renderer : function(value){
                       return value;
                   }
               }, {
                   text : "updateDate",
                   dataIndex : 'updateDate',
                   minWidth: 90,
                   align : 'center',
                   renderer : function(value){
                       return value;
                   }
               }],
               listeners : {
                   'itemclick' : function(view, record, item, index, e) {
                   }
               },
               tbar :[
                   {text: '刷新',iconCls:'a_refresh', handler: sp_refresh},
                   {text: '新增',iconCls:'a_add', handler: sp_add},
                   {text: '修改',iconCls:'a_edit', handler: sp_update},
                   {text: '删除',iconCls:'a_delete', handler: sp_delete}
               ],
               bbar : [ {
                   xtype : 'pagingtoolbar',
                   store : store,
                   displayMsg : '显示 {0} - {1} 条，共计 {2} 条',
                   emptyMsg : '没有数据',
                   beforePageText : '当前页',
                   afterPageText : '共{0}页',
                   displayInfo : true
               } ],
               renderTo : 'data-grid'
           });
           store.on('beforeload', function(store, options) {
               var searchParams = {
				   /*
                   keyWord : $("#keyWord").val(),
                   language : $("#language").val()
				   */
               };
               Ext.apply(store.proxy.extraParams, searchParams);
           });
           form = Ext.create('Ext.form.Panel', {
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
                           items:[{
                                   fieldLabel : "id",
                                   name : 'id',
                                   readOnly:true,
                                   hidden:true
                               }, {
                                   fieldLabel :"code",
                                   name : "code",
                                   allowBlank : false
                               }, {
                                   fieldLabel : "value",
                                   name : 'value',
                               }, {
                                   xtype: "combobox",
                                   name: "language",
                                   fieldLabel: "language",
                                   store: Ext.create("Ext.data.Store", {
//                                     fields: ["Name", "Value"],
//                                     data: [
//                                         { Name: "男", Value: 1 },
//                                         { Name: "女", Value: 2 }
//                                     ]
                                       fields: ['id', 'lang'],
                                       data: [
                                           {id:'zh_CN', lang : '中文'},
                                           {id:'en_US', lang : 'English'}
                                       ]
                                   }),
                                   editable: false,
                                   displayField: "lang",
                                   valueField: "id",
                                   emptyText: "--请选择--",
                                   queryMode: "local"
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
                   text : '取消',
                   handler : function() {
                       win.close();
                   }
               }, {
                   text : '保存',
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
                                    url : '/i18n/addOrUpdateI18n',
                                    type : "post",
                                    contentType: "application/json",
                                    data : JSON.stringify(o),
                                    success : function(result) {
                                        if (result.code == 0){
                                            Ext.Msg.alert("提示", "保存成功", function(result){
                                                if(result){
                                                    sp_refresh();
                                                }
                                            });
                                        } else {
                                            Ext.Msg.alert("提示", "保存失败" + result.message);
                                        }
                                    }
                                });
                            win.close();
                       }
                   }
               } ],
           });
           win = new Ext.Window({  
               width: 500,
               resizable: false,
               closeAction: 'close',
               closable: true,  
               modal: 'true',
               buttonAlign: "center",
               items: [form]
           });
       });
       function sp_add() {
           form.getForm().reset();
           win.setTitle("Add I18n");
           win.show();
       }
       function sp_refresh() {
           //不宜用loadPage(1);
           store.reload();
       }
       function sp_update() {
           var no = null;
           var selection = grid.getView().getSelectionModel().getSelection()[0];
           if(selection){
               selectedStoreIndex = store.indexOf(selection);
               form.loadRecord(selection);
               win.setTitle("Update I18n");
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
                       $.post('/i18n/deleteI18n', [ {
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