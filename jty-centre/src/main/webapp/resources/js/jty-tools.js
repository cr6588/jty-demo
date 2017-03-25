if(!window['jty']){
    window['jty'] = {};
}
/************define jty js params*************/
jty.params = {};
var pageSize = 20, pageHeight = 510, selectPageHeight = 350, selectPageSize = 10;

jty.params.pageSize = pageSize;
jty.params.pageHeight = pageHeight;
jty.params.selectPageSize = selectPageSize;
jty.params.selectPageHeight = selectPageHeight;

/************define jty js chche*************/
jty.cache = {};
/************define jty tools*************/
jty.tools = {};
var LoadGrants = function(){
    var panelBars = [];
    if(pageGrants['refresh'] && window['sp_refresh']){
        var refreshButton = {text: jty.tools.i18n.getMessage("message.refresh"),iconCls:'a_refresh', handler: sp_refresh};
        panelBars.push(refreshButton);
    }
    if(pageGrants['add'] && window['sp_add']) {
        var addButton = {text: jty.tools.i18n.getMessage("message.add"),iconCls:'a_add', handler: sp_add};
        panelBars.push(addButton);
    }
    if(pageGrants['delete'] && window['sp_delete']){
        var delButton = {text: jty.tools.i18n.getMessage("message.delete"),iconCls:'a_delete', handler: sp_delete};
        panelBars.push(delButton);
    }
    if(pageGrants['update'] && window['sp_update']){
        var updateButton = {text: jty.tools.i18n.getMessage("message.update"),iconCls:'a_edit', handler: sp_update};
        panelBars.push(updateButton);
    }
    if(pageGrants['view'] && window['sp_view']){
        var viewButton = {text: jty.tools.i18n.getMessage("message.view"),iconCls:'a_info', handler: sp_view};
        panelBars.push(viewButton);
    }
    if(pageGrants['save'] && window['sp_save']){
        var saveButton = {text: jty.tools.i18n.getMessage("message.save"),iconCls:'a_save', handler: sp_save};
        panelBars.push(saveButton);
    }
    return panelBars;
}

var showGender = function(value) {
    if(value == "Male") {
        return jty.tools.i18n.getMessage("message.male");
    }else {
        return jty.tools.i18n.getMessage("message.female");
    }
}
var showStatus = function(value) {
    if(value == "Internship") {
        return jty.tools.i18n.getMessage("code.Internship");
    }if(value == "Formal") {
        return jty.tools.i18n.getMessage("code.Formal");
    }if(value == "Leave") {
        return jty.tools.i18n.getMessage("code.Leave");
    }else {
        return jty.tools.i18n.getMessage("code.Probation");
    }
}

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth() + 1, // 月份
        "d+" : this.getDate(), // 日
        "h+" : this.getHours(), // 小时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
        "S" : this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


var showTime = function (value) {
    if (!value || value == "null") {
        return "";
    }
    var nowDate = new Date(value).format('yyyy-MM-dd hh:mm:ss');
    return nowDate;
}
var showDate = function (value) {
    if (!value || value == "null") {
        return "";
    }
    var nowDate = new Date(value).format('yyyy-MM-dd');
    return nowDate;
}
var parseTime = function (value) {
    if (!value || value == "null") {
        return "";
    }
    var longDate = new Date(value);
    return longDate;
}
var showBoolean = function(value) {
    if(value == "true" || value == true) {
        return jty.tools.i18n.getMessage("message.true");
    }else {
        return jty.tools.i18n.getMessage("message.false");
    }
}
var showSystemCode = function(value) {
    if (!value) {
        return "";
    }
    return jty.tools.i18n.getMessage("code." + value);
}
var I18N = function() {
    this.messageSource = null;
    this.init = function(){
        var that = this;
        if(parent == null || !parent.jty.cache['messageSource']){
            $.ajax({url : "/i18n/getMessages",
                type : 'post',
                cache: true,
                async: false,
                success : function (result) {
                    if (result.code == 0) {
                        that.messageSource = result.body;
                    } else {
                        that.messageSource = {};
                        console.log("Failed to init message tool.");
                    }
                    jty.cache['messageSource'] = that.messageSource;
                }
            });
        }else{
            that.messageSource = parent.jty.cache['messageSource'];
            jty.cache['messageSource'] = that.messageSource;
        }
    };
    this.getMessage = function(code){
        var message ="";
        for(var i = 0; i<arguments.length; i++) { 
            if(!arguments[i] || arguments[i] == "null"){
                continue;
            }
            if(this.messageSource[arguments[i]]){
                message += this.messageSource[arguments[i]];
            }else{
                message += code;
            }
            return message;
        }
    };
    this.init();
}
var getMenuIcon = function(modelId){
    var srcPicture;
    if(modelId){
        srcPicture = jty.systemParams.get("host") + "/resources/images/jty/admin/menu_icons/"+modelId+".png";
    }else{
        srcPicture = jty.systemParams.get("host") + "/resources/images/jty/admin/menu_icons/system.png";
    }
    return srcPicture;
}
/*call this method to init page panelBars, jty-tools.js must be imported before page's extpanel js,
 * the panelBars will be stored in global param window.panelBars*/
jty.tools.loadGrants = LoadGrants;
jty.tools.showGender = showGender;
jty.tools.getMenuIcon = getMenuIcon;
jty.tools.showBoolean = showBoolean;
jty.tools.showTime = showTime;
jty.tools.showDate = showDate;
jty.tools.parseTime = parseTime;
jty.tools.showSystemCode = showSystemCode;
jty.tools.showStatus = showStatus;
jty.tools.i18n = new I18N();