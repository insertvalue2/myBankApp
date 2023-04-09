<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<style>

.user-box {
	border: 1px solid black;
	padding: 10px;
}
.table>thead {
	font-size: 12px;
	font-weight: bold;
}
</style>
<div class="col-sm-8">
	<h2>계좌 상세보기(인증)</h2>
	<h6>어서오세요 환영합니다</h6>

	<div class="align-items-center justify-content-center bg-light p-md-3 h-75">
		<div class="user-box">
			fullname님 계좌<br /> 계좌번호 : 1111<br /> 잔액 : 1000원
		</div>
		<br>
		<div>
			<a href="#">전체</a> <a href="#">입금</a> <a href="#">출금</a>
		</div>
		<br>
		<table class="table">
			<thead>
				<tr>
					<th>날짜</th>
					<th>보낸이</th>
					<th>받은이</th>
					<th>입출금금액</th>
					<th>계좌잔액</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>2023.04.10</td>
					<td>ATM</td>
					<td>1111계좌</td>
					<td>1000원</td>
					<td>2000원</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
