package org.sklse.processRegister.db.dao;

import org.sklse.ontologyRegister.db.BaseDAO;
import org.sklse.ontologyRegister.db.DBConnector;
import org.sklse.processRegister.db.dto.ProcessElementDTO;
import org.sklse.processRegister.db.dto.ProcessElementDTO.ElementType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessElementDAO extends BaseDAO {

    public ProcessElementDAO(Connection conn) {
        super(conn);
    }

    public ProcessElementDAO() {
    }

    public long create(ProcessElementDTO dto) throws SQLException {
        long ret = -1;
        String sql = "insert into process_element(type) values(?) returning id;";

        ps = mycon.prepareStatement(sql);
        ps.setString(1, dto.getType().toString());
        boolean exec = ps.execute();
        if (exec) {
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                ret = rs.getLong(1);
            }
        }

        DBConnector.closeStatement(ps);


        return ret;
    }

    public boolean delete(long id) throws SQLException {
        boolean ret = false;
        String sql = "delete from process_element where id = ? ;";

        ps = mycon.prepareStatement(sql);
        ps.setLong(1, id);
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;

        DBConnector.closeStatement(ps);

        return ret;
    }

    public List<ProcessElementDTO> queryAll() throws SQLException {
        List<ProcessElementDTO> ret = new ArrayList<ProcessElementDTO>();
        String sql = "select * from process_element;";
        ps = mycon.prepareStatement(sql);
        set = ps.executeQuery();
        while (set.next()) {
            ProcessElementDTO dto = new ProcessElementDTO();
            dto.setId(set.getLong("id"));
            dto.setType(set.getString("type"));
            ret.add(dto);
        }

        DBConnector.closeStatement(ps);

        return ret;
    }

    public ProcessElementDTO queryById(long id) throws SQLException {
        ProcessElementDTO ret = null;
        String sql = "select * from process_element where id = ?;";

        ps = mycon.prepareStatement(sql);
        ps.setLong(1, id);
        set = ps.executeQuery();
        if (set.next()) {
            ret = new ProcessElementDTO();
            ret.setId(set.getLong("id"));
            ret.setType(set.getString("type"));
        }

        DBConnector.closeStatement(ps);

        return ret;
    }

    public List<ProcessElementDTO> queryByType(ElementType type) throws SQLException {
        List<ProcessElementDTO> ret = new ArrayList<ProcessElementDTO>();
        String sql = "select * from process_element where type = ?;";
        ps = mycon.prepareStatement(sql);
        ps.setString(1, type.toString());
        set = ps.executeQuery();
        while (set.next()) {
            ProcessElementDTO dto = new ProcessElementDTO();
            dto.setId(set.getLong("id"));
            dto.setType(set.getString("type"));
            ret.add(dto);
        }
        DBConnector.closeStatement(ps);

        return ret;
    }

}