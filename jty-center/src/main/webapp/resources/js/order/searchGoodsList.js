var goodsStore, goodsGrid,selectedGoodsStoreIndex, goods = {};
Ext.onReady(function() {
    Ext.define('goodsModel', {
            extend : 'Ext.data.Model',
            fields : [ "id", "name", "sku", "price" ],
            idProperty : 'id'
        });
    goodsStore = Ext.create('Ext.data.Store', {
        pageSize : 10,
        model : 'goodsModel',
        remoteSort : false,
        autoLoad : true,
        proxy : {
            type : 'ajax',
            url : '/order/getGoodsList',
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

        goodsGrid = Ext.create('Ext.grid.Panel', {
            store : goodsStore,
            multiSelect : true,
//            height : 510,
            layout : 'fit',
            columns : [ {
                text : 'id',
                dataIndex : 'id',
                minWidth:90,
                align : 'center'
            }, {
                text : "name",
                dataIndex : 'name',
                minWidth:150,
                flex : 1,
                align : 'left'
            }, {
                text : "SKU",
                dataIndex : 'sku',
                minWidth: 150,
                flex : 1,
                align : 'left'
            }, {
                text : "price",
                dataIndex : 'price',
                minWidth: 90,
                align : 'center'
            }],
            listeners : {
                'itemclick' : function(view, record, item, index, e) {
                }
            },
            tbar :[
                {text: '刷新',iconCls:'a_refresh', handler: goods_refresh},
                {text: '确认',iconCls:'a_add', handler: sure_goods},
            ],
            bbar : [ {
                xtype : 'pagingtoolbar',
                store : goodsStore,
                displayMsg : '显示 {0} - {1} 条，共计 {2} 条',
                emptyMsg : '没有数据',
                beforePageText : '当前页',
                afterPageText : '共{0}页',
                displayInfo : true
            } ],
//            renderTo : 'goods_grid',
        });
        goodsStore.on('beforeload', function(store, options) {
            var searchParams = {
                /*
                keyWord : $("#keyWord").val(),
                language : $("#language").val()
                */
            };
            Ext.apply(store.proxy.extraParams, searchParams);
        });
    });
    function goods_refresh() {
        goodsStore.reload();
    }
    function sure_goods() {
        var selection = goodsGrid.getView().getSelectionModel().getSelection()[0];
        if (selection != undefined) {
            if(orderGoodsStore.find("goods.id", selection.data.id) != -1) {
                Ext.MessageBox.alert("提示", "商品已存在");
            } else {
                var tempOrderGoods = {
                        "goods.id": selection.data.id,
                        "goods.name" : selection.data.name,
                        "goods.price" : selection.data.price,
                }
                orderGoodsStore.insert(orderGoodsStore.getCount(), tempOrderGoods);
                goodsWin.hide();
            }
        }
    }
