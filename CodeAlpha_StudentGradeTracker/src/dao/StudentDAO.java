package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Student;
import util.DBConnection;

public class StudentDAO {

    public void insertStudent(Student student) {

        String sql = "INSERT INTO student " +
                     "(name, department, year, semester, subject1, subject2, subject3, score) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getDepartment());
            ps.setInt(3, student.getYear());
            ps.setInt(4, student.getSemester());
            ps.setInt(5, student.getSubject1());
            ps.setInt(6, student.getSubject2());
            ps.setInt(7, student.getSubject3());
            ps.setDouble(8, student.getScore());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student getStudentById(int id) {

        String sql = "SELECT * FROM student WHERE id = ?";
        Student student = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                student.setYear(rs.getInt("year"));
                student.setSemester(rs.getInt("semester"));
                student.setSubject1(rs.getInt("subject1"));
                student.setSubject2(rs.getInt("subject2"));
                student.setSubject3(rs.getInt("subject3"));
                student.setScore(rs.getDouble("score"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public void updateStudent(Student student) {

        String sql = "UPDATE student SET name=?, department=?, year=?, semester=?, " +
                     "subject1=?, subject2=?, subject3=?, score=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getDepartment());
            ps.setInt(3, student.getYear());
            ps.setInt(4, student.getSemester());
            ps.setInt(5, student.getSubject1());
            ps.setInt(6, student.getSubject2());
            ps.setInt(7, student.getSubject3());
            ps.setDouble(8, student.getScore());
            ps.setInt(9, student.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {

        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double[] getSummary() {

        double[] summary = new double[3];

        String sql = "SELECT AVG(score), MAX(score), MIN(score) FROM student";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                summary[0] = rs.getDouble(1);
                summary[1] = rs.getDouble(2);
                summary[2] = rs.getDouble(3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return summary;
    }

    public int getTotalStudents() {

        String sql = "SELECT COUNT(*) FROM student";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean hasStudents() {

        String sql = "SELECT 1 FROM student LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
