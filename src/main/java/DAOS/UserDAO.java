package DAOS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Objects.User;
import Objects.UserLoanInformation;

public class UserDAO extends baseDAO {
	
	private String tablename = "public.user";

    private List<User> selectUsers (String query) {
        List<User> results = new ArrayList<User>();

        try (Connection con = super.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet dbResultSet = stmt.executeQuery(query);

            while (dbResultSet.next()) {
                int userId = dbResultSet.getInt("userid");
                String userType = dbResultSet.getString("usertype");
                String firstname = dbResultSet.getString("firstname");   
                String lastname = dbResultSet.getString("lastname");   
            	int phonenumber = dbResultSet.getInt("phonenumber");
            	String password = dbResultSet.getString("password");
            	String salt = dbResultSet.getString("salt");
            	String status = dbResultSet.getString("status");
            	Date DateOfBirth = dbResultSet.getDate("dateofbirth");
            	int addressIdFk = dbResultSet.getInt("addressidfk");
            	String photo = dbResultSet.getString("photo");
            	String username = dbResultSet.getString("username");
           
                User newUser = new User(userId, userType, firstname, lastname, phonenumber, password, salt, status, addressIdFk, photo, DateOfBirth, username);


                results.add(newUser);
            }
            stmt.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public List<User> findAll() { return selectUsers("SELECT * From " + tablename); }

    public User findById(int id) {
        List<User> results = selectUsers("SELECT * FROM " + tablename + " WHERE userid = " + id + "");

        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }
    
    public int findIdByName(String name){
    	List<User> results = selectUsers("SELECT * FROM " + tablename + " WHERE name = '" + name + "'");

        if (results.size() == 0) {
            return (Integer) null;
        } else {
            return results.get(0).getUserId();
        }
    }

    public User update(User user) {
        String query = "UPDATE " + tablename + " SET usertype = ?, firstname = ?, lastname = ? phonenumber = ?,"
        		+ " status = ?, dateofbirth = ?, photo = ?, addressidfk = ?, username = ?"
        		+ " WHERE userid = ?";

        try (Connection con = super.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getUserType());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastname());
            pstmt.setInt(5, user.getPhonenumber());
            pstmt.setString(6, user.getStatus());
            pstmt.setString(7, user.getPhoto());
            pstmt.setInt(8, user.getAddressIdFk());
            pstmt.setString(9, user.getUsername());
            pstmt.setInt(10, user.getUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findById(user.getUserId());
    }

    public boolean delete (int userId) {
        boolean result = false;

        if (findById(userId) != null) {
            String query = "DELETE FROM " + tablename + " WHERE userid = ?";

            try (Connection con = getConnection()) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, userId);

                if (pstmt.executeUpdate() == 1) {
                    result = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public User save(User user) {
        String query = "INSERT INTO " + tablename + "(usertype, firstname, lastname, phonenumber, password, salt, status, dateofbirth, photo, addressidfk, username) VALUES (?,?,?,?,?,?,?,?,?,?,?) RETURNING userid";
        int result;
        try (Connection con = super.getConnection()){
            PreparedStatement pstmt = con.prepareStatement(query);
            
            pstmt.setString(1, user.getUserType());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastname());
            pstmt.setInt(4, user.getPhonenumber());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getSalt());
            pstmt.setString(7, user.getStatus());
            pstmt.setDate(8, user.getDateOfBirth());
            pstmt.setString(9, user.getPhoto());
            pstmt.setInt(10, user.getAddressIdFk());
            pstmt.setString(11, user.getUsername());

            ResultSet dbResultSet = pstmt.executeQuery();
            if(dbResultSet.next()) {

               result =  dbResultSet.getInt("userid");
               user.setUserId(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findById(user.getUserId());
    }
    
    public String findRoleForNameAndPassword(String name, String password){
    	String role = null;    	
    	String query = "SELECT usertype FROM "+tablename+" WHERE username = ? AND password = ?";
    	System.out.println(query);
    	
    	try(Connection con = super.getConnection()){
    		PreparedStatement pstmt = con.prepareStatement(query);
    		pstmt.setString(1, name);
    		pstmt.setString(2, password);
    		ResultSet rs = pstmt.executeQuery();
    		
    		if(rs.next()){
    			role = rs.getString("usertype");
    			System.out.println(role);
    		}
    	} catch (SQLException sqle){
    		sqle.printStackTrace();
    	}
    	
    	return role;
    }
    
    public List<UserLoanInformation> getUserLoanInformation(int userId){
    	List<UserLoanInformation> result = new ArrayList<UserLoanInformation>();
    	String query = 	"SELECT g.loanofficeridfk, g.id as groupid, l.loanid" +
    					" FROM public.loan l, public.grouploan gl, public.group g" +
    					" WHERE l.useridfk = ? and l.loanid = gl.loanidfk and gl.groupidfk = g.id;";
    	
    	try(Connection con = super.getConnection()) {
    		PreparedStatement pstmt = con.prepareStatement(query);
    		pstmt.setInt(1, userId);
    		
    		ResultSet dbResultSet = pstmt.executeQuery();
    		
    		while (dbResultSet.next()) {
    			int loanOfficerId = dbResultSet.getInt("loanofficeridfk");
    			int groupId = dbResultSet.getInt("groupid");
    			int loanId = dbResultSet.getInt("loanid");
    			
    			UserLoanInformation userLoanInformation = new UserLoanInformation(loanOfficerId, groupId, loanId);
    			result.add(userLoanInformation);
    		}
    	}catch (SQLException e){
    		e.printStackTrace();
    	}
    	return result;
    }
}
