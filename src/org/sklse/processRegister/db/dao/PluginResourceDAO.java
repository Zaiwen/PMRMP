package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import org.sklse.processRegister.db.dto.PluginResourceDTO;
import org.sklse.processRegister.db.enums.ResourceTypeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PluginResourceDAO {
    //one DAO can be used for different DTO,
    //but in the end close() must be invoked
    private Access ac;
    private PreparedStatement ps = null;
    private static final String INSERT = "INSERT into pluginresource() values(?,?,?,?,?,?,?,?);";
    private static final String DELETE = "DELETE from pluginresource where id = ?";
    private static final String DELETE_BY_PROCESS_ID = "DELETE from pluginresource where process_id = ?";
    private static final String get = "select * from pluginresource where id = ?";
    private static final String update = "update pluginresource set name = ?,uri = ?," +
            "annotation = ?,description = ?,relation_type = ?,process_id = ? where id = ?";
    private static final String GET_BY_PROCESSID_RESOURCETYPE = "select * from pluginresource where process_id = ?  AND relation_type = ?";


    private static PluginResourceDAO instance;

    public static PluginResourceDAO getInstance() {
        if (instance == null) {
            synchronized (PluginResourceDAO.class) {
                if (instance == null) {
                    try {
                        instance = new PluginResourceDAO();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    private Connection mycon;

    public PluginResourceDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
    }

    public void insert(PluginResourceDTO prd) throws SQLException {
        mycon = ac.getCon();
        ps = mycon.prepareStatement(INSERT);
        ps.setLong(1, prd.getId());
        ps.setString(2, prd.getName());
        ps.setString(3, prd.getUri());
        ps.setString(4, prd.getAnnotation());
        ps.setString(5, prd.getDescription());
        ps.setInt(6, prd.getResourceType());
        ps.setLong(7, prd.getProcessId());
        ps.setString(8, prd.getParam());
        ps.execute();
        ps.close();
        ac.closeDB();
    }

    public void delete(long id) throws SQLException {
        mycon = ac.getCon();
        ps = ac.getCon().prepareStatement(DELETE);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
        ac.closeDB();

    }

    public void deleteByProcessId(long id) throws SQLException {
        mycon = ac.getCon();
        ps = ac.getCon().prepareStatement(DELETE_BY_PROCESS_ID);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
        ac.closeDB();
    }

    public PluginResourceDTO get(Long id) throws SQLException {
        PluginResourceDTO prd = new PluginResourceDTO();
        ps = ac.getCon().prepareStatement(get);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            prd.setId(rs.getInt(1));
            prd.setName(rs.getString(2));
            prd.setUri(rs.getString(3));
            prd.setAnnotation(rs.getString(4));
            prd.setDescription(rs.getString(5));
            prd.setResourceType(rs.getInt(6));
            prd.setProcessId(rs.getLong(7));
            return prd;
        }
        ac.closeDB();

        return null;
    }

    public void update(PluginResourceDTO prd) throws SQLException {
        ps = ac.getCon().prepareStatement(update);
        ps.setLong(7, prd.getId());
        ps.setString(1, prd.getName());
        ps.setString(2, prd.getUri());
        ps.setString(3, prd.getAnnotation());
        ps.setString(4, prd.getDescription());
        ps.setInt(5, prd.getResourceType());
        ps.setLong(6, prd.getProcessId());
        ps.executeUpdate();
        ps.close();
        ac.closeDB();

    }

    /**
     * 根据process id和resourceType获得ResourceDTO
     *
     * @param processid
     * @param resourceType
     * @return
     */
    public List<PluginResourceDTO> getByProcessIdAndResourceType(long processid, ResourceTypeEnum resourceType) throws SQLException {
        List<PluginResourceDTO> result = new ArrayList<PluginResourceDTO>();
        ps = ac.getCon().prepareStatement(GET_BY_PROCESSID_RESOURCETYPE);
        ps.setLong(1, processid);
        ps.setInt(2, resourceType.getValue());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            PluginResourceDTO prd = new PluginResourceDTO();
            prd.setId(rs.getInt(1));
            prd.setName(rs.getString(2));
            prd.setUri(rs.getString(3));
            prd.setAnnotation(rs.getString(4));
            prd.setDescription(rs.getString(5));
            prd.setResourceType(rs.getInt(6));
            prd.setProcessId(rs.getLong(7));
            result.add(prd);
        }
        ac.closeDB();
        return result;
    }

    public void close() {
        try {
            ac.closeDB();
        } catch (SQLException e) {
            System.out.println("Close DB in PluginResourceDAO");
            e.printStackTrace();
        }
    }
}
