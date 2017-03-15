var orderGoodsStore, orderGoodsGrid,isDeletedContact = [], goodsWin, goodsForm,selectedOrderGoodsStoreIndex, order = {};
//function loadGoodsOrder(order) {
    Ext.onReady(function() {
        Ext.define('orderGoodsModel', {
            fields : [ "id", "goods.id", "goods.name", "goods.price","num"],
            extend : 'Ext.data.Model',
            idProperty : 'id'
        });
        orderGoodsStore = Ext.create('Ext.data.Store', {
            model : 'orderGoodsModel',
            remoteSort : false,
            autoLoad : true,
            data : order,
            proxy : {
                type : 'memory',
                reader : {
                    type : 'json',
                    root : 'orderGoods',
                }
            },
            sorters : [ {
                property : 'id',
                direction : 'DESC'
            } ]
        });
    
        orderGoodsGrid = Ext.create('Ext.grid.GridPanel', {
            title: 'Order Goods',
            store : orderGoodsStore,
            multiSelect : false,
            layout : 'fit',
            plugins: [
                Ext.create('Ext.grid.plugin.CellEditing', {
                    clicksToEdit: 1
                })
            ],
            columns : [{
                text : "id",
                dataIndex : 'id',
                minWidth:90,
                align : 'center',
                flex : 1,
                hidden: true
            }, {
                text : "Goods Id",
                dataIndex : "goods.id",
                minWidth:90,
                align : 'center',
                flex : 1
            },{
                text : 'Goods Name',
                dataIndex : 'goods.name',
                minWidth:90,
                align : 'center',
                flex : 1
            }, {
                text : 'Goods Price',
                dataIndex : 'goods.price',
                minWidth:90,
                align : 'center',
                flex : 1
            }, {
                text : "num",
                dataIndex : 'num',
                minWidth:90,
                align : 'center',
                editor: {
                    allowBlank:false
                }
            }],
            tbar :[
                {text: '新增',iconCls:'a_add', handler: add_goods},
                {text: '删除',iconCls:'a_delete', handler: delete_goods}
            ]
        });
        orderGoodsStore.on('beforeload', function(store, options) {
            var searchParams = {
                    /*
                    keyWord : $("#keyWord").val(),
                    language : $("#language").val()
                    */
                };
            Ext.apply(store.proxy.extraParams, searchParams);
        });
    });
    function sp_refresh() {
        //不宜用loadPage(1);
        store.reload();
    }
    function add_goods() {
        loadGoods();
        goodsForm.getForm().reset();
        goodsWin.setTitle("Add Goods");
        goodsWin.show();
    }
    function delete_goods(){
        var id = null;
        var selection = orderGoodsGrid.getView().getSelectionModel().getSelection()[0];
        if (selection != undefined) {
            Ext.Msg.confirm("提示", "确认删除吗？", function(res) {
                if(res=="yes") {
                    orderGoodsStore.remove(selection);
                } else {
                }
            });
        }else{
            Ext.MessageBox.alert("提示", "请选择行");
        }
    }
    function loadGoods() {
        if(goodsForm != null) {return}
        goodsForm = Ext.create('Ext.form.Panel', {
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
                    columnWidth: .45,
                    style:'border-width:0 0 0 0',
                    frame:true,
                    layout:'form',
                    defaultType:'textfield',
                    items:[ {
                        fieldLabel :"name",
                        name : "name"
                        }
                    ]
                }, {
                    defaults :{
                        labelWidth:60,
                        labelAlign:'right',
                    },
                    columnWidth: .45,
                    style:'border-width:0 0 0 0',
                    frame:true,
                    layout:'form',
                    defaultType:'textfield',
                    items:[ {
                        fieldLabel :"SKU",
                        name : "SKU"
                        }
                    ]
                }, {
                    defaults :{
                        labelWidth:60,
                        labelAlign:'right',
                    },
                    columnWidth: .05,
                    style:'border-width:0 0 0 0',
                    frame:true,
                    layout:'form',
                    defaultType:'button',
                    items:[{
                        text:"搜索",
                        handler : function() {
                        }
                    }]
                }]
            }, goodsGrid]
        });
        goodsWin = new Ext.Window({
            width: 700,
            resizable: false,
            closeAction: 'close',
            closable: true,  
            modal: 'true',
            buttonAlign: "center",
            items: [goodsForm]
        });
    }
