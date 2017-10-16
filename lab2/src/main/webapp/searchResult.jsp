<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@taglib prefix="s" uri="/struts-tags"%> 
<%@ page import="lab2.model.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果</title>
<style>
p {line-height:300%;}
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
	<h1>搜索结果</h1>
</header>
<body>
<s:iterator value="results">
	<p>书名:
	<s:a action="select">
		<s:param name="ISBN"><s:property value="book.ISBN"/></s:param>
		<s:property value="book.title"/>
	</s:a></p>
	<p>ISBN: <s:property value="book.ISBN"/></p>
        <p>作者: <s:property value="author.name"/></p>
	<form id="delete" action="delete?ISBN=<s:property value="book.ISBN"/>" method="post">
    	<button type="submit" id="deleteSubmit" name="deleteSubmit" class="btn" style=color:red>删除本书</button>
	</form>
</s:iterator>
<p></p>
<a href="/">返回首页</a>
</body>
</html>