package org.sklse.processRegister.db.dao;

import databaseaccess.Access;
import ontology.IdWorker;
import org.sklse.processRegister.db.dto.PluginInfoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PluginProcessDAO {

    private Access ac;
    private PreparedStatement ps = null;
    public static final String insertsql =
            "insert into pluginprocess(id,name,uri,annotation,query_str1,query_str2,author,time,data_extend_type,structure_extend_type,param,field,process_type,search_extensibility_point_type,process_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String delete = "delete from pluginprocess where id = ?";
    public static final String update = "update pluginprocess set name = ?,uri = ?" +
            ",annotation = ?,precondition = ?,postcondition = ?,author = ?,time = ?" +
            ",data_extend_type = ?,structure_extend_type = ?,param = ? where id = ?";

    public static final String get = "select id,name,uri,annotation,query_str1,query_str2,author,time,data_extend_type,structure_extend_type,param,field,process_type,search_extensibility_point_type,process_id from pluginprocess where id = ?";

    private static PluginProcessDAO instance;

    public static PluginProcessDAO getInstance() {
        if (instance == null) {
            synchronized (PluginProcessDAO.class) {
                if (instance == null) {
                    try {
                        instance = new PluginProcessDAO();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return instance;
    }

    private Connection mycon;

    public PluginProcessDAO() throws Exception {
        ac = new Access();
        ac.connDB("bpep");
    }

    public long insert(PluginInfoDTO ppd) throws SQLException {
        mycon = ac.getCon();
        long nextId = IdWorker.getNextId();
        ppd.setId(nextId);
        ps = mycon.prepareStatement(insertsql);
        ps.setLong(1, ppd.getId());
        ps.setString(2, ppd.getName());
        ps.setString(3, ppd.getUrl());
        ps.setString(4, ppd.getAnnotation());
        ps.setString(5, ppd.getQueryStr1());
        ps.setString(6, ppd.getQueryStr2());
        ps.setLong(7, ppd.getAuthor());
        ps.setDate(8, ppd.getCreateTime());
        ps.setInt(9, ppd.getDataFlowExtensionParttern());
        ps.setInt(10, ppd.getControllFlowExtensionPattern());
        ps.setString(11, ppd.getPluginParam());
        ps.setInt(12, ppd.getField());
        ps.setInt(13, ppd.getProcessType());
        ps.setInt(14, ppd.getSearchExtensibilityPointType());
        ps.setLong(15, ppd.getProcessId());
        ps.execute();
        ps.close();
        ac.closeDB();
        return nextId;
    }

    public void delete(long id) throws SQLException {
        ps = ac.getCon().prepareStatement(delete);
        ps.setLong(1, id);
        ps.execute();
        ps.close();
        ac.closeDB();
    }

    public PluginInfoDTO get(long id) throws SQLException {
        ps = ac.getCon().prepareStatement(get);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            PluginInfoDTO ppd = new PluginInfoDTO();
            ppd.setId(rs.getLong(1));
            ppd.setName(rs.getString(2));
            ppd.setUrl(rs.getString(3));
            ppd.setAnnotation(rs.getString(4));
            ppd.setQueryStr1(rs.getString(5));
            ppd.setQueryStr2(rs.getString(6));
            ppd.setAuthor(rs.getLong(7));
            ppd.setCreateTime(rs.getDate(8));
            ppd.setDataFlowExtensionParttern(rs.getInt(9));
            ppd.setControllFlowExtensionPattern(rs.getInt(10));
            ppd.setPluginParam(rs.getString(11));
            ppd.setField(rs.getInt(12));
            ppd.setProcessType(rs.getInt(13));
            ppd.setSearchExtensibilityPointType(rs.getInt(14));
            ppd.setProcessId(rs.getLong(15));
            rs.close();
            ps.close();
            this.close();
            return ppd;
        }
        return null;
    }

    public void update(PluginInfoDTO ppd) throws SQLException {
        if (ppd == null) return;
        mycon = ac.getCon();
        ps = mycon.prepareStatement(update);
        ps.setLong(1, ppd.getId());
        ps.setString(2, ppd.getName());
        ps.setString(3, ppd.getUrl());
        ps.setString(4, ppd.getAnnotation());
        ps.setString(5, ppd.getQueryStr1());
        ps.setString(6, ppd.getQueryStr2());
        ps.setLong(7, ppd.getAuthor());
        ps.setDate(8, ppd.getCreateTime());
        ps.setInt(9, ppd.getDataFlowExtensionParttern());
        ps.setInt(10, ppd.getControllFlowExtensionPattern());
        ps.setString(11, ppd.getPluginParam());
        ps.setInt(12, ppd.getField());
        ps.setInt(13, ppd.getProcessType());
        ps.setInt(14, ppd.getSearchExtensibilityPointType());
        ps.setLong(15, ppd.getProcessId());
        ps.executeUpdate();
        ps.close();
        this.close();
    }

    public void close() {
        try {
            ac.closeDB();
        } catch (SQLException e) {
            System.out.println("Close DB Failed in PluginProcessDAO");
            e.printStackTrace();
        }
    }
}
