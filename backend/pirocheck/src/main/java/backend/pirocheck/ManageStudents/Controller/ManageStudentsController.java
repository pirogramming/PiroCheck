package backend.pirocheck.ManageStudents.Controller;

import backend.pirocheck.ManageStudents.dto.response.ManageStudentsListResDto;
import backend.pirocheck.ManageStudents.service.ManageStudentsService;
import backend.pirocheck.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/managestudent")
@RequiredArgsConstructor
public class ManageStudentsController {

    private final ManageStudentsService manageStudentsService;

    @GetMapping
    public List<ManageStudentsListResDto> getStudents(@RequestParam(required = false) String name) {
        return manageStudentsService.searchStudents(name);
    }

}
