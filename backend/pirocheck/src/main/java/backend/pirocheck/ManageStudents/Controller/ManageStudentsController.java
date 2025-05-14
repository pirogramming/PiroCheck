package backend.pirocheck.ManageStudents.Controller;

import backend.pirocheck.ManageStudents.dto.request.ShieldUpdateReqDto;
import backend.pirocheck.ManageStudents.dto.response.ManageStudentDetailResDto;
import backend.pirocheck.ManageStudents.dto.response.ManageStudentsListResDto;
import backend.pirocheck.ManageStudents.service.ManageStudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/managestudent")
@RequiredArgsConstructor
public class ManageStudentsController {

    private final ManageStudentsService manageStudentsService;

    // 수강생 리스트 조회
    @GetMapping("")
    public List<ManageStudentsListResDto> getStudents(@RequestParam(required = false) String name) {
        return manageStudentsService.searchMembers(name);
    }

    // 수강생 상세 조회
    @GetMapping("/{studentId}")
    public ManageStudentDetailResDto getStudentDetail(@PathVariable Long studentId) {
        return manageStudentsService.getMemberDetail(studentId);
    }

    // 방어권 업데이트
    @PatchMapping("/{studentId}/shield")
    public void updateShield(@PathVariable Long studentId, @RequestBody ShieldUpdateReqDto req) {
        manageStudentsService.updateShield(studentId, req.getShield());
    }

}
