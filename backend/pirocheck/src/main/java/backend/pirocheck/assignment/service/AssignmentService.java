package backend.pirocheck.assignment.service;

import backend.pirocheck.assignment.dto.request.AssignmentReq;
import backend.pirocheck.assignment.dto.response.AssignmentDayRes;
import backend.pirocheck.assignment.dto.response.AssignmentDetailRes;
import backend.pirocheck.assignment.dto.response.AssignmentRes;
import backend.pirocheck.assignment.dto.response.AssignmentWeekRes;
import backend.pirocheck.assignment.entity.Assignment;
import backend.pirocheck.assignment.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    // 그룹화 되지 않은 JSON 형식
//    public List<AssignmentRes> search(Long userId) {
//        // 각 유저별 전체 과제 목록 조회 (userId를 어떻게 넘길 것인가?) search의 인자로 넘긴다...
//        List<Assignment> assignments = assignmentRepository.findByUserId(userId);
//
//        return assignments.stream()
//                .map(assignment -> new AssignmentRes(assignment.getAssignmentName(), assignment.getWeek(), assignment.getSection(), assignment.getOrderNumber(), assignment.getSubmitted()))
//                .toList();
//    }
    public List<AssignmentWeekRes> search(Long userId) {

        List<Assignment> assignments = assignmentRepository.findByUserId(userId);
        // week 기준으로 그룹화
        Map<Long, List<Assignment>> weekGroup = assignments.stream()
                .collect(Collectors.groupingBy(Assignment::getWeek)); // assignments를 week 별로 그룹핑

        List<AssignmentWeekRes> assignmentResponses = new ArrayList<>();

        for (Map.Entry<Long, List<Assignment>> entry : weekGroup.entrySet()) {
            Long week = entry.getKey();
            List<Assignment> assignmentList = entry.getValue();

            // day를 기준으로 그룹핑
            Map<Long, List<Assignment>> dayGroup = assignments.stream()
                    .collect(Collectors.groupingBy(Assignment::getSection));

            List<AssignmentDayRes> assignmentDayResList = new ArrayList<>();

            for (Map.Entry<Long, List<Assignment>> dayEntry : dayGroup.entrySet()) {
                Long day = dayEntry.getKey();
                List<Assignment> dayAssignmentList = dayEntry.getValue();

                // 세부 과제명과 과제 결과를 리스트 형태로
                List<AssignmentDetailRes> assignmentDetailResList = dayAssignmentList.stream()
                        .map(assignment -> new AssignmentDetailRes(
                                assignment.getAssignmentName(),
                                assignment.getSubmitted()
                        ))
                        .toList();
            }
        }

        return assignmentResponses;
    }

    public String create(AssignmentReq assignmentReq) {

        Assignment assignment = Assignment.create(
                assignmentReq.getAssignmentName(),
                assignmentReq.getWeek(),
                assignmentReq.getSection(),
                assignmentReq.getOrderNumber());

        assignment = assignmentRepository.save(assignment);

        return assignment.getAssignmentName();
    }
}
