<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${ movieInfo.posterUrl[0] ne 'nan' }">
      <br><br><br>
      <% int cnt = 0; %>
      <div align="center" style="width:100%;">
         <div align="center" style="width:80%;">
            <div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">Posters</span></div>
            <div style="display:flex; position:relative">
            
               <button id="postersLeftBtn" class="leftBtn" type="button" onclick="postersLeftBtn();">◀</button>
               <c:forEach var="url" items="${ movieInfo.posterUrl }">
               <% if(cnt < 5){ %>
                  <div class="posters" style="display:; padding-right:10px; position:relative;">
                     <img style="width:300px; height:428.16px;" src="${ url }">
                  </div>
               <% }else{ %>
                  <div class="posters" style="display:none; padding-right:10px; position:relative;">
                     <img style="width:300px; height:428.16px;" src="${ url }">
                  </div>
               <% } cnt++; %>
               </c:forEach>
               <% if(cnt > 5){ %>
                  <button id="postersRightBtn" class="rightBtn" type="button" onclick="postersRightBtn('<%=cnt%>');">▶</button>
               <% } %>
            </div>
         </div>
      </div>
   </c:if>
</body>
</html>