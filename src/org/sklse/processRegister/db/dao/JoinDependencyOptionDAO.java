package org.sklse.processRegister.db.dao;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import databaseaccess.Access;
import org.sklse.processRegister.db.dto.JoinDependencyOptionDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoinDependencyOptionDAO  {

	private Access ac;
	private Connection mycon = null;
    private PreparedStatement ps = null;
    private static final  String INSERT = "insert join_dependency_option() "
            + "values(?,?,?,?,?)";
    private static final String DELETE = "delete from join_dependency_option where id = ? ;";
    private static final String  QUERY_ALL = "select * from join_dependency_option;";
    private static final String QUERY_BY_ID = "select * from join_dependency_option where id = ?;";
    private static final String UPDATE = "update  join_dependency_option set preceding = ? ,join_id = ? , condition = ? where id = ? ";
    public JoinDependencyOptionDAO() throws Exception {
    	 ac = new Access();
         ac.connDB("bpep");
         mycon = ac.getCon();
    }

    public boolean create(JoinDependencyOptionDTO dto) {
        boolean ret = false;
       

        try {
            mycon = ac.getCon();
            ps = mycon.prepareStatement(INSERT);
            ps.setLong(1, dto.getId());
            ps.setLong(3, dto.getPreceding());
            ps.setString(2, dto.getCondition());
            ps.setLong(4, dto.getJoinid());
            ps.setString(5, dto.getParam());
            ps.execute();
            ps.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(">>> JoinNodesDAO INSert WARNING : "
                    + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }

        return ret;
    }

    
    public boolean delete(long id) {
        boolean ret = false;   
        try {
            mycon = ac.getCon();
            ps = mycon.prepareStatement(DELETE);
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 1)
                ret = true;
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	this.close();
        }
        return ret;
    }

    public List<JoinDependencyOptionDTO> queryAll() {
        List<JoinDependencyOptionDTO> ret = new ArrayList<JoinDependencyOptionDTO>();
      
        try {
            mycon = ac.getCon();
            ps = mycon.prepareStatement(QUERY_ALL);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                JoinDependencyOptionDTO dto = new JoinDependencyOptionDTO();
                dto.setJoinid(set.getLong("join_id"));
                dto.setPreceding(set.getLong("preceding"));
                dto.setCondition(set.getString("condition"));
                dto.setId(set.getLong("id"));
                dto.setParam(set.getString("param"));
                ret.add(dto);
            }
            set.close();
            ps.close();
        } catch (SQLException e) {
        	System.out.println("Query ALL failed in JoinDependencyOptionDAO");
            e.printStackTrace();
        } finally {
            this.close();
        }
        return ret;
    }

    public JoinDependencyOptionDTO queryById(long id) {
        try {
            mycon = ac.getCon();
        	JoinDependencyOptionDTO dto = null;
            ps = mycon.prepareStatement(QUERY_BY_ID);
            ps.setLong(1, id);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                dto = new JoinDependencyOptionDTO();
                dto.setJoinid(set.getLong("join_id"));
                dto.setPreceding(set.getLong("preceding"));
                dto.setCondition(set.getString("condition"));
                dto.setId(set.getLong("id"));
                dto.setParam(set.getString("param"));
                
            }
            set.close();
            ps.close();
            return dto;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("QUERY_BY_ID FAILED IN JOINDEPENDENCYOPTION");
        } finally {
            this.close();
        }
        return null;
    }

    public boolean update(JoinDependencyOptionDTO dto, ProcessDTO processDTO) {
        boolean ret = false;
       

        try {
            mycon = ac.getCon();
            ps = mycon.prepareStatement(UPDATE);
            ps.setLong(1, dto.getPreceding());
            ps.setLong(2, dto.getJoinid());
            ps.setString(3, dto.getCondition());
            ps.setLong(4, processDTO.getId());
            ps.setString(5, dto.getParam());
            int rows = ps.executeUpdate();
            if (rows == 1)
                ret = true;
           
            ps.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(">>> JoinNodesDAO UPDATE WARNING : "
                    + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
          this.close();
        }

        return ret;
    }
    
    public void close(){
    	try {
			ac.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}