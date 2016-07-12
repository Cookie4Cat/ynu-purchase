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

	//下载文件，暂时没用到
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

	//提交项目表单
	@RequestMapping(value = "/PurchaseApplySheet/submit",method = RequestMethod.POST)
	public Integer PurchaseApplySheetSubmit(HttpServletRequest request,
											@RequestBody PurchaseApplySubmit purchaseApplySheet) throws Exception {
		String userId = (String) request.getAttribute("userId");
		teacherService.submitPurchaseApply(purchaseApplySheet,userId);
		return 1;
	}

	//提交草稿
	@RequestMapping(value = "/PurchaseApplySheet/submitDraft",method = RequestMethod.POST)
	public Integer PurchaseApplySheetSubmitDraft(HttpServletRequest request,
												 @RequestBody PurchaseApplySubmit submit) throws Exception {
		String userId = (String) request.getAttribute("userId");
		teacherService.saveDraftByUID(userId, submit);
		return 1;
	}

	//返回草稿
	@RequestMapping(value = "/PurchaseApplySheet/draft",method = RequestMethod.GET)
	public PurchaseApplySubmit getPurchaseApplySheetDraft(HttpServletRequest request) throws Exception {
		String userId = (String) request.getAttribute("userId");
		return teacherService.findDraftByUID(userId);
	}

	//获取项目信息
	@RequestMapping(value = "/projects/{pid}",method=RequestMethod.GET)
	public PurchaseApplySubmit getById(@PathVariable String pid){
		return teacherService.findByPID(pid);
	}

	//更新（其实是新建）项目信息
	@RequestMapping(value = "/projects/{pid}",method=RequestMethod.POST)
	public Integer updatePurchaseApply(@RequestBody PurchaseApplySubmit submit,@PathVariable String pid){
		System.out.print(submit);
		teacherService.updatePurchaseApply(submit,pid);
		return 1;
	}

	//返回待处理列表
	@RequestMapping(value = "/projects/handling/all",method = RequestMethod.GET)
	public List<PurchaseApplySubmit> listAllHandlingProjects(HttpServletRequest request)throws Exception{
		String teacherId = (String) request.getAttribute("userId");
		return teacherService.listAllHandlingProjects(teacherId);
	}

	//返回历史列表
	@RequestMapping(value = "/projects/history/all",method = RequestMethod.GET)
	public List<PurchaseApplySubmit> listAllHistoryProjects(HttpServletRequest request)throws Exception{
		String teacherId = (String) request.getAttribute("userId");
		return teacherService.listAllHistoryProjects(teacherId);
	}
}
