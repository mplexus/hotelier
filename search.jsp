<?xml version="1.0" coding="ISO-8859-1"?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-7">
<!-- <link rel="stylesheet" type="text/css" href="mystyle.css"> -->
<title>
User home.
</title>

</head>

<body bgcolor="#ffffef">
<%
  String roomsStr = request.getParameter("rooms");
  int rooms = 0;
  try{
    rooms = Integer.parseInt(roomsStr);
  }
  catch(Exception e){
    rooms = 0;
  }
  if(rooms <= 0){
%>

<h2>invalid parameter number of rooms: <%=roomsStr%></h2>

<%
  }else{
%>

<h2 align="center">Search results for number of rooms <%=rooms%></h2>

<jsp:include page="/hotels">
    <jsp:param name="rooms" value="rooms" />
</jsp:include>
<%

  }
%>
</body>

</html>
