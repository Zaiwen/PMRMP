package ontology;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ontology.SameAsResult.LO;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import databaseaccess.Access;

public class SameAsOfAtomicConstructDAO extends BaseDAO {
	public SameAsOfAtomicConstructDAO(Connection conn) {
		super(conn);
	}

	public boolean create(SameAsOfAtomicConstructDTO dto) {
		boolean ret = false;
		String sql = "insert into same_as_of_ontology_atomic_construct(namespace_RO, non_logical_symboll_RO,namespace_LO, non_logical_symboll_LO) "
				+ "values(?,?,?,?);";
		PreparedStatement sta = null;
		if (dto.getNon_logical_symboll_RO() != dto.getNon_logical_symboll_LO()) {
			try {
				sta = conn.prepareStatement(sql);
				sta.setString(1, dto.getNamespace_RO());
				sta.setString(2, dto.getNon_logical_symboll_RO());
				sta.setString(3, dto.getNamespace_LO());
				sta.setString(4, dto.getNon_logical_symboll_LO());

				//System.out.println("SQL:  " + sta);
				int rows = sta.executeUpdate();
				if (rows == 1)
					ret = true;
			} catch (MySQLIntegrityConstraintViolationException e) {
				System.out.println(">>> SameAsOfAtomicConstructDAO WARNING : "
						+ e.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					set.close();
					//DBConnector.closeStatement(sta);
					Access.closeStatement(sta);
				} catch (Exception e) {
				}
			}
		}

		return ret;
	}

	// public boolean delete(SameAsOfAtomicConstructDTO dto) {
	// boolean ret = false;
	// String sql = "delete from same_as_of_ontology_atomic_construct "
	// + "where id_1 = ? and id_2 = ?;";
	// try {
	// sta = conn.prepareStatement(sql);
	// if (dto.getId1() < dto.getId2()) {
	// sta.setInt(1, dto.getId1());
	// sta.setInt(2, dto.getId2());
	// } else {
	// sta.setInt(1, dto.getId2());
	// sta.setInt(2, dto.getId1());
	// }
	// int rows = sta.executeUpdate();
	// if (rows == 1)
	// ret = true;
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// DBConnector.closeStatement(sta);
	// }
	// return ret;
	// }

	public List<SameAsOfAtomicConstructDTO> queryAll() {
		List<SameAsOfAtomicConstructDTO> ret = new ArrayList<SameAsOfAtomicConstructDTO>();
		String sql = "select * from same_as_of_ontology_atomic_construct;";
		PreparedStatement sta = null;
		ResultSet set = null;
		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			while (set.next()) {
				SameAsOfAtomicConstructDTO dto = new SameAsOfAtomicConstructDTO();
				dto.setNamespace_RO(set.getString("namespace_RO"));
				dto.setNon_logical_symboll_RO(set
						.getString("non_logical_symboll_RO"));
				dto.setNamespace_RO(set.getString("namespace_LO"));
				dto.setNon_logical_symboll_LO(set
						.getString("non_logical_symboll_LO"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				//DBConnector.closeStatement(sta);
				Access.closeStatement(sta);
			} catch (Exception e) {
			}
		}
		return ret;
	}

	public List<Integer> getSameIds(final int id) {
		List<Integer> ret = new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;

			{
				this.add(new Integer(id));
			}
		};
		int index = 0;
		do {
			List<Integer> list_tmp = this.getSameIdsDirectly(ret.get(index++));
			for (Integer int_tmp : list_tmp) {
				boolean isNew = true;
				for (Integer int_tmp_2 : ret) {
					if (int_tmp.equals(int_tmp_2)) {
						isNew = false;
						break;
					}
				}
				if (isNew) {
					ret.add(int_tmp);
				}
			}
		} while (index != ret.size());
		ret.remove(0);

		return ret;
	}

