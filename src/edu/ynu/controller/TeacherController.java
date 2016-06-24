package edu.ynu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.TeacherService;
import edu.ynu.service.UserService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private UserService userService;

	@Autowired
	private TeacherService teacherService;


	@RequestMapping(value = "/history/completed",method = RequestMethod.GET)
	public List<PurchaseApplySubmit> listCompletedProjects(HttpServletRequest request,
														   Integer countPerPage,
														   Integer pageNum) throws Exception {
		String teacherId = (String) request.getAttribute("userId");
		return teacherService.listHistorySubmit(teacherId,countPerPage,pageNum);
	}

	@RequestMapping(value = "/history/completed/count",method = RequestMethod.GET)
	public Integer countCompletedProjects(HttpServletRequest request) throws Exception {
		String teacherId = (String) request.getAttribute("userId");
		return teacherService.countHistorySubmit(teacherId);
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

	@RequestMapping(value = "/PurchaseApplySheet/submit",method = RequestMethod.POST)
	public Integer PurchaseApplySheetSubmit(HttpServletRequest request,
											@RequestBody PurchaseApplySubmit purchaseApplySheet) throws Exception {
		String userId = (String) request.getAttribute("userId");
		teacherService.submitPurchaseApply(purchaseApplySheet,userId);
		return 1;
	}

	@RequestMapping(value = "/PurchaseApplySheet/submitDraft",method = RequestMethod.POST)
	public Integer PurchaseApplySheetSubmitDraft(HttpServletRequest request,
												 @RequestBody PurchaseApplySubmit submit) throws Exception {
		String userId = (String) request.getAttribute("userId");
		teacherService.saveDraftByUID(userId, submit);
		return 1;
	}

	@RequestMapping(value = "/PurchaseApplySheet/draft",method = RequestMethod.GET)
	public PurchaseApplySubmit getPurchaseApplySheetDraft(HttpServletRequest request) throws Exception {
		String userId = (String) request.getAttribute("userId");
		return teacherService.findDraftByUID(userId);
	}
	@RequestMapping(value = "/projects/{pid}",method=RequestMethod.GET)
	public PurchaseApplySubmit getById(@PathVariable String pid){
		return teacherService.findByPID(pid);
	}

	@RequestMapping(value = "/projects/handling/count",method = RequestMethod.GET)
	public Integer countHandlingProjects(HttpServletRequest request)throws Exception{
		String teacherId = (String) request.getAttribute("userId");
		return teacherService.countHandingProjects(teacherId);
	}
	@RequestMapping(value = "/projects/handling",method = RequestMethod.GET)
	public List<PurchaseApplySubmit> listHandlingProjects(HttpServletRequest request,
														  Integer countPerPage,
														  Integer pageNum)throws Exception{
		String teacherId = (String) request.getAttribute("userId");
		return teacherService.listHandlingProjects(teacherId,countPerPage,pageNum);
	}
}
