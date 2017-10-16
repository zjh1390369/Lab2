<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<html>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<head>
<link rel="stylesheet" href="css/css.css" type="text/css"/>
<title>书籍检索</title>

<style>
h1 {font-size:30px;font-family: Georgia, serif;font-weight:900;font-style:italic;}
h2 {font-size:5px;}
form {height:200px;}
header {
    background-color:purple;
    color:white;
    text-align:center;
    padding:8px;	 
}
section {
	text-align:center;
	height:300px;
	line-height:100px;
}
footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
    padding:1px;	 	 
}
</style>

</head>
<body>

<header>
	<h1>书籍检索</h1>
</header>

<section>
<p></p>
<form id="search" action="search" method="post">
        <input id="searchQuery" name="searchQuery" type="text" class="start" placeholder="ISBN,书名,作者" style=width:400px;height:40px>
    	<button type="submit" id="searchSubmit" name="searchSubmit" class="btn" style=background:blue;color:yellow;height:40px;width:80px>搜索一下</button>
</form>
<s:a action="insertAuthor" style="font-size:20px;color:blue">添加作者</s:a>
<s:a action="insertBook" style="font-size:20px;color:blue">添加书籍</s:a>
</section>

<footer>
<h2>制作@Charlie赵</h2>
</footer>

</body>
</html>
