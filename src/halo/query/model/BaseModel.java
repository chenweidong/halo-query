package halo.query.model;

import halo.query.Query;
import halo.query.mapping.EntityTableInfo;

import java.util.List;

public class BaseModel {

	protected static Query query;

	public void setQuery(Query query) {
		BaseModel.query = query;
	}

	public static Query getQuery() {
		return query;
	}

	private String tablePostfix;

	public void setTablePostfix(String tablePostfix) {
		this.tablePostfix = tablePostfix;
	}

	public String getTablePostfix() {
		return tablePostfix;
	}

	/**
	 * 创建数据到数据库
	 * 
	 * @throws Exception
	 */
	public void create() throws Exception {
		EntityTableInfo<?> et = query.getEntityTableInfo(this.getClass());
		Number n = query.insertForNumber(this, this.tablePostfix);
		Object idValue = et.getIdField().get(this);
		if (idValue == null) {
			et.getIdField().set(this, n);
		}
		else {
			if (idValue instanceof Number) {
				Number v = (Number) idValue;
				if (v.longValue() == 0) {
					et.getIdField().set(this, n);
				}
			}
		}
	}

	/**
	 * 更新已存在的数据，数据必须有id
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception {
		query.update(this, this.tablePostfix);
	}

	/**
	 * 删除已存在的数据，数据必须有id
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {
		query.delete(this, this.tablePostfix);
	}

	/**
	 * @param sqlAfterTable
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static int count(String sqlAfterTable, Object[] values)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param tablePostfix
	 * @param sqlAfterTable
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static int count(String tablePostfix, String sqlAfterTable,
			Object[] values)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param idValue
	 * @return
	 * @throws Exception
	 */
	public static <T> T objById(Object idValue) throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param idValue
	 * @param tablePostfix
	 * @return
	 * @throws Exception
	 */
	public static <T> T objById(Object idValue, String tablePostfix)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param sqlAfterTable
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static <T> T obj(String sqlAfterTable, Object[] values)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param tablePostfix
	 * @param sqlAfterTable
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static <T> T obj(String tablePostfix, String sqlAfterTable,
			Object[] values)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param sqlAfterTable
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> list(String sqlAfterTable, Object[] values)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param tablePostfix
	 * @param sqlAfterTable
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> list(String tablePostfix, String sqlAfterTable,
			Object[] values) throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param sqlAfterTable
	 * @param begin
	 * @param size
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> listMySQL(String sqlAfterTable, int begin,
			int size, Object[] values) throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param tablePostfix
	 * @param sqlAfterTable
	 * @param begin
	 * @param size
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> listMySQL(String tablePostfix,
			String sqlAfterTable,
			int begin, int size, Object[] values) throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param updateSqlSeg
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static int update(String updateSqlSeg, Object[] values)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}

	/**
	 * @param tablePostfix
	 * @param updateSqlSeg
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static int update(String tablePostfix, String updateSqlSeg,
			Object[] values)
			throws Exception {
		throw new RuntimeException("class must override this method");
	}
}
