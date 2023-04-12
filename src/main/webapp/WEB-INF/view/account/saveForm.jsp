<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<div class="col-sm-8">
	<h2>계좌 생성(인증)</h2>
	<h6>어서오세요 환영합니다</h6>
	<div class="align-items-center justify-content-center bg-light p-md-5 h-75">
		<form action="/account/save-form" method="post">
			<div class="form-group">
				<label for="number">계좌번호:</label>
				<input type="text" class="form-control" placeholder="Enter 계좌번호" id="number" name="number">
			</div>
			<div class="form-group">
				<label for="password">계좌비밀번호:</label>
				<input type="password" class="form-control" placeholder="Enter 입금계좌번호" id="password" name="password">
			</div>
			<button type="submit" class="btn btn-primary">계좌생성</button>
		</form>
	</div>
</div>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
