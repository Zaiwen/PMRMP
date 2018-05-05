package ontology;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import databaseaccess.Access;

public class OntologyComponentDAO extends BaseDAO {

	public OntologyComponentDAO(Connection conn) {
		super(conn);
	}

	public OntologyComponentDAO() {
	}

	public boolean create(OntologyComponentDTO dto) {
		boolean ret = false;
		String sql = "insert into ontology_component(administered_item_administration_record,namespace,sentence_identifier,type,source,key_atomic_id,canmodify,ontology_id) "
				+ "values(?,?,?,?,?,?,?,?);";

		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, dto.getAdministeredItemAdministrationRecord());
			sta.setString(2, dto.getNamespace());
			sta.setString(3, dto.getSentenceIdentifier());
			sta.setString(4, dto.getType());
			sta.setString(5, dto.getSource());
			sta.setString(6, dto.getKeyAtomic());
			sta.setInt(7, dto.getCanModify());
			sta.setInt(8, dto.getOntologyId());
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(">>> OntologyComponentDAO WARNING : "
					+ e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}

		return ret;
	}

	public boolean update(OntologyComponentDTO dto) {
		boolean ret = false;
		String sql = "update ontology_component set administered_item_administration_record = ?,"
				+ "namespace = ?,sentence_identifier = ?,"
				+ "type = ?,source = ?,name = ? where id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, dto.getAdministeredItemAdministrationRecord());
			sta.setString(2, dto.getNamespace());
			sta.setString(3, dto.getSentenceIdentifier());
			sta.setString(4, dto.getType());
			sta.setString(5, dto.getSource());
			sta.setString(6, dto.getKeyAtomic());
			sta.setInt(7, dto.getId());
			int rows = sta.executeUpdate();
			if (rows == 1)
				ret = true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(">>> OntologyComponentDAO WARNING : "
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
		String sql = "delete from ontology_component where id = ? ;";
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

	public List<OntologyComponentDTO> queryAll() {
		List<OntologyComponentDTO> ret = new ArrayList<OntologyComponentDTO>();
		String sql = "select * from ontology_component;";
		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyComponentDTO dto = new OntologyComponentDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setSentenceIdentifier(set.getString("sentence_identifier"));
				dto.setType(set.getString("type"));
				dto.setSource(set.getString("source"));
				dto.setKeyAtomic(set.getString("key_atomic_id"));
				dto.setCanModify(set.getInt("canmodify"));
				dto.setOntologyId(set.getInt("ontology_id"));
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

	public OntologyComponentDTO queryById(int id) {
		OntologyComponentDTO ret = null;
		String sql = "select * from ontology_component where id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, id);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyComponentDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setNamespace(set.getString("namespace"));
				ret.setSentenceIdentifier(set.getString("sentence_identifier"));
				ret.setType(set.getString("type"));
				ret.setSource(set.getString("source"));
				ret.setKeyAtomic(set.getString("key_atomic_id"));
				ret.setCanModify(set.getInt("canmodify"));
				ret.setOntologyId(set.getInt("ontology_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public List<OntologyComponentDTO> queryByOntologyId(int ontologyId) {
		List<OntologyComponentDTO> ret = new ArrayList<OntologyComponentDTO>();
		String sql = "select * from ontology_component where ontology_id=? order by id asc;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, ontologyId);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyComponentDTO dto = new OntologyComponentDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setSentenceIdentifier(set.getString("sentence_identifier"));
				dto.setType(set.getString("type"));
				dto.setSource(set.getString("source"));
				dto.setKeyAtomic(set.getString("key_atomic_id"));
				dto.setCanModify(set.getInt("canmodify"));
				dto.setOntologyId(set.getInt("ontology_id"));
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

	public OntologyComponentDTO queryByNsAndSI(String namespace,
			String sentenceIdentifier) {
		OntologyComponentDTO ret = null;
		String sql = "select * from ontology_component where namespace = ? and sentence_identifier = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, namespace);
			sta.setString(2, sentenceIdentifier);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyComponentDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setNamespace(set.getString("namespace"));
				ret.setSentenceIdentifier(set.getString("sentence_identifier"));
				ret.setType(set.getString("type"));
				ret.setSource(set.getString("source"));
				ret.setKeyAtomic(set.getString("key_atomic_id"));
				ret.setCanModify(set.getInt("canmodify"));
				ret.setOntologyId(set.getInt("ontology_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public OntologyComponentDTO queryByNsAndSI(String namespace,
			String sentenceIdentifier, int ontologyId) {
		OntologyComponentDTO ret = null;
		String sql = "select * from ontology_component where namespace = ? and sentence_identifier = ? and ontology_id=?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, namespace);
			sta.setString(2, sentenceIdentifier);
			sta.setInt(3, ontologyId);
			set = sta.executeQuery();
			if (set.next()) {
				ret = new OntologyComponentDTO();
				ret.setId(set.getInt("id"));
				ret.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				ret.setNamespace(set.getString("namespace"));
				ret.setSentenceIdentifier(set.getString("sentence_identifier"));
				ret.setType(set.getString("type"));
				ret.setSource(set.getString("source"));
				ret.setKeyAtomic(set.getString("key_atomic_id"));
				ret.setCanModify(set.getInt("canmodify"));
				ret.setOntologyId(set.getInt("ontology_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ret;
	}

	public List<OntologyComponentDTO> queryByKeyAtomicId(int keyAtomicId) {
		List<OntologyComponentDTO> ret = new ArrayList<OntologyComponentDTO>();
		String sql = "select * from ontology_component where key_atomic_id = ?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, keyAtomicId);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyComponentDTO dto = new OntologyComponentDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setSentenceIdentifier(set.getString("sentence_identifier"));
				dto.setType(set.getString("type"));
				dto.setSource(set.getString("source"));
				dto.setKeyAtomic(set.getString("key_atomic_id"));
				dto.setCanModify(set.getInt("canmodify"));
				dto.setOntologyId(set.getInt("ontology_id"));
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

	public List<OntologyComponentDTO> queryByKeyAtomic(String namespace,
			String nonLogicalSymbol) {
		List<OntologyComponentDTO> ret = new ArrayList<OntologyComponentDTO>();

		String sql = "select * from ontology_component ";
		if (namespace != null || nonLogicalSymbol != null) {
			sql += "where "; 
			if (namespace != null){
				sql += "namespace = '" + namespace + "'";
				if (nonLogicalSymbol != null)
					sql += " and ";
			}
			if (nonLogicalSymbol != null)
				sql += "key_atomic_id = '" + nonLogicalSymbol + "'";
		}
		sql += ";";

		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyComponentDTO dto = new OntologyComponentDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setSentenceIdentifier(set.getString("sentence_identifier"));
				dto.setType(set.getString("type"));
				dto.setSource(set.getString("source"));
				dto.setKeyAtomic(set.getString("key_atomic_id"));
				dto.setCanModify(set.getInt("canmodify"));
				dto.setOntologyId(set.getInt("ontology_id"));
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
	 * 获取新的构件
	 * @param cdto
	 * @return
	 */
	public List<OntologyComponentDTO> queryNewComponent(OntologyDTO cdto){
		List<OntologyComponentDTO> ret = new ArrayList<OntologyComponentDTO>();
		List<OntologyComponentDTO> result = new ArrayList<OntologyComponentDTO>();
		String sql = "select * from ontology_component where ontology_id=?;";
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, cdto.getId());
			//System.out.println(sta);
			set = sta.executeQuery();
			while (set.next()) {
				OntologyComponentDTO dto = new OntologyComponentDTO();
				dto.setId(set.getInt("id"));
				dto.setAdministeredItemAdministrationRecord(set
						.getString("administered_item_administration_record"));
				dto.setNamespace(set.getString("namespace"));
				dto.setSentenceIdentifier(set.getString("sentence_identifier"));
				dto.setType(set.getString("type"));
				dto.setSource(set.getString("source"));
				dto.setKeyAtomic(set.getString("key_atomic_id"));
				dto.setCanModify(set.getInt("canmodify"));
				dto.setOntologyId(set.getInt("ontology_id"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		for(OntologyComponentDTO dto: ret){
			String namespace = queryNamespace(dto.getKeyAtomic());
			if(namespace.equals(cdto.getUri())){}
			result.add(dto);
		}
		
		return result;
	} 
	
	/**
	 * 
	 * @param keyAtomic
	 * @return
	 */
	public String queryNamespace(String keyAtomic){
		//List<OntologyComponentDTO> ret = new ArrayList<OntologyComponentDTO>();
		String sql = "select namespace from ontology_atomic_construct where non_logical_symbol = ?;";
		String ns="";
		try {
			sta = conn.prepareStatement(sql);
			sta.setString(1, DataFormatUtil.transferStrToDBStr(keyAtomic));
			//System.out.println(sta);
			set = sta.executeQuery();
			if (set.next()) {
				ns = set.getString("namespace");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DBConnector.closeStatement(sta);
			Access.closeStatement(sta);
		}
		return ns;
	} 
}

