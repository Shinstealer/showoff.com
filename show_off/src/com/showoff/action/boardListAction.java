package com.showoff.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.showoff.dao.BoardDAO;
import com.showoff.dto.BoardDTO;
import com.showoff.dto.CriteriaDTO;
import com.showoff.dto.PageMakerDTO;

public class boardListAction implements Action {

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Criteria는 맨위에 있어야함
		CriteriaDTO criDto = new CriteriaDTO();
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("페이지번호 : " + page);
		criDto.setPage(page);
		
		String url = "board/board_list.jsp";
		
		BoardDAO dao = BoardDAO.getInstance();
		//게시물 목록
		List<BoardDTO> boardList = dao.boardListAll(criDto);
		
		request.setAttribute("boardList", boardList);
		
		//현재날짜 출력
		Date today = new Date();
		request.setAttribute("today", today);
		
		
		
		PageMakerDTO pageMaker = new PageMakerDTO();
		pageMaker.setCriDto(criDto);
		
		int totalcount = dao.totalCount(criDto);
		pageMaker.setTotalCount(totalcount);
		request.setAttribute("pageMaker", pageMaker);
		
		System.out.println(pageMaker.toString());
		
		ActionForward forward = new ActionForward();
		forward.setPath(url);
		forward.setRedirect(false);
		
		return forward;
	}

}
