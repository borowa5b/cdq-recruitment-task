package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.tasks;

public class TaskTableDefinition {

    public static final String TABLE_NAME = "task";

    public class Column {
        public static final String ID = TABLE_NAME + ".id";
        public static final String STAGE = TABLE_NAME + ".stage";
        public static final String PROGRESS = TABLE_NAME + ".progress";
        public static final String CREATION_DATE = TABLE_NAME + ".creation_date";
    }
}
