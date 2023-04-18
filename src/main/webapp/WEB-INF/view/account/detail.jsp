<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<%@ page import="com.tenco.bank.utils.TimestampUtil"%>


<style>
.user-box {
	border: 1px solid black;
	padding: 10px;
}

.table>thead {
	font-size: 12px;
	font-weight: bold;
	text-align: left;
}

.table>tbody {
	font-size: 12px;
	text-align: left;
}
</style>
<!-- flex 속성을 사용하고 싶다면 flex container 로 만들기 (d-flex)  -->
<div class="col-sm-8 d-flex flex-column ">
	<h2>계좌 상세보기(인증)</h2>
	<h6>어서오세요 환영합니다</h6>
	<div class="bg-light p-md-3 flex-grow-1"">
		<div class="user-box">
			${principal.username}님 계좌<br /> 계좌번호 : ${account.number}<br /> 잔액 : ${account.balance}원
		</div>
		<br>
		<div>
			<a href="/account/detail/${account.id}?type=all">전체</a> <a href="/account/detail/${account.id}?type=deposit">입금</a> <a href="/account/detail/${account.id}?type=withdraw">출금</a>
		</div>
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
				<c:forEach var="historyDto" items="${historyList}">
					<tr>
						<td>${historyDto.formatCreatedAt()}</td>
						<td>${historyDto.sender}</td>
						<td>${historyDto.receiver}</td>
						<td>${historyDto.amount}</td>
						<td>${historyDto.formatBalance()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
