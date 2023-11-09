package dao;

import core.DB;
import core.Utility;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    private final Connection connection;

    public UserDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                userList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    public ArrayList<User> searchList(String name, String email, String role) {
        ArrayList<User> userList = new ArrayList<>();
        String[] searchCriteria = {"name", name, "email", email, "role", role};

        String query = Utility.createSearchQuery("user", searchCriteria, "AND");

        User obj;

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                userList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    public boolean add(User user) {
        String query = "INSERT INTO user (name, email, pass, role) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPass());
            ps.setString(4, user.getRole());

            int response = ps.executeUpdate();

            if (response == -1) {
                Utility.showMessage("error");
            }
            ps.close();

            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public ArrayList<User> getListByRole(String role) {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user WHERE role = '" + role + "'";
        User obj;

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                userList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }



    public User findByLogin(String email, String pass) {
        User obj = null;
        String query = "SELECT * FROM user WHERE email = ? AND pass = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public User findByEmail(String email) {
        User obj = null;
        String query = "SELECT * FROM user WHERE email = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public User findById(int id) {
        User obj = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public boolean update(User user) {
        String query = "UPDATE user SET name = ?, email = ?, pass = ?, role = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPass());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getId());

            int response = ps.executeUpdate();

            if (response == -1) {
                Utility.showMessage("error");
            }
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM user WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }



    public ArrayList<User> search(String name, String email, String role) {
        ArrayList<User> userList = new ArrayList<>();
        String[] searchCriteria = {"name", name, "email", email, "role", role};

        String query = Utility.createSearchQuery("user", searchCriteria, "AND");

        User obj;

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                userList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setPass(rs.getString("pass"));
        obj.setRole(rs.getString("role"));
        return obj;
    }
}
