<%
  String username = (String) session.getAttribute("userName");
	if(username == null){
		response.sendRedirect("./login.jsp");
	}
%>