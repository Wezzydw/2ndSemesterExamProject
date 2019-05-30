package pkg2ndsemesterexamproject.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Worker;

public class WorkerDAO {

    private DatabaseConnection conProvider;

    public WorkerDAO() throws IOException {
        conProvider = new DatabaseConnection();
    }

    /**
     * Metoden skaber forbindelse til databasen og henter de efterspurgte
     * informationer om workers ind i programmet og tilføjer Workers dertil.
     *
     * @return Listen af Workers hentet fra databasen.
     * @throws SQLException
     */
    public List<IWorker> getAllWorkers() throws SQLException {

        List<IWorker> allWorkers = new ArrayList();
        try (Connection con = conProvider.getConnection()) {
            String a = "SELECT * FROM Worker;";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String initials = rs.getString("initials");
                int salaryNumber = rs.getInt("salaryNumber");

                Worker worker = new Worker(name, initials, salaryNumber);
                allWorkers.add(worker);
            }
            prst.close();
        } catch (SQLException ex) {
            throw new SQLException("No data from getAllWorkers" + ex);
        }
        return allWorkers;
    }
}
