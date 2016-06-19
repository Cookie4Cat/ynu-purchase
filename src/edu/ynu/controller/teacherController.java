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

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.service.TeacherService;
import edu.ynu.service.UserService;

@RestController
@RequestMapping("/teacher")
public class teacherController {

	@Autowired
	private UserService userService;

	@Autowired
	private TeacherService teacherService;

	private final static int CountPerPage = 8;

	@RequestMapping("/history/unCompleted")
	public @ResponseBody List<PurchaseHistoryRecord> ing(HttpServletRequest request, Integer currentPage)
	        throws Exception {
		String userId = (String) request.getAttribute("userId");
		List<PurchaseHistoryRecord> findSubmitHistoryById = userService.findSubmitHistoryByIdUnCompleted(userId,
		        CountPerPage, currentPage);
		return findSubmitHistoryById;
	}

	@RequestMapping("/history/unCompletedPageCount")
	public @ResponseBody Integer ingPageCount(HttpServletRequest request) throws Exception {
		String userId = (String) request.getAttribute("userId");
		return teacherService.findSubmitHistoryByIdUnCompletedPageCount(userId);

	}

	@RequestMapping("/history/completed")
	public @ResponseBody List<PurchaseHistoryRecord> completed(HttpServletRequest request, Integer currentPage)
	        throws Exception {
		String userId = (String) request.getAttribute("userId");
		List<PurchaseHistoryRecord> findSubmitHistoryById = userService.findSubmitHistoryByIdCompleted(userId,
		        CountPerPage, currentPage);
		return findSubmitHistoryById;
	}

	@RequestMapping("/history/CompletedPageCount")
	public @ResponseBody Integer completedPageCount(HttpServletRequest request) throws Exception {
		String userId = (String) request.getAttribute("userId");
		return teacherService.findSubmitHistoryByIdCompletedPageCount(userId);
	}

	@RequestMapping("/PurchaseApplySheet/download")
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

	@RequestMapping("/PurchaseApplySheet/submit")
	public @ResponseBody Integer PurchaseApplySheetSubmit(HttpServletRequest request,
	        PurchaseApplySubmit purchaseApplySheet) throws Exception {
		String userId = (String) request.getAttribute("userId");
		return userService.submitPurchaseApply(userId, purchaseApplySheet);
	}

	@RequestMapping("/PurchaseApplySheet/submitDraft")
	public @ResponseBody Integer PurchaseApplySheetSubmitDraft(HttpServletRequest request,
	        PurchaseApplySubmit purchaseApplySheet) throws Exception {
		String userId = (String) request.getAttribute("userId");
		return userService.storePurchaseApplyDraft(userId, purchaseApplySheet);
	}

}
