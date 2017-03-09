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
<title>国际化列表</title>
</head>
<body>
       <div id="data-grid"></div>
       <script type="text/javascript">
       var store, grid;
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
       });
       function sp_add() {
     //      window.location.href = siping.systemParams.get("host") + "/admin/system/editI18Ns";
       }
       </script>
</body>
</html>