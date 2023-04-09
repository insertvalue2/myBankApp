<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<div class="col-sm-8">
	<h2>회원 가입</h2>
	<h6>어서오세요 환영합니다</h6>
	<div class="align-items-center justify-content-center bg-light p-md-5 h-75">
		<form action="/user/signUp" method="post">
			<div class="form-group">
				<label for="username">userName:</label>
				<input type="text" class="form-control" placeholder="Enter username" id="username" name="username">
			</div>
			<div class="form-group">
				<label for="password">Password:</label>
				<input type="password" class="form-control" placeholder="Enter password" id="password" name="password">
			</div>
			<div class="form-group">
				<label for="fullname">fullName:</label>
				<input type="text" class="form-control" placeholder="Enter fullname" id="fullname" name="fullname">
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/view/layout/footer.jsp"%>