<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>translist</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/resources/JS/list.js"></script>
<link rel="stylesheet" href="../../../resources/CSS/list.css" type="text/css">
</head>
<body>
	<%@ include file="../header/header.jsp" %>
	<div id="content">
		<div id="middle">
			<p id="categorylist">교통</p>
			<table>
				<tr id="tr1">
					<td class="bno"><span></span></td>
					<td class="title"><span>제목</span></td>
					<td class="nick"><span>작성자</span></td>
					<td class="regdate"><span>작성일</span></td>
					<td class="cnt"><span>조회</span></td>
					<td class="good"><span>좋아요</span></td>
				</tr>
				<c:if test="${paging.cri.pageNum == 1 and (keyword == null or keyword == '')}">
					<!-- 매니저글 for문 시작 -->
					<c:forEach items="${manager}" var="managerlist">
						<tr id="notice">
							<td class="bno">공지</td>
							<td class="title"><a href="../detail/detail?bno=${managerlist.bno}">${managerlist.title}</a></td>
							<td class="nick">${managerlist.nick}</td>
							<td class="regdate">${managerlist.regdate}</td>
							<td class="cnt">${managerlist.cnt}</td>
							<td class="good">${managerlist.good}</td>
						</tr>
					</c:forEach>
					<!-- 매니저글 for문 끝 -->
				</c:if>
				<!--  게시글 목록 for문 시작 -->
				<c:forEach items="${list}" var="translist">
				<tr>
					<td class="bno">${translist.bno}</td>
					<td class="title"><a href="../detail/detail?bno=${translist.bno}">${translist.title}</a></td>
					<td class="nick">${translist.nick}</td>
					<td class="regdate">${translist.regdate}</td>
					<td class="cnt">${translist.cnt}</td>
					<td class="good">${translist.good}</td>
				</tr>
				</c:forEach>
				<!-- 게시글 for문 끝 -->
			</table>
			<c:if test="${paging.total == 0}">
				<div id="nodata">
					<p id="nodatah">등록된 게시글이 없습니다</p>
				</div>
			</c:if>
		</div>
		<div id="search">
			<div id="pagingArea">
				<!-- prev(이전)이 true이면 이전버튼 활성화 -->
				<c:if test="${paging.prev}">
					<a href="/list/translist?category=${paging.cri.category}&period=${paging.cri.period}&type=${paging.cri.type}&keyword=${paging.cri.keyword}&pageNum=${paging.startPage-1}&amount=${paging.cri.amount}" id="pageBtn">이전</a>
				</c:if>
		
				<!-- begin(1)이 end(10) 될 동안 반복(1이 10 될 동안 반복) -->
				<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="num">
					<a href="/list/translist?category=${paging.cri.category}&period=${paging.cri.period}&type=${paging.cri.type}&keyword=${paging.cri.keyword}&pageNum=${num}&amount=${paging.cri.amount}" id="pageNum">${num}</a>
				</c:forEach>
				
				<!-- next(다음)이 true이면 다음버튼 활성화 -->
				<c:if test="${paging.next}">
					<a href="/list/translist?category=${paging.cri.category}&period=${paging.cri.period}&type=${paging.cri.type}&keyword=${paging.cri.keyword}&pageNum=${paging.endPage+1}&amount=${paging.cri.amount}" id="pageBtn">다음</a>
				</c:if>
			</div>
			<form action="/list/translist" method="get" id="searchform">
				<div id=searchAll>
					<select class="search" name="period">
						<option value="total">전체기간</option>
						<option value="day">1일</option>
						<option value="week">1주</option>
						<option value="month">1개월</option>
						<option value="sixMonth">6개월</option>
						<option value="year">1년</option>
					</select>
					<select class="search" name="type">
						<option value="T">제목 </option>
						<option value="N">글작성자</option>
						<option value="TC">제목+게시글</option>
					</select>
					<input type="text" class="search" id="query" name="keyword" placeholder="검색어를 입력해주세요">
					<button type="button" class="search" id="searchbtn">검색</button>
				</div>
				<div>
					<input type="hidden" name="pageNum" value="${paging.cri.pageNum}">
					<input type="hidden" name="amount" value="${paging.cri.amount}">
					<input type="hidden" name="category" value="${paging.cri.category}">
				</div>
			</form>
		</div>
	</div>
	<%@ include file="../header/footer.jsp" %>
</body>
</html>