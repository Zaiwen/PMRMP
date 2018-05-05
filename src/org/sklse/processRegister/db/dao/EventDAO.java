package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.EventDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;
    private static final String INSERT = "insert into event(id,name,uri,description,annotation,param) " +
            "values(?,?,?,?,?,?) ";
    private static final String UPDATE = "update event set name = ?, uri = ?, description = ?,"
            + "annotation = ? param = ? where id = ?;";
    private static final String DELETE = "delete from event where id = ? ;";
    private static final String QUERY_ALL = "select * from event;";
    private static final String QUERY_BY_ID = "select * from event where id = ?;";

    public EventDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
        mycon = ac.getCon();
    }

    public long create(EventDTO dto) throws SQLException {
        long ret = -1;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, dto.getId());
        ps.setString(2, dto.getName());
        ps.setString(3, dto.getUri());
        ps.setString(4, dto.getDescription());
        ps.setString(5, dto.getAnnotation());
        ps.setString(6, dto.getParam());
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

    public boolean update(EventDTO dto) throws SQLException {
        boolean ret = false;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(UPDATE);
        ps.setString(1, dto.getName());
        ps.setString(2, dto.getUri());
        ps.setString(3, dto.getDescription());
        ps.setString(4, dto.getAnnotation());
        ps.setLong(5, dto.getId());
        ps.setString(6, dto.getParam());
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

    public List<EventDTO> queryAll() throws SQLException {
        List<EventDTO> ret = new ArrayList<EventDTO>();
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_ALL);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            EventDTO dto = new EventDTO();
            dto.setId(set.getLong("id"));
            dto.setDescription(set.getString("description"));
            dto.setName(set.getString("name"));
            dto.setUri(set.getString("uri"));
            dto.setAnnotation(set.getString("annotation"));
            dto.setParam(set.getString("param"));
            ret.add(dto);
        }
        ps.close();
        this.close();

        return ret;
    }

    public EventDTO queryById(long id) throws SQLException {
        EventDTO ret = null;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(QUERY_BY_ID);
        ps.setLong(1, id);
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            ret = new EventDTO();
            ret.setId(set.getLong("id"));
            ret.setDescription(set.getString("description"));
            ret.setName(set.getString("name"));
            ret.setUri(set.getString("uri"));
            ret.setAnnotation(set.getString("annotation"));
            ret.setParam(set.getString("param"));
        }
        ps.close();
        this.close();
        return ret;
    }

    public void close() {
        try {
            ac.closeDB();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("close DB failed in EventDao");
            e.printStackTrace();
        }
    }

}