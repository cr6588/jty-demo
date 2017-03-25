var registerForm;
Ext.onReady(function() {
    registerForm = Ext.create('Ext.form.Panel', {
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
                    columnWidth: .5,
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
                            fieldLabel :"username",
                            name : "username",
                            allowBlank : false
                        }, {
                            fieldLabel : "password",
                            name : 'password',
                        }, {
                            fieldLabel : 'realname',
                            name : 'realname',
                        }, {
                            fieldLabel : 'tel',
                            name : 'tel',
                        }, {
                            fieldLabel : 'qq',
                            name : 'qq',
                        }
                    ]
                },{
                    defaults :{
                        labelWidth:60,
                        labelAlign:'right',
                    },
                    layout:'form',
                    columnWidth: .5,
                    frame:true,
                    style:'border-width:0 0 0 0',
                    defaultType:'textfield',
                    items:[{
                        fieldLabel : 'address',
                        name : 'address',
                    }, {
                        fieldLabel : 'company',
                        name : 'company',
                    }, {
                        fieldLabel : 'sex',
                        name : 'sex',
                    }, {
                        fieldLabel : 'age',
                        name : 'age',
                    }, {
                        fieldLabel : 'email',
                        name : 'email',
                    }]
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
                if (registerForm.isValid()) {
//                     if(selectedStoreIndex != null) {
//                         var o = form.getValues();
//                         for(var i in o) {
//                             store.getAt(selectedStoreIndex).set(i, o[i]);
//                         }
//                     } else {
//                         store.insert(store.getCount(), form.getValues());
//                     }
                     var o = registerForm.getValues();
                     $.ajax({
                             url : '/user/addOrUpdateUser',
                             type : "post",
                             contentType: "application/json",
                             data : JSON.stringify(o),
                             success : function(result) {
                                 if (result.code == 0){
                                     Ext.Msg.alert("提示", "保存成功", function(result){
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
        width: 1000,
        resizable: false,
        closeAction: 'close',
        closable: true,  
        modal: 'true',
        buttonAlign: "center",
        items: [registerForm]
    });
});