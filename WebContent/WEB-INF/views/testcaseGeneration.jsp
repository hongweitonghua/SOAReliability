<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<style>
	body{
			padding: 0;
			margin: 0;
		}
		h1{
			text-align: center;
		}
		.banxin{
			width: 980px;
			margin:0 auto;
		}
		.content{
			margin-top: 25px;
		}
		.content p{
			text-align: left;
			width: 340px;
		}
		.content p input{
			float: right;
		}
		.testcase{
			height: 400px;
			overflow: auto;
		}
		h4{
			margin-top: 25px;
		}
</style>
</head>
<body>
	<div class="banxin">
		<h1>Web Service Test Case Automated Generation Tool</h1>
		<div class="content">	
			<p>Test Path Total Number: <input type="text" name="" value="${testpaths.size()}"></p>
			<p>Test case Number：<input type="text" name="" value="${testpathTotalNumber} "></p>
			<p>Test Case Coverage: <input type="text" name="" value="100%"></p>
			<h4>Test Path Set:</h4>
			<div class="testpath">
				<table  class="table table-bordered table-condensed table-hover">
				<tr>
					<th>ID</th>
					<th>Test Path</th>
				</tr>				
				 <s:iterator value="#request.testpaths" status="i">
    	 			<tr>
						<td><s:property value="%{#i.index+1}"/></td>
						<td>${testpath}</td>
					</tr>
				</s:iterator>
				 <%-- <s:iterator value="pathArr" status="i" id="path">
    	 			<tr>
						<td><s:property value="%{#i.index+1}"/></td>
						<td><s:property  value="path"/></td>
					</tr>
				</s:iterator>  --%>
			</table>
			</div>
			<form action="testbpel-pathselect" method="get" >
				筛选条件：<input type="text" name="testpathbpel.testpath">
				<button>筛选</button>
			</form>
			<h4>Test Case Set:（共${testcases.size()}条）</h4>
			<div class="testcase">
				<table  class="table table-bordered table-condensed table-hover">
					<tr>
						<th>ID</th>
						<th>Test Path </th>
						<th>Test Case Set(a,b,type)</th>
					</tr>
					<s:iterator value="#request.testcases" status="i">
	    	 			<tr>
							<td><s:property value="%{#i.index+1}"/></td>
							<td>${testpathbpel.testpath}</td>
							<td>${testcase}</td>
						</tr>
					</s:iterator> 	
				</table>
			</div>
			
		</div>
	</div>

</body>
</html>