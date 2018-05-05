package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.ProcessDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessDAO {


    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;
    private static final String INSERT = "insert into process(id,uri,name,annotation,precondition,postcondition,param) " +
            "values(?,?,?,?,?,?,?) ";
    private static final String UPDATE = "update process set " +
            "uri = ?, " +
            "name = ?, " +
            "annotation = ? ,precondition = ?,postconditon = ?,param = ? ," +
            "where id = ?;";

    private static final String DELETE = "delete from process where id = ? ;";
    private static final String QUERY_ALL = "select * from process;";
    private static final String QUERY_BY_ID = "select * from process where id = ?;";

    public ProcessDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
        mycon = ac.getCon();
    }


    public static ProcessDAO instance = ProcessDAO.getInstance();

    public static ProcessDAO getInstance() {
        if (instance == null) {
            synchronized (ProcessDAO.class) {
                if (instance == null) {
                    try {
                        instance = new ProcessDAO();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public long create(ProcessDTO dto) throws SQLException {
        if (dto == null) {
            return -1;
        }
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, dto.getId());
        ps.setString(2, dto.getUri());
        ps.setString(3, dto.getName());
        ps.setLong(5, dto.getPreconditionid());
        ps.setLong(6, dto.getPostconditionid());
        if (dto.getAnnotation() != null && !dto.getAnnotation().isEmpty()) {
            ps.setString(4, dto.getAnnotation());
        } else ps.setString(4, "");
        ps.setString(7, dto.getParam());

        ps.execute();
        ps.close();
        this.close();
        return dto.getId();
    }

    public boolean update(ProcessDTO dto) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(UPDATE);
        ps.setString(1, dto.getUri());
        ps.setString(2, dto.getName());
        if (dto.getAnnotation() != null && !dto.getAnnotation().isEmpty()) {
            ps.setString(3, dto.getAnnotation());
        } else ps.setString(3, "");
        ps.setLong(4, dto.getId());
        ps.setLong(5, dto.getPreconditionid());
        ps.setLong(6, dto.getPostconditionid());
        ps.setString(7, dto.getParam());
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ps.close();
        this.close();
        return ret;
    }

    public boolean delete(long id) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(DELETE);
        ps.setLong(1, id);
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ps.close();

        this.close();

        return ret;
    }

    public List<ProcessDTO> queryAll() throws SQLException {
        List<ProcessDTO> ret = new ArrayList<ProcessDTO>();
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_ALL);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            ProcessDTO dto = new ProcessDTO();
            dto.setId(set.getLong("id"));
            dto.setName(set.getString("name"));
            dto.setUri(set.getString("uri"));
            dto.setAnnotation(set.getString("annotation"));
            dto.setPreconditionid(set.getLong("precondition"));
            dto.setPostconditionid(set.getLong("postcondition"));
            dto.setParam(set.getString("param"));
            ret.add(dto);
        }
        set.close();
        ps.close();
        this.close();

        return ret;
    }

    public ProcessDTO queryById(long id) throws SQLException {
        ProcessDTO ret = null;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_BY_ID);
        ps.setLong(1, id);
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            ret = new ProcessDTO();
            ret.setId(set.getLong("id"));
            ret.setName(set.getString("name"));
            ret.setUri(set.getString("uri"));
            ret.setAnnotation(set.getString("annotation"));
            ret.setPreconditionid(set.getLong("precondition"));
            ret.setPostconditionid(set.getLong("postcondition"));
            ret.setParam(set.getString("param"));
        }
        set.close();
        ps.close();
        this.close();

        return ret;
    }

    public void close() {
        try {
            ac.closeDB();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Close DB failed in ProcessDao");
            e.printStackTrace();
        }
    }
}