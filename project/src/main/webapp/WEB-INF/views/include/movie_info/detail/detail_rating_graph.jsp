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
	<c:if test="${ ifWroteComment.reviewScore ne 0.0 && not empty ifWroteComment.reviewScore }">
		<div id="graph-table-div" style="display:none;">
			<table>
				<tr>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">0.5</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">1</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">1.5</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">2</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">2.5</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">3</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">3.5</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">4</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">4.5</div></div></div></td>
					<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">5</div></div></div></td>
				</tr>
			</table>
		</div>
	</c:if>
</body>
</html>