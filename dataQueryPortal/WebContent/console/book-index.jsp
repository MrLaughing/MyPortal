<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head> 
    <title> 图书展示系统 </title> 

 </head> 
 <body> 
 <s:actionmessage /> 
 <table> 
    <tr> 
        <th> 图书 ID</th> 
        <th> 书名 </th> 
        <th> 价格 </th> 
        <th> 操作 </th> 
    </tr> 
    <s:iterator value="model"> 
    <tr> 
        <td><s:property value="id"/></td> 
        <td>${name}</td> 
        <td>${price}</td> 
        <td><a href="book/${id}"> 查看 </a> | 
        <a href="book/${id}/edit"> 编辑 </a> | 
        <a href="book/${id}/deleteConfirm"> 删除 </a></td> 
    </tr> 
    </s:iterator> 
 </table> 
 <!-- <a href="/book/new"> 创建新图书 </a>  -->
 </body> 
 </html>