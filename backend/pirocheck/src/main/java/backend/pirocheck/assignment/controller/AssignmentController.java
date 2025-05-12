package backend.pirocheck.assignment.controller;

import backend.pirocheck.assignment.dto.request.AssignmentReq;
import backend.pirocheck.assignment.dto.response.AssignmentRes;
import backend.pirocheck.assignment.dto.response.AssignmentWeekRes;
import backend.pirocheck.assignment.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    // 과제 결과 확인 API
//    @GetMapping("/{userId}")
//    public List<AssignmentRes> getAssignment(@PathVariable("userId") Long userId) {
//        // 주차별 과제 제목, 요일별 과제 제목, 과제 상태 반환
//        return assignmentService.search(userId);
//    }
    // 과제 주차별, 요일별 그룹화 JSON
    @GetMapping("/grouped/{userId}")
    public List<AssignmentWeekRes> getGroupedAssignments(@PathVariable Long userId) {
        return assignmentService.search(userId);
    }

    // 과제 생성 API
    @PostMapping("/signup")
    public String signupAssignment(@RequestBody AssignmentReq assignmentReq) {
        return assignmentService.create(assignmentReq);
    }
}
