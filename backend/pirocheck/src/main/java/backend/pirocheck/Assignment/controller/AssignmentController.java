package backend.pirocheck.Assignment.controller;

import backend.pirocheck.Assignment.dto.request.AssignmentCreateReq;
import backend.pirocheck.Assignment.dto.request.AssignmentUpdateReq;
import backend.pirocheck.Assignment.dto.response.AssignmentWeekRes;
import backend.pirocheck.Assignment.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    // 과제 결과 확인 API
    // 과제 주차별, 요일별 그룹화 JSON
    @GetMapping("/assignment/{userId}")
    public List<AssignmentWeekRes> getGroupedAssignments(@PathVariable Long userId) {
        return assignmentService.search(userId);
    }

    // 과제 생성 API
    @PostMapping("/admin/assignment/signup")
    public String signupAssignment(@RequestBody AssignmentCreateReq assignmentCreateReq) {
        return assignmentService.createAssignment(assignmentCreateReq);
    }

    // 과제 삭제 API
    @DeleteMapping("/admin/assignment/{assignmentId}")
    public String deleteAssignment(
            @PathVariable Long assignmentId
    ) {
        return assignmentService.deleteAssignment(assignmentId);
    }

    // 과제 수정 API
    @PutMapping("/admin/assignment/{assignmentId}")
    public String updateAssignment(
            @PathVariable("assignmentId") Long assignmentId,
            @RequestBody AssignmentUpdateReq assignmentUpdateReq
    ) {
        return assignmentService.updateAssignment(assignmentId, assignmentUpdateReq);
    }

    // 사용자별 과제 제출 여부 생성 API
    @PostMapping("/admin/users/{userId}/assignments/{assignmentId}/submission")
    public String submissionAssignment(@PathVariable Long userId, @PathVariable Long assignmentId) {
        return null;
    }

    // 사용자별 과제 제출 여부 수정 API
    @PutMapping("/api/users/{userId}/assignments/{assignmentId}/submission")
    public String updateSubmission(@PathVariable Long userId, @PathVariable Long assignmentId) {
        return null;
    }

}
