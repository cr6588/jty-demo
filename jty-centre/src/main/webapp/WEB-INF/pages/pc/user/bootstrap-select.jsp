<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
<script src="/resources/js/bootstrap-select-1.12.2/js/jquery-3.1.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap-select-1.12.2/js/bootstrap-select.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script src="/resources/js/bootstrap-select-1.12.2/js/i18n/defaults-zh_CN.js"></script>
</head>
<body>
<h2>Hello World!你好</h2>
<select class="selectpicker">
  <option>Mustard</option>
  <option>Ketchup</option>
  <option>Relish</option>
</select>
  <nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="#">Navbar</a>
      </div>

      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <select class="selectpicker" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true">
            <optgroup label="filter1">
              <option>option1</option>
              <option>option2</option>
              <option>option3</option>
              <option>option4</option>
            </optgroup>
            <optgroup label="filter2">
              <option>option1</option>
              <option>option2</option>
              <option>option3</option>
              <option>option4</option>
            </optgroup>
            <optgroup label="filter3">
              <option>option1</option>
              <option>option2</option>
              <option>option3</option>
              <option>option4</option>
            </optgroup>
          </select>
        </div>

        <div class="input-group">
          <input type="text" class="form-control" placeholder="Search" name="q">

          <div class="input-group-btn">
            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
          </div>
        </div>
        <button type="submit" class="btn btn-default">Search</button>
      </form>

    </div>
    <!-- .container-fluid -->
  </nav>

    <nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="#">Navbar</a>
      </div>

      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <select id="store" class="selectpicker" multiple data-live-search="true" data-live-search-placeholder="请输入店铺关键字" title="请选择店铺">
            <optgroup label="filter1">
              <option>option1</option>
              <option>option2</option>
              <option>option3</option>
              <option>option4</option>
            </optgroup>
              <option>option1</option>
              <option>option2</option>
              <option>option3</option>
              <option>option4</option>
            
          </select>
        </div>

        <div class="input-group">
          <input type="text" class="form-control" placeholder="Search" name="q">

          <div class="input-group-btn">
            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
          </div>
        </div>
        <input type="button" class="btn btn-default" value="search" onclick="search()">
      </form>

    </div>
    <!-- .container-fluid -->
  </nav>
<script type="text/javascript">
$(function () {
    $.post("/i18n/getUserList", {"page": 1, "limit":"10"}, function (result) {
        var datas = result.rows;
        for(var data in datas) {
            $("#store").append("<option value='"+datas[data].id+"'>"+datas[data].username+ "_" + datas[data].realname + "</option>");
        }
        $('.selectpicker').selectpicker('refresh');
    });
});
function search() {
    alert("选择的值是" + $("#store").val());
}
</script>
</body>
</html>