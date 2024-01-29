package org.example.service;

import org.example.domain.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplateService {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplateService(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static class StudentResultSetExtractorImpl implements ResultSetExtractor<List<Student>> {
        @Override
        public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                students.add(student);
            }
            return students;
        }
    }

    public List<Student> getStudents(String fullName) {
        // others
        String[] variables = fullName.split("-");

        String first_name = variables[0];
        String last_name = variables[1];
        // others
        String sql = "SELECT * FROM Student WHERE first_Name = '" + first_name + "' AND last_Name = '" + last_name + "'";
        return jdbcTemplate.query(sql, new StudentResultSetExtractorImpl());
    }

    public List<Student> getStudentsWithQuestionMarkNotation(String fullName) {
        // others
        String[] strings = fullName.split("-");

//        String first_name = strings[0];
//        String last_name = strings[1];
        // others
        String sql = "SELECT * FROM Student WHERE first_Name = ? AND last_Name = ?";
        return jdbcTemplate.query(sql, strings, new StudentResultSetExtractorImpl());
    }

    public List<Student> getStudentsWithNamedParameter(String fullName) {
        // others
        String[] variables = fullName.split("-");

        String first_name = variables[0];
        String last_name = variables[1];
        // others
        String sql = "SELECT * FROM Student WHERE first_Name = :firstName AND last_Name = :lastName";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("firstName", first_name)
                .addValue("lastName", last_name);

        return namedParameterJdbcTemplate.query(sql, namedParameters, new StudentResultSetExtractorImpl());
    }
}
