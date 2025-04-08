package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RestController
@Tag(name = "people")
@RequestMapping("/people")
@Retention(RetentionPolicy.RUNTIME)
public @interface PeopleEndpoint {
}
