package backend.pirocheck.Assignment.controller;

import backend.pirocheck.Assignment.dto.request.AssignmentCreateReq;
import backend.pirocheck.Assignment.dto.request.AssignmentUpdateReq;
import backend.pirocheck.Assignment.dto.response.AssignmentWeekRes;
import backend.pirocheck.Assignment.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "과제관리", description = "과제 관련 API")
public class AssignmentController {

    private final AssignmentService assignmentService;

    // 과제 결과 확인 API
    // 과제 주차별, 요일별 그룹화 JSON
    @Operation(summary = "학생별 과제 결과 확인", description = "관리자가 채점한 과제의 결과를 학생들이 확인합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "사용자별 과제 조회에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @GetMapping("/assignment/{userId}")
    public List<AssignmentWeekRes> getGroupedAssignments(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId
    ) {
        return assignmentService.search(userId);
    }

    // 과제 생성 API
    @Operation(summary = "과제 생성 API", description = "관리자가 과제를 생성합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "과제 생성에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @PostMapping("/admin/assignment/signup")
    public String signupAssignment(
            @Valid
            @RequestBody AssignmentCreateReq assignmentCreateReq
    ) {
        return assignmentService.createAssignment(assignmentCreateReq);
    }

    // 과제 삭제 API
    @Operation(summary = "과제 삭제 API", description = "관리자가 과제를 삭제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "과제 삭제에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @DeleteMapping("/admin/assignment/{assignmentId}")
    public String deleteAssignment(
            @Parameter(description = "과제 ID", example = "1")
            @PathVariable Long assignmentId
    ) {
        return assignmentService.deleteAssignment(assignmentId);
    }

    // 과제 수정 API
    @Operation(summary = "과제 수정 API", description = "관리자가 과제의 잘못된 부분을 수정합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "과제 수정에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @PutMapping("/admin/assignment/{assignmentId}")
    public String updateAssignment(
            @Parameter(description = "과제 ID", example = "1")
            @PathVariable("assignmentId") Long assignmentId,
            @RequestBody AssignmentUpdateReq assignmentUpdateReq
    ) {
        return assignmentService.updateAssignment(assignmentId, assignmentUpdateReq);
    }

    // 사용자별 과제 제출 결과 생성 API
    @Operation(summary = "관리자 과제 채점 API", description = "관리자가 사용자들의 과제를 채점한 결과 저장합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "사용자의 과제 채점 결과 저장에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @PostMapping("/admin/users/{userId}/assignments/{assignmentId}/submission")
    public String submissionAssignment(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "과제 ID", example = "1")
            @PathVariable Long assignmentId
    ) {
        return null;
    }

    // 사용자별 과제 제출 여부 수정 API
    @Operation(summary = "관리자 과제 채점 내용 수정 API", description = "관리자가 사용자의 과제 결과를 수정하여 저장합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "사용자 과제 채점 결과 수정에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @PutMapping("/admin/users/{userId}/assignments/{assignmentId}/submission")
    public String updateSubmission(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "과제 ID", example = "1")
            @PathVariable Long assignmentId
    ) {
        return null;
    }

}
