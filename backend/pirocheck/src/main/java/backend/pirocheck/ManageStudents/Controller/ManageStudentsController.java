package backend.pirocheck.ManageStudents.Controller;

import backend.pirocheck.ManageStudents.dto.request.DefenceUpdateReqDto;
import backend.pirocheck.ManageStudents.dto.response.ManageStudentDetailResDto;
import backend.pirocheck.ManageStudents.dto.response.ManageStudentsListResDto;
import backend.pirocheck.ManageStudents.service.ManageStudentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "수강생 관리", description = "관리자가 수강생을 조회하고 방어권을 수정하는 API")
@RestController
@RequestMapping("/api/admin/managestudent")
@RequiredArgsConstructor
public class ManageStudentsController {

    private final ManageStudentsService manageStudentsService;

    // 수강생 리스트 조회
    @Operation(summary = "수강생 리스트 조회", description = "이름에 따라 수강생 리스트를 검색합니다.")
    @GetMapping("")
    public List<ManageStudentsListResDto> getStudents(@RequestParam(required = false) String name) {
        return manageStudentsService.searchMembers(name);
    }

    // 수강생 상세 조회
    @Operation(summary = "수강생 상세 조회", description = "studentId로 해당 수강생의 보증금, 방어권, 과제 정보를 조회합니다.")
    @GetMapping("/{studentId}")
    public ManageStudentDetailResDto getStudentDetail(@PathVariable Long studentId) {
        return manageStudentsService.getMemberDetail(studentId);
    }

    // 방어권 업데이트
    @Operation(summary = "방어권 수정", description = "studentId에 해당하는 수강생의 보증금 방어권 금액을 수정합니다.")
    @PatchMapping("/{studentId}/defence")
    public void updateDefence(@PathVariable Long studentId, @RequestBody DefenceUpdateReqDto req) {
        manageStudentsService.updateDefence(studentId, req.getDefence());
    }

}
