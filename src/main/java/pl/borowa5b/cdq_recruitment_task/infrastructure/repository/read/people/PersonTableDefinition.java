package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.people;

public class PersonTableDefinition {

    public static final String TABLE_NAME = "person";

    public class Column {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String BIRTH_DATE = "birth_date";
        public static final String COMPANY = "company";
    }
}
