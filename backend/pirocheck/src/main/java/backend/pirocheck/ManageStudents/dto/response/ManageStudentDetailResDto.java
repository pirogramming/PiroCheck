package backend.pirocheck.ManageStudents.dto.response;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ManageStudentDetailResDto {

    private String name;
    private int deposit;
    private int defence; // 방어권
    private List<String> assignmentTitles; // 과제 제목 리스트
}
