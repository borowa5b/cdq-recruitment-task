package pl.borowa5b.cdq_recruitment_task.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
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
interface TestPersonRepository extends MongoRepository<PersonEntity, String> {
}

@Repository
interface TestTaskResultRepository extends MongoRepository<TaskResultEntity, String> {
}

@Repository
interface TestTaskRepository extends MongoRepository<TaskEntity, String> {
}