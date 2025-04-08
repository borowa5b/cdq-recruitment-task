package pl.borowa5b.cdq_recruitment_task.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.PersonEntity;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.TaskEntity;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.TaskResultEntity;

@Component
class Database {

    @Autowired
    private TestPersonRepository personRepository;

    @Autowired
    private TestTaskRepository taskRepository;

    @Autowired
    private TestTaskResultRepository taskResultRepository;

    public void prepare() {
        personRepository.deleteAll();
        taskResultRepository.deleteAll();
        taskRepository.deleteAll();
    }
}


@Repository
interface TestPersonRepository extends JpaRepository<PersonEntity, String> {
}

@Repository
interface TestTaskResultRepository extends JpaRepository<TaskResultEntity, String> {
}

@Repository
interface TestTaskRepository extends JpaRepository<TaskEntity, String> {
}