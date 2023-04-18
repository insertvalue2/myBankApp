<%@ page import="com.tenco.bank.handler.exception.CustomRestfullException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tenco.bank.repository.model.user.User"%>
<div class="col-sm-8">
	<h2>계좌 목록(인증)</h2>
	<h6>어서오세요 환영합니다</h6>
	<div class="align-items-center justify-content-center bg-light p-md-5 h-75">
		<c:choose>
			<c:when test="${accountList != null}">
				<table class="table">
					<thead>
						<tr>
							<th>계좌번호</th>
							<th>잔액</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="userAccount" items="${accountList}">
							<tr> 
								<%-- 코드 추가  --%>
								<td><a href="/account/detail/${userAccount.id}">${userAccount.number}</a></td>
								<td>${userAccount.balance}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p>아직 생성된 계좌가 없습니다.</p>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
