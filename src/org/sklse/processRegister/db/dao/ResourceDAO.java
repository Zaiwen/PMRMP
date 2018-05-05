package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.ontologyRegister.db.BaseDAO;
import org.sklse.ontologyRegister.db.DBConnector;
import org.sklse.processRegister.db.dto.ResourceDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResourceDAO extends BaseDAO {

    private Access ac;
    private Connection mycon = null;
    private PreparedStatement ps = null;

    public ResourceDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
        mycon = ac.getCon();
    }

    public long create(ResourceDTO dto) throws SQLException {
        long ret = -1;
        String sql = "insert into resource(name,uri,description,annotation,param) " +
                "values(?,?,?,?,?) returning id;";
        mycon = ac.getCon();

        ps = mycon.prepareStatement(sql);
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            ps.setString(1, dto.getName());
        } else ps.setString(1, "");
        if (dto.getUri() != null && !dto.getUri().isEmpty()) {
            ps.setString(2, dto.getUri());
        } else ps.setString(2, "");
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            ps.setString(3, dto.getDescription());
        } else ps.setString(3, "");
        if (dto.getAnnotation() != null && !dto.getAnnotation().isEmpty()) {
            ps.setString(4, dto.getAnnotation());
        } else ps.setString(4, "");
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
        String sql = "delete from resource where id = ? ;";
        mycon = ac.getCon();

        ps = mycon.prepareStatement(sql);
        ps.setLong(1, id);
        int rows = ps.executeUpdate();
        if (rows == 1)
            ret = true;

        DBConnector.closeStatement(ps);

        return ret;
    }

    public ResourceDTO queryById(long id) throws SQLException {
        ResourceDTO ret = null;
        String sql = "select * from resource where id = ?;";
        mycon = ac.getCon();
        ps = mycon.prepareStatement(sql);
        ps.setLong(1, id);
        set = ps.executeQuery();
        if (set.next()) {
            ret = new ResourceDTO();
            ret.setId(set.getLong("id"));
            ret.setName(set.getString("name"));
            ret.setDescription(set.getString("description"));
            ret.setUri(set.getString("uri"));
            ret.setAnnotation(set.getString("annotation"));
        }

        DBConnector.closeStatement(ps);

        return ret;
    }


}