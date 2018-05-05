package ontology;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import databaseaccess.Access;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OntologyAtomicConstructDAO extends BaseDAO {

	public OntologyAtomicConstructDAO(Connection conn) {
		super(conn);
	}

	public OntologyAtomicConstructDAO() {
	}

	public boolean create(OntologyAtomicConstructDTO dto) {
		boolean ret = false;
		String sql = "insert into ontology_atomic_construct(administered_item_administration_record,namespace,non_logical_symbol,type,component_id) "
				+ "values(?,?,?,?,?);";

		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, dto.getAdministeredItemAdministrationRecord());
			sta.setString(2, dto.getNamespace());
			sta.setString(3, DataFormatUtil.transferStrToDBStr(dto
					.getNonLogicalSymbol()));
			sta.setInt(4, dto.getType());
			sta.setInt(5, dto.getOntologyComponentId());
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(">>> OntologyAtomicConstructDAO WARNING : "
					+ e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}

		return ret;
	}

	public boolean update(OntologyAtomicConstructDTO dto) {
		boolean ret = false;
		String sql = "update ontology_atomic_construct set administered_item_administration_record = ?,"
				+ "namespace = ?,non_logical_symbol = ?,"
				+ "type = ? where id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, dto.getAdministeredItemAdministrationRecord());
			sta.setString(2, dto.getNamespace());
			sta.setString(3, DataFormatUtil.transferStrToDBStr(dto
					.getNonLogicalSymbol()));
			sta.setInt(4, dto.getType());
			sta.setInt(5, dto.getId());
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(">>> OntologyAtomicConstructDAO WARNING : "
					+ e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public boolean delete(int id) {
		boolean ret = false;
		String sql = "delete from ontology_atomic_construct where id = ? ;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, id);
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public List<OntologyAtomicConstructDTO> queryAll() {
		List<OntologyAtomicConstructDTO> ret = new ArrayList<OntologyAtomicConstructDTO>();
		String sql = "select * from ontology_atomic_construct;";
		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyAtomicConstructDTO dto = new OntologyAtomicConstructDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setNonLogicalSymbol(DataFormatUtil.transferDBStrToStr(set
						.getString("non_logical_symbol")));
				dto.setType(set.getInt("type"));
				dto.setOntologyComponentId(set.getInt("component_id"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public OntologyAtomicConstructDTO queryById(int id) {
		OntologyAtomicConstructDTO ret = null;
		String sql = "select * from ontology_atomic_construct where id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, id);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyAtomicConstructDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setNamespace(set.getString("namespace"));
				ret.setNonLogicalSymbol(DataFormatUtil.transferDBStrToStr(set
						.getString("non_logical_symbol")));
				ret.setType(set.getInt("type"));
				ret.setOntologyComponentId(set.getInt("component_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public List<OntologyAtomicConstructDTO> queryByComponentId(int componentId) {
		List<OntologyAtomicConstructDTO> ret = new ArrayList<OntologyAtomicConstructDTO>();
		String sql = "select * from ontology_atomic_construct where component_id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, componentId);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyAtomicConstructDTO dto = new OntologyAtomicConstructDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setNonLogicalSymbol(DataFormatUtil.transferDBStrToStr(set
						.getString("non_logical_symbol")));
				dto.setType(set.getInt("type"));
				dto.setOntologyComponentId(set.getInt("component_id"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public List<OntologyAtomicConstructDTO> queryByNSAndNLS(String namespace,
			String nonLogicalSymbol) {
		List<OntologyAtomicConstructDTO> ret = new ArrayList<OntologyAtomicConstructDTO>();
		String sql = "select * from ontology_atomic_construct";
		if (namespace != null || nonLogicalSymbol != null) {
			sql += " where ";
			if (namespace != null) {
				sql += "ontology_atomic_construct.namespace = '" + namespace
						+ "'";
				if (nonLogicalSymbol != null) {
					sql += " and ";
				}
			}
			if (nonLogicalSymbol != null)
				sql += "ontology_atomic_construct.non_logical_symbol = '"
						+ DataFormatUtil.transferStrToDBStr(nonLogicalSymbol)
						+ "'";
		}
		sql += ";";

		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			if (set.next()) {
				OntologyAtomicConstructDTO dto = new OntologyAtomicConstructDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setNonLogicalSymbol(DataFormatUtil.transferDBStrToStr(set
						.getString("non_logical_symbol")));
				dto.setType(set.getInt("type"));
				dto.setOntologyComponentId(set.getInt("component_id"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	/**
	 * 
	 * 获得含有这个非逻辑符号的所有本体URI
	 * 
	 * @param nonlogical
	 *            非逻辑符号
	 * @return
	 */
	public ArrayList<String> queryNsByNonlogical(String nonlogical) {
		ArrayList<String> ret = new ArrayList<String>();
		String sql = "select DISTINCT namespace from ontology_atomic_construct where non_logical_symbol = ?";

		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, DataFormatUtil.transferStrToDBStr(nonlogical));
			set = sta.executeQuery();
			while (set.next()) {
				ret.add(set.getString("namespace"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	/**
	 * 获取该本体所复用的原子构件
	 * 
	 * @param id
	 * @return
	 */
	public List<OntologyAtomicConstructDTO> queryImportByOntologyID(int id) {
		OntologyDAO dao = new OntologyDAO();
		String uri = dao.queryById(id).getUri();
		dao.closeDBConnection();

		List<OntologyAtomicConstructDTO> ret = new ArrayList<OntologyAtomicConstructDTO>();
		String sql = "select * from ontology_atomic_construct where component_id in (select id  from ontology_component where ontology_id=? )and namespace !=?";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, id);
			sta.setString(2, uri);
			//System.out.println(sta);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyAtomicConstructDTO dto = new OntologyAtomicConstructDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setNonLogicalSymbol(DataFormatUtil.transferDBStrToStr(set
						.getString("non_logical_symbol")));
				dto.setType(set.getInt("type"));
				dto.setOntologyComponentId(set.getInt("component_id"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	/**
	 * 获取该本体所的原子构件
	 * 
	 * @param id
	 * @return
	 */
	public List<String> queryByOntologyID(int id) {
		OntologyDAO dao = new OntologyDAO();
		// String uri = dao.queryById(id).getUri();
		dao.closeDBConnection();

		List<String> ret = new ArrayList<String>();
		String sql = "select DISTINCT namespace from ontology_atomic_construct where component_id in (select id  from ontology_component where ontology_id="+id+")";
		//System.out.println(sql);
		try {
			sta = conn.prepareStatement(sql);
			
			set = sta.executeQuery();
			while (set.next()) {
				String dto = set.getString("namespace");
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}
	

}
