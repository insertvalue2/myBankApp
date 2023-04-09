<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<div class="col-sm-8">
	<h2>입금 페이지(인증)</h2>
	<h6>어서오세요 환영합니다</h6>

	<div class="align-items-center justify-content-center bg-light p-md-5 h-75">
		<form action="/user/signUp" method="post">
			<div class="form-group">
				<label for="amount">입금금액:</label>
				<input type="text" class="form-control" placeholder="Enter 입금금액" id="amount" name="amount">
			</div>
			<div class="form-group">
				<label for="dAccountNumber">입금계좌번호:</label>
				<input type="text" class="form-control" placeholder="Enter 입금계좌번호" id="dAccountNumber" name="wAccountNumber">
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
</div>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
