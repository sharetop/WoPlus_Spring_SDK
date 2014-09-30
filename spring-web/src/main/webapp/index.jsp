<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css">
</head>
<body>
<h2>Hello World!</h2>

<!-- Nav tabs -->
<ul class="nav nav-tabs" role="tablist">
  <li class="active"><a href="#ex0" role="tab" data-toggle="tab">获取渠道短信</a></li>
  <li><a href="#ex1" role="tab" data-toggle="tab">计费2.0</a></li>
</ul>

<!-- Tab panes -->
<div class="tab-content">
  <div class="tab-pane active" id="ex0" style="padding:20px;">
  
  <button id="getChannelSMS" class="btn btn-primary">GET SMS Content</button>
  <p>
  <textarea class="form-control" style="height:600px" id="resultDesc">
  </textarea>
  </p>
  </div>
  
  <div class="tab-pane" id="ex1" style="padding:20px;">...
  
  </div>
</div>


<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<script src="index.js"></script>
</body>
</html>
