package edu.maebe.sql2omodel;

import edu.maebe.RandomUuidGenerator;
import edu.maebe.UuidGenerator;
import edu.maebe.model.Journal;
import edu.maebe.model.Model;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model {

    private Sql2o sql2o;
    private UuidGenerator uuidGenerator;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
        uuidGenerator = new RandomUuidGenerator();
    }

    @Override
    public UUID createJournal(String type, String value) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID journalUuid = uuidGenerator.generate();
            conn.createQuery("insert into journals(id, type, value, date) VALUES (:id, :type, :value, :date)")
                    .addParameter("id", journalUuid)
                    .addParameter("type", type)
                    .addParameter("value", value)
                    .addParameter("date", new Date())
                    .executeUpdate();
            conn.commit();
            return journalUuid;
        }
    }


    @Override
    public List<Journal> getAllJournals() {
        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery("select * from journals").executeAndFetch(Journal.class);
            return journals;
        }
    }



    @Override
    public boolean existJournal(UUID journal) {
        try (Connection conn = sql2o.open()) {
            List<Journal> journals = conn.createQuery("select * from journals where id=:journal")
                    .addParameter("journal", journal)
                    .executeAndFetch(Journal.class);
            return journals.size() > 0;
        }
    }

}