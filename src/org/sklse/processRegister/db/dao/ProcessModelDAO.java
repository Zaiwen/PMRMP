package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.ProcessModelDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessModelDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;

    public ProcessModelDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
        mycon = ac.getCon();
    }

    public static ProcessModelDAO instance;

    public static ProcessModelDAO getInstance() {
        if (instance == null) {
            synchronized (ProcessDAO.class) {
                try {
                    instance = new ProcessModelDAO();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    public void setInstance(ProcessModelDAO instance) {
        this.instance = instance;
    }

    public long create(ProcessModelDTO dto) throws SQLException {
        long ret = -1;
        String sql = "insert into process_model(name,uri,language,pid,annotation,id,epcid,version,field,author,createTime) "
                + "values(?,?,?,?,?,?,?,?,?,?,?) ";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);

        ps.setString(1, dto.getName());

        if (dto.getUri() != null && !dto.getUri().isEmpty()) {
            ps.setString(2, dto.getUri());
        } else ps.setString(2, "");

        if (dto.getLanguage() != null)
            ps.setString(3, dto.getLanguage().toString());
        else ps.setString(3, "");

        if (dto.getPid() > 0) {
            ps.setLong(4, dto.getPid());
        } else ps.setNull(4, java.sql.Types.BIGINT);

        if (dto.getAnnotation() != null && !dto.getAnnotation().isEmpty()) {
            ps.setString(5, dto.getAnnotation());
        } else ps.setString(5, "");

        ps.setLong(6, dto.getId());
        ps.setLong(7, dto.getEpcid());
        ps.setLong(8, dto.getVersion());
        ps.setInt(9, dto.getField());
        ps.setDate(11, dto.getCreateTime());
        ps.setLong(10, dto.getAuthor());
        boolean exec = ps.execute();
        if (exec) {
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                ret = rs.getLong(1);
            }
        }
        ps.close();
        this.close();


        return ret;
    }

    public boolean delete(long id) throws SQLException {
        boolean ret = false;
        String sql = "delete from process_model where id = ? ;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, id);
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;
        ps.close();
        this.close();

        return ret;
    }

    public List<ProcessModelDTO> queryAll() throws SQLException {
        List<ProcessModelDTO> ret = new ArrayList<ProcessModelDTO>();
        String sql = "select * from process_model;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            ProcessModelDTO dto = new ProcessModelDTO();
            dto.setId(set.getLong("id"));
            dto.setName(set.getString("name"));
            dto.setUri(set.getString("uri"));
            dto.setLanguage(set.getString("language"));
            dto.setPid(set.getLong("pid"));
            dto.setAnnotation(set.getString("annotation"));
            dto.setAuthor(set.getLong("author"));
            dto.setEpcid(set.getLong("epcid"));
            dto.setField(set.getInt("field"));
            dto.setVersion(set.getLong("version"));
            dto.setCreateTime(set.getDate("createTime"));
            ret.add(dto);
        }
        set.close();
        ps.close();
        this.close();

        return ret;
    }

    public ProcessModelDTO queryById(long id) throws SQLException {
        ProcessModelDTO ret = null;
        String sql = "select * from process_model where id = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            ret = new ProcessModelDTO();
            ret.setId(set.getLong("id"));
            ret.setName(set.getString("name"));
            ret.setUri(set.getString("uri"));
            ret.setLanguage(set.getString("language"));
            ret.setPid(set.getLong("pid"));
            ret.setAnnotation(set.getString("annotation"));
        }
        set.close();
        ps.close();
        this.close();

        return ret;
    }

    public ProcessModelDTO queryByPid(long pid) throws SQLException {
        List<ProcessModelDTO> ret = new ArrayList<ProcessModelDTO>();
        String sql = "select * from process_model where pid = ?;";
        mycon = ac.getCon();
        ProcessModelDTO pm = null;
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, pid);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            pm = new ProcessModelDTO();
            pm.setId(set.getLong("id"));
            pm.setName(set.getString("name"));
            pm.setUri(set.getString("uri"));
            pm.setLanguage(set.getString("language"));
            pm.setPid(set.getLong("pid"));
            pm.setAnnotation(set.getString("annotation"));

        }
        set.close();
        ps.close();
        this.close();
        return pm;

    }

    public void close() throws SQLException {
        ac.closeDB();
    }

}