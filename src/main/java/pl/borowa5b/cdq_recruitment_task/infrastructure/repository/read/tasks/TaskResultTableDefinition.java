package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.tasks;

public class TaskResultTableDefinition {

    public static final String TABLE_NAME = "task_result";

    public class Column {
        public static final String ID = TABLE_NAME + ".id";
        public static final String TASK_ID = TABLE_NAME + ".task_id";
        public static final String VALUE_BEFORE = TABLE_NAME + ".value_before";
        public static final String CURRENT_VALUE = TABLE_NAME + ".current_value";
        public static final String DISSIMILARITY = TABLE_NAME + ".dissimilarity";
        public static final String CLASSIFICATION = TABLE_NAME + ".classification";
    }
}
