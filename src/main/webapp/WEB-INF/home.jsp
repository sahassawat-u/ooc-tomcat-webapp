<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@page import="java.util.ArrayList" %>
<%@page import="io.muzoo.ooc.webapp.basic.security.User" %>

<!DOCTYPE html>
<html>
<% ArrayList users = (ArrayList)session.getAttribute("users");%>
<% String currentUser = (String)session.getAttribute("username");%>
<title>Home Page</title>
<body>
<h2>
    You are <%= currentUser %>
</h2>
<form action="/" method="post">
    <input type="text" placeholder="Enter Username" name="username" required><br>
    <input type="password" placeholder="Enter Password" name="password" required><br>
    <button type="submit">Add</button>
</form>
<h2>
    This is a user list
</h2>
<% for (int i=0;i<users.size();i++){ %>
    <tr>
        <% User u = (User)users.get(i);%>
        <% if (!currentUser.equals(u.getUsername()) ) { %>
        <td>
            <%= u.getUsername() %>
        </td>
            <form action="/" method="post">
                   <input type="text" placeholder="Edit name" name="toBeEdited">
                   <button type="submit" value=<%= u.getUsername() %> name="edited">confirm</button>
                    </form>
                   <form action="/" method="post"  onclick="return confirm('Are you sure to proceed?')">
                   <button type="submit" value=<%= u.getUsername() %> name="testParam">remove</button>
                   </form>

        <%}%>
    </tr>






<% } %>
<br>
<form action="/" method="get">
    <button type="submit" value="wannaLogout" name="logout">Logout</button>
</form>



</body>
</html>