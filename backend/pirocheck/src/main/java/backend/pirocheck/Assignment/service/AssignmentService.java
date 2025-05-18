package backend.pirocheck.Assignment.service;

import backend.pirocheck.Assignment.dto.request.AssignmentReq;
import backend.pirocheck.Assignment.dto.response.AssignmentDayRes;
import backend.pirocheck.Assignment.dto.response.AssignmentDetailRes;
import backend.pirocheck.Assignment.dto.response.AssignmentWeekRes;
import backend.pirocheck.Assignment.entity.Assignment;
import backend.pirocheck.Assignment.entity.AssignmentItem;
import backend.pirocheck.Assignment.repository.AssignmentItemRepository;
import backend.pirocheck.Assignment.repository.AssignmentRepository;
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

    private final AssignmentItemRepository assignmentItemRepository;
    private final AssignmentRepository assignmentRepository;

    public List<AssignmentWeekRes> search(Long userId) {

        List<AssignmentItem> assignments = assignmentItemRepository.findByUserId(userId);
        // week 기준으로 그룹화
        Map<Long, List<AssignmentItem>> weekGroup = assignments.stream()
                .collect(Collectors.groupingBy(item -> item.getAssignment().getWeek())); // assignmentItems를 week 별로 그룹핑

        List<AssignmentWeekRes> assignmentResponses = new ArrayList<>();

        for (Map.Entry<Long, List<AssignmentItem>> entry : weekGroup.entrySet()) {
            Long week = entry.getKey();
            List<AssignmentItem> assignmentList = entry.getValue();

            // day를 기준으로 그룹핑
            Map<String, List<AssignmentItem>> dayGroup = assignmentList.stream()
                    .collect(Collectors.groupingBy(item -> item.getAssignment().getDay()));

            List<AssignmentDayRes> assignmentDayResList = new ArrayList<>();

            for (Map.Entry<String, List<AssignmentItem>> dayEntry : dayGroup.entrySet()) {
                String day = dayEntry.getKey();
                List<AssignmentItem> dayAssignmentList = dayEntry.getValue();

                // 세부 과제명과 과제 결과를 리스트 형태로
                List<AssignmentDetailRes> assignmentDetailResList = dayAssignmentList.stream()
                        .map(assignmentItem -> new AssignmentDetailRes(
                                assignmentItem.getAssignment().getAssignmentName(),
                                assignmentItem.getSubmitted()
                        ))
                        .toList();
                assignmentDayResList.add(new AssignmentDayRes(day, assignmentDetailResList));
            }

            assignmentResponses.add(new AssignmentWeekRes(week, assignmentDayResList));
        }

        return assignmentResponses;
    }

    public String create(AssignmentReq assignmentReq) {

        Assignment assignment = Assignment.create(
                assignmentReq.getAssignmentName(),
                assignmentReq.getWeek(),
                assignmentReq.getDay(),
                assignmentReq.getOrderNumber());

        assignment = assignmentRepository.save(assignment);

        return assignment.getAssignmentName();
    }
}
