package org.sklse.processRegister.expander.impl;

import change4epml.ConvertInterface;
import databaseaccess.Access;
import org.sklse.processRegister.expander.OriginalProcessService;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/5/4
 * @email 745861642@qq.com
 * @description
 */
public class OriginalProcessServiceImpl implements OriginalProcessService {
    private static String getGraphIdByUserAndName = "select graph from original_process where user=? and name=?";

    @Override
    public long getGraphIdByUserAndName(String user, String name) throws Exception {
        //在processModel表中获取对应process的precondition和postcondition的id
        Access ac = new Access();
        ac.connDB("bpep");
        Connection conn = ac.getCon();
        PreparedStatement ps = conn.prepareStatement(getGraphIdByUserAndName);
        ps.setString(1, user);
        ps.setString(2, name);
        ResultSet rs = ps.executeQuery();
        long graph = 0l;
        if (rs.next()) {
            return Long.parseLong(rs.getString(1));
        }
        ac.closeDB();
        return -1;
    }

    private static String sql2 = "select peid from pm_contain_pe where pmid=? and petype='PROCESS'";

    @Override
    public List<Long> getPeidByPmid(long graph) throws Exception {
        Access ac = new Access();
        ac.connDB("bpep");
        Connection conn = ac.getCon();
        PreparedStatement ps = conn.prepareStatement(sql2);
        ps.setLong(1, graph);
        ResultSet rs = ps.executeQuery();
        List<Long> peids = new ArrayList<Long>();
        while (rs.next()) {
            peids.add(Long.parseLong(rs.getString(1)));
        }
        ac.closeDB();
        return peids;
    }

    private static String getMaxVersion = "select max(version) from original_process where user=? and name=?";

    @Override
    public long getMaxVersion(String user, String name) throws Exception {
        Access ac = new Access();
        ac.connDB("bpep");
        // 查询原本流程的最新版本的版本号
        PreparedStatement pst = ac.getCon().prepareStatement(getMaxVersion);
        pst.setString(1, user);
        pst.setString(2, name);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        ac.closeDB();
        return 0;
    }

    @Override
    public void insertOriginalProcess(String user, String name, String content, long version, long lastVersion) throws Exception {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        String sql = "insert into original_process values(null,?,?,?,null,?,?,?,?,?)";
        Access ac = new Access();
        ac.connDB("bpep");
        PreparedStatement pst = ac.getCon().prepareStatement(sql);
        pst.setString(1, user);
        pst.setString(2, name);
        pst.setString(3, content);
        pst.setString(4, time);
        pst.setString(5, "epc");
        pst.setLong(6, version);
        pst.setLong(7, lastVersion);
        pst.setLong(8, ConvertInterface.convert(ConvertInterface.string2File(content, name)));
        pst.execute();
        ac.closeDB();
    }

    public static void main(String[] args) {
        Class<OriginalProcessServiceImpl> originalProcessServiceClass = OriginalProcessServiceImpl.class;
        ClassLoader classLoader = originalProcessServiceClass.getClassLoader();
        URL resource = classLoader.getResource("struts.xml");
        System.out.println(resource.getPath());
    }
}
