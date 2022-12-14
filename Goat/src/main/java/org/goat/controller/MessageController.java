package org.goat.controller;

import java.util.ArrayList;

import org.goat.model.MessageVO;
import org.goat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {
	@Autowired
	MessageService ms;
	
	// 메세지 페이지(화면)
	@RequestMapping(value = "/message/msgBox", method = RequestMethod.GET)
	public String msgBox() {
		return "message/msgBox";
	}
	
	// 메세지 리스트
	@RequestMapping(value = "/message/Box/{user_id}/{pageNum}/{amount}/{box}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<MessageVO>> msgList(@PathVariable String user_id, @PathVariable int pageNum, @PathVariable int amount,@PathVariable String box){
		System.out.println(user_id);
		System.out.println(pageNum);
		System.out.println(amount);
		System.out.println(box);
		
		return new ResponseEntity<>(ms.msgList(user_id, pageNum, amount, box),HttpStatus.OK);
	}
	
	// getTotal
	@RequestMapping(value = "/message/getTotal/{user_id}/{box}", method=RequestMethod.GET)
	public ResponseEntity<Integer> getTotal(@PathVariable String user_id, @PathVariable String box){
		return new ResponseEntity<>(ms.getTotal(user_id, box),HttpStatus.OK);
	}
	
	// 메세지 보내기 페이지(화면)
	@RequestMapping(value = "/message/send", method = RequestMethod.GET)
	public String send(Model model, MessageVO mvo) {
		model.addAttribute("message", mvo);
		return "message/sendMsg";
	}
	
	// 메세지 보내기 페이지(insert 이루어짐)
	@RequestMapping(value = "/message/send", method=RequestMethod.POST)
	public ResponseEntity<String> msgSend(@RequestBody MessageVO mvo){
		// insert가 성공했으면 result변수에 1저장
		// insert가 실패했으면 result변수에 0저장	
		int result = ms.send(mvo);
		System.out.println(mvo);

		// result가 1이면 HttpStatus.OK
		// result가 0이면 HttpStatus.INTERNAL_SERVER_ERROR
		return result==1? new ResponseEntity<>("success",HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}

	// 받은 메세지 삭제(실제 삭제는 안 되고 DB에 recv_chk 를 1로 업데이트 한다)
	@RequestMapping(value = "/message/recvRemove", method=RequestMethod.PUT)
	public ResponseEntity<String> recvRemove(@RequestBody MessageVO mvo){
		int result = ms.recvRemove(mvo);
		
		// result가 1이면 HttpStatus.OK
		// result가 0이면 HttpStatus.INTERNAL_SERVER_ERROR
		return result==1? new ResponseEntity<>("success",HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	// 보낸 메세지 삭제(실제 삭제는 안 되고 DB에 send_chk 를 1로 업데이트 한다)
	@RequestMapping(value = "/message/sendRemove", method=RequestMethod.PUT)
	public ResponseEntity<String> sendRemove(@RequestBody MessageVO mvo){
		int result = ms.sendRemove(mvo);
		
		// result가 1이면 HttpStatus.OK
		// result가 0이면 HttpStatus.INTERNAL_SERVER_ERROR
		return result==1? new ResponseEntity<>("success",HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	// 새로운 메세지 확인(DB에 new_msg 를 1로 업데이트 한다)
	@RequestMapping(value = "/message/chkNewMsg", method=RequestMethod.PUT)
	public ResponseEntity<String> chkNewMsg(@RequestBody MessageVO mvo){
		int result = ms.chkNewMsg(mvo);
		
		// result가 1이면 HttpStatus.OK
		// result가 0이면 HttpStatus.INTERNAL_SERVER_ERROR
		return result==1? new ResponseEntity<>("success",HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}

	/*
	// 새로온 쪽지 페이지(화면)
	@RequestMapping(value = "/message/newMsgBox", method = RequestMethod.GET)
	public String newMsg(Model model, CriteriaVO cri) {
		int total=ms.newMsgTotal(cri);
		System.out.println(total);
		model.addAttribute("paging", new pageVO(cri, total));
		return "message/newMsgBox";
	}

	// 새로온 쪽지 리스트
	@RequestMapping(value = "/message/newBox/{recv_id}/{pageNum}/{amount}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<MessageVO>> newMsg(@PathVariable String recv_id, @PathVariable int pageNum, @PathVariable int amount){
		System.out.println("뉴 박스"+recv_id);	
		System.out.println("뉴 박스"+pageNum);	
		System.out.println("뉴 박스"+amount);	
		
		return new ResponseEntity<>(ms.newMsg(recv_id, pageNum, amount),HttpStatus.OK);
	}
	
	// 받은 쪽지 페이지(화면)
	@RequestMapping(value = "/message/recieveMsgBox", method = RequestMethod.GET)
	public String recieveMsg(Model model, CriteriaVO cri) {
		int total=ms.recieveMsgTotal(cri);
		System.out.println(total);
		
		model.addAttribute("paging", new pageVO(cri, total));
		return "message/recieveMsgBox";
	}
	
	// 받은 쪽지 리스트
	@RequestMapping(value = "/message/recieveBox/{recv_id}/{pageNum}/{amount}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<MessageVO>> recieveMsg(@PathVariable String recv_id, @PathVariable int pageNum, @PathVariable int amount){
		System.out.println("리시브 박스"+recv_id);
		System.out.println("리시브 박스"+pageNum);
		System.out.println("리시브 박스"+amount);
		
		return new ResponseEntity<>(ms.recieveMsg(recv_id, pageNum, amount),HttpStatus.OK);
	}
	
	// 보낸 쪽지 페이지(화면)
	@RequestMapping(value = "/message/sendMsgBox", method = RequestMethod.GET)
	public String sendMsg(Model model, CriteriaVO cri) {
		int total=ms.sendMsgTotal(cri);
		System.out.println(total);
		
		model.addAttribute("paging", new pageVO(cri, total));
		return "message/sendMsgBox";
	}
	
	// 보낸 쪽지 리스트
	@RequestMapping(value = "/message/sendBox/{send_id}/{pageNum}/{amount}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<MessageVO>> sendMsg(@PathVariable String send_id, @PathVariable int pageNum, @PathVariable int amount){
		System.out.println("센드 박스"+send_id);
		System.out.println("센드 박스"+pageNum);
		System.out.println("센드 박스"+amount);
		
		return new ResponseEntity<>(ms.sendMsg(send_id, pageNum, amount),HttpStatus.OK);
	}
	*/
}