	public List<Integer> getSameIdsDirectly(int id) {
		List<Integer> ret = new ArrayList<Integer>();
		String sql = "select id_1 as res from same_as_of_ontology_atomic_construct "
				+ "where id_2 = ?"
				+ " union select id_2 as res from same_as_of_ontology_atomic_construct "
				+ "where id_1 = ?;";
		PreparedStatement sta = null;
		ResultSet set = null;
		try {
			sta = conn.prepareStatement(sql);
			sta.setInt(1, id);
			sta.setInt(2, id);
			set = sta.executeQuery();
			while (set.next()) {
				Integer integer = new Integer(set.getInt("res"));
				ret.add(integer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				//DBConnector.closeStatement(sta);
				Access.closeStatement(sta);
			} catch (Exception e) {
			}
		}
		return ret;
	}

	// LYNN
	public boolean isExist(SameAsOfAtomicConstructDTO dto) {
		String sql = "select * from same_as_of_ontology_atomic_construct "
				+ "where namespace_RO= '" + dto.getNamespace_RO()
				+ "' AND non_logical_symboll_RO='"
				+ dto.getNon_logical_symboll_RO() + "' AND namespace_LO = '"
				+ dto.getNamespace_LO() + "' AND non_logical_symboll_LO='"
				+ dto.getNon_logical_symboll_LO() + "'";
		//System.out.println(sql);
		boolean exit = false;
		PreparedStatement sta = null;
		ResultSet set = null;
		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			exit = set.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				//DBConnector.closeStatement(sta);
				Access.closeStatement(sta);
			} catch (Exception e) {
			}
		}
		return exit;
	}

	/**
	 * 获得与RO的非逻辑符号为nonLogicalSymbol构件有SAMEAS关系的所有本地本体构件
	 * 
	 * @param domainOntologyID
	 *            领域本体ID
	 * @param nonLogicalSymbol
	 *            非逻辑符号
	 * @return
	 */
	public SameAsResult querySameASByRO(int domainOntologyID,
			String nonLogicalSymbol) {
		SameAsResult same = new SameAsResult();
		OntologyDAO dao = new OntologyDAO();
		OntologyDTO dto = dao.queryById(domainOntologyID);
		same.setNamespace_RO(dto.getUri());
		same.setNonLogical_RO(nonLogicalSymbol);
		dao.closeDBConnection();

		List<LO> ret = same.getList_LO();
		String sql = "select * from same_as_of_ontology_atomic_construct "
				+ "where namespace_RO= '" + same.getNamespace_RO()
				+ "' AND non_logical_symboll_RO='" + same.getNonLogical_RO()
				+ "'";
		//System.out.println("RO: "+sql);
		PreparedStatement sta = null;
		ResultSet set = null;
		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			while (set.next()) {
				//System.out.println("LO");
				ret.add(new LO(set.getString("namespace_LO"), set
						.getString("non_logical_symboll_LO")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				//DBConnector.closeStatement(sta);
				Access.closeStatement(sta);
			} catch (Exception e) {
			}
		}
		return same;
	}

	/**
	 * 获得与LO的非逻辑符号为nonLogicalSymbol构件有SAMEAS关系的所有本地本体构件和参考本体构件
	 * 
	 * @param domainOntologyID
	 *            领域本体ID
	 * @param nonLogicalSymbol
	 *            非逻辑符号
	 * @return
	 */
	public List<SameAsResult> querySameASByLO(int domainOntologyID,
			String nonLogicalSymbol) {
		List<SameAsResult> sames = new ArrayList<SameAsResult>();

		OntologyDAO dao = new OntologyDAO();
		OntologyDTO dto = dao.queryById(domainOntologyID);

		dao.closeDBConnection();
		String sql = "select id,namespace_ro, non_logical_symboll_ro from ontology, same_as_of_ontology_atomic_construct "
				+ "where namespace_LO= '"
				+ dto.getUri()
				+ "' AND non_logical_symboll_LO='"
				+ nonLogicalSymbol
				+ "' AND uri= namespace_ro";
		//System.out.println("LO: "+sql);
		PreparedStatement sta = null;
		ResultSet set = null;
		try {
			sta = conn.prepareStatement(sql);
			set = sta.executeQuery();
			while (set.next()) {
				//System.out.println("RO");
				SameAsResult same = new SameAsResult();
				same.setNamespace_RO(set.getString("namespace_RO"));
				same.setNonLogical_RO(set.getString("non_logical_symboll_RO"));
				same = querySameASByRO(set.getInt("id"), same
						.getNonLogical_RO());
				sames.add(same);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				//DBConnector.closeStatement(sta);
				Access.closeStatement(sta);
			} catch (Exception e) {
			}
		}
		return sames;

	}
}
