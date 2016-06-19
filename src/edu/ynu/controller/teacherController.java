package edu.ynu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.service.UserService;

@RestController
@RequestMapping("/teacher")
public class teacherController {

	@Autowired
	private UserService userService;

	@RequestMapping("/history/unCompleted")
	public @ResponseBody List<PurchaseHistoryRecord> ing(HttpServletRequest request, Integer currentPage)
	        throws Exception {
		String userId = (String) request.getAttribute("userId");
		List<PurchaseHistoryRecord> findSubmitHistoryById = userService.findSubmitHistoryByIdUnCompleted(userId, 8,
		        currentPage);
		return findSubmitHistoryById;
	}

	@RequestMapping("/history/completed")
	public @ResponseBody List<PurchaseHistoryRecord> completed(HttpServletRequest request, Integer currentPage)
	        throws Exception {
		String userId = (String) request.getAttribute("userId");
		List<PurchaseHistoryRecord> findSubmitHistoryById = userService.findSubmitHistoryByIdCompleted(userId, 8,
		        currentPage);
		return findSubmitHistoryById;
	}

	@RequestMapping("/download/PurchaseApplySheet")
	public ResponseEntity<byte[]> downloadPurchaseApplySheet(HttpServletRequest request, String projectId)
	        throws Exception {
		Map<String, Object> applySheet = userService.downloadPurchaseApplySheet(projectId);
		String filename = (String) applySheet.get("filename");
		byte[] file = (byte[]) applySheet.get("file");
		String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(file, headers, HttpStatus.CREATED);
	}
	
	
//	@RequestMapping("/history/completed")
//	public @ResponseBody List<PurchaseHistoryRecord> completed(HttpServletRequest request, Integer currentPage)
//	        throws Exception {
//		String userId = (String) request.getAttribute("userId");
//		List<PurchaseHistoryRecord> findSubmitHistoryById = userService.findSubmitHistoryByIdCompleted(userId, 8,
//		        currentPage);
//		return findSubmitHistoryById;
//	}

}
