<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详细信息</title>
<style>
p {line-height:200%;text-align:center;}
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
	<h1>详细信息</h1>
</header>
<body>
	<p>书名: <s:property value="book.title"/></p>
	<p>ISBN: <s:property value="book.ISBN"/></p>
	<p>出版社: <s:property value="book.publisher"/></p>
	<p>出版日期: <s:date name="book.publishDate" format="yyyy-MM-dd"/></p>
	<p>价格: <s:property value="%{formatDouble(book.price)}"/></p>
	<p>作者: <s:property value="author.name"/></p>
	<p>作者年龄: <s:property value="author.age"/></p>
	<p>作者国籍: <s:property value="author.country"/></p>
<p></p>
<a href="/">返回首页</a>
</body>
</html>