<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>wallet Management Application</title>
</head>
<body>
<center>
    <h1>Wallet Management</h1>
    <h2>
        <a href="/wallets?action=create">Add New wallet</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of wallets</h2></caption>
        <tr>
            <th>ID</th>
            <th>ID User</th>
            <th>Icon</th>
            <th>Name wallet</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
        <c:forEach var="wallet" items="${listWallet}">
            <tr>
                <td><c:out value="${wallet.idWallet}"/></td>
                <td><c:out value="${wallet.idUser}"/></td>
                <td><c:out value="${wallet.icon}"/></td>
                <td><c:out value="${wallet.nameWallet}"/></td>
                <td><c:out value="${wallet.description}"/></td>
                <td>
                    <a href="/wallets?action=edit&idWallet=${wallet.idWallet}">Edit</a>
                    <a href="/wallets?action=delete&idWallet=${wallet.idWallet}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table><br>

    <form action="/wallets?action=find" method="post">
        <input type="text" name="name" placeholder="Name" value=""/><br>
        <input type = "submit" id = "submit" value = "Tìm kiếm"/>
    </form>
    <form action="/wallets?action=sort" method="post">
        <input type = "submit"  value = "sắp xếp"/>
    </form>
</div>
</body>
</html>