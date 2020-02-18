package space.nyuki.questionnaire.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.nyuki.questionnaire.factory.TransFactory;
import space.nyuki.questionnaire.group.GroupView;
import space.nyuki.questionnaire.pojo.Member;
import space.nyuki.questionnaire.pojo.TransData;
import space.nyuki.questionnaire.service.MemberService;
import space.nyuki.questionnaire.utils.FileDownloadUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired
	MemberService memberService;

	@GetMapping("{gid}")
	@JsonView(GroupView.View.class)
	public TransData getData(@PathVariable(name = "gid") String gid) {
		return TransFactory.getSuccessResponse(memberService.getData(gid));
	}

	@PostMapping("{gid}")
	public TransData addData(@PathVariable(name = "gid") String gid,
	                         @RequestHeader(name = "token") String token,
	                         @Validated(GroupView.Create.class)
	                         @RequestBody Member member,
	                         BindingResult result) {
		memberService.addData(gid, member, token);
		return TransFactory.getSuccessResponse();
	}

	@PutMapping("{gid}")
	public TransData updateData(
			@PathVariable(name = "gid") String gid,
			@RequestHeader(name = "token") String token,
			@Validated(GroupView.Update.class)
			@RequestBody Member member,
			BindingResult bindingResult) {
		memberService.updateData(gid, member, token);
		return TransFactory.getSuccessResponse();
	}

	@DeleteMapping("{gid}/{id}")
	public TransData delete(
			@PathVariable(name = "gid") String gid,
			@RequestHeader(name = "token") String token,
			@PathVariable(name = "id") String id) {
		memberService.deleteData(gid, id, token);
		return TransFactory.getSuccessResponse();
	}

	@SneakyThrows
	@GetMapping("template")
	public ResponseEntity<Resource> downloadTemplate(HttpServletRequest request) {
		Resource resource = memberService.getTemplateFile();
		return FileDownloadUtil.responseEntity(request, resource);
	}

	@SneakyThrows
	@GetMapping("export/{gid}")
	public ResponseEntity<Resource> exportMember(HttpServletRequest request, @PathVariable(name = "gid") String gid) {
		Resource resource = memberService.exportData(gid);
		return FileDownloadUtil.responseEntity(request, resource);
	}

	@PostMapping("upload/{id}")
	public TransData uploadData(
			@PathVariable(name = "id") String id,
			@RequestHeader(name = "token") String token,
			@RequestParam("file") MultipartFile file) {
		memberService.uploadData(id, token, file);
		return TransFactory.getSuccessResponse();
	}
}
