<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加书籍</title>
<style>
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
	<h1>添加书籍</h1>
</header>

<body>
<form id="addBook" action="addBook" method="post">
		<p>ISBN:<input id="ISBN" name="ISBN" type="text" value=""></p>
        <p>书名:<input id="title" name="title" type="text" value=""></p>
        <p>作者:<input id="author" name="author" type="text" value=""></p>
        <p>出版社:<input id="publisher" name="publisher" type="text" value=""></p>
        <p>出版日期:<input id="year" name="year" type="text" placeholder="Year"><input id="month" name="month" type="text" placeholder="month"><input id="day" name="day" type="text" placeholder="Day"></p>
        <p>价格:<input id="price" name="price" type="number" step="0.01" value=""></p>
        <input type="reset" style=color:red value="清空" />
    	<button type="submit" id="addBookSubmit" name="addBookSubmit" class="btn" style=color:green>提交</button>
</form>
<p></p>
<a href="/">返回首页</a>
</body>
</html>