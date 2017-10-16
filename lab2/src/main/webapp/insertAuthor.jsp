<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加作者</title>
<style>
form{line-height:300%;}
h1 {font-size:20px;font-family: Georgia, serif;font-weight:900;font-style:italic;}
header {
    background-color:purple;
    color:white;
    text-align:center;
    padding:5px;	 
}
</style>
</head>
<header>
	<h1>添加作者</h1>
</header>
<body>
<form id="addAuthor" action="addAuthor" method="post">
        <p>姓名:   <input id="name" name="name" type="text" value="${author}"></p>
        <p>年龄:     <input id="age" name="age" type="text" value=""></p>
        <p>国籍: <input id="country" name="country" type="text" value=""></p>
        <input type="reset" style=color:red value="清空" />
    	<button type="submit" id="addAuthorSubmit" name="addAuthorSubmit" class="btn" style=line-height:15px;color:green>提交</button>
</form>
<p></p>
<a href="/">返回首页</a>
</body>
</html>