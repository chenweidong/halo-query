package halo.query.dal;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import halo.datasource.JsonUtil;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.*;

/**
 * 通过properties文件创建dal使用的数据源
 * Created by akwei on 7/13/14.
 */
@SuppressWarnings("unchecked")
public class HaloDALC3p0PropertiesDataSource extends HaloDALDataSource {

    private static final String GLOBAL_KEY = "global.";

    private static final String DEFAULT_KEY = "default";

    private static final String DS_SLAVE_KEY = "ds_slave";

    private static final String URL_KEY = "url";

    private static final String JDBCURL_KEY = "jdbcUrl";

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(this.name);
        Set<String> keySet = resourceBundle.keySet();
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> globalMap = new HashMap<String, String>();
        for (String key : keySet) {
            String value = resourceBundle.getString(key);
            if (key.startsWith(GLOBAL_KEY)) {
                globalMap.put(key.substring(GLOBAL_KEY.length()), value);
                continue;
            }
            if (key.equals(DEFAULT_KEY)) {
                this.setDefaultDsKey(value);
            } else {
                map.put(key, value);
            }
        }
        this.create(map, globalMap);
        super.afterPropertiesSet();
    }

    public void create(Map<String, String> map, Map<String, String> globalMap) {
        Map<String, DataSource> dsMap = new HashMap<String, DataSource>();
        for (Map.Entry<String, String> e : map.entrySet()) {
            String dsKey = e.getKey();
            Map<String, Object> cfgMap = (Map<String, Object>) JsonUtil.parse(e.getValue(), Map.class);
            if (cfgMap == null) {
                throw new IllegalArgumentException("dsKey[" + dsKey + "] config must be not empty");
            }
            List<String> slaveDsKeys = (List<String>) cfgMap.get(DS_SLAVE_KEY);
            if (slaveDsKeys != null && slaveDsKeys.size() > 0) {
                this.masterSlaveDsKeyMap.put(dsKey, slaveDsKeys);
            }
            cfgMap.remove(DS_SLAVE_KEY);

            if (globalMap != null) {
                for (Map.Entry<String, String> entry : globalMap.entrySet()) {
                    String propertyKey = entry.getKey();
                    if (propertyKey.equals(JDBCURL_KEY) || propertyKey.equals(URL_KEY)) {
                        continue;
                    }
                    Object value = cfgMap.get(propertyKey);
                    if (value == null) {
                        cfgMap.put(propertyKey, entry.getValue());
                    }
                }

                //jdbcUrl
                String prvJdbcUrl = (String) cfgMap.get(JDBCURL_KEY);
                if (isEmpty(prvJdbcUrl)) {
                    String globalJdbcUrl = globalMap.get(JDBCURL_KEY);
                    if (globalJdbcUrl == null) {
                        throw new IllegalArgumentException("global.jdbcUrl or [db].jdbcUrl must be not empty");
                    }
                    String prvUrl = (String) cfgMap.get(URL_KEY);
                    Object jdbcUrl;
                    if (isNotEmpty(prvUrl)) {
                        jdbcUrl = this.buildJdbcUrl(prvUrl, globalJdbcUrl);
                    } else {
                        jdbcUrl = globalJdbcUrl;
                    }
                    cfgMap.put(JDBCURL_KEY, jdbcUrl);
                }
            }
            cfgMap.remove(URL_KEY);
            DataSource dataSource = this.createDataSource(cfgMap);
            dsMap.put(dsKey, dataSource);
        }
        this.setDataSourceMap(dsMap);
    }

    private DataSource createDataSource(Map<String, Object> cfgMap) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Set<Map.Entry<String, Object>> set = cfgMap.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String methodName = C3p0DataSourceUtil.createSetterMethodName(entry.getKey());
            C3p0DataSourceUtil.methodInvoke(dataSource, methodName, entry.getValue());
        }
        return dataSource;
    }

    private String buildJdbcUrl(String url, String globalJdbcUrlTpl) {
        if (isEmpty(url)) {
            throw new IllegalArgumentException("url must be not null");
        }
        if (isEmpty(globalJdbcUrlTpl)) {
            throw new IllegalArgumentException("globalJdbcUrlTpl must be not null");
        }
        return MessageFormat.format(globalJdbcUrlTpl, url);
    }

    private static boolean isNotEmpty(String value) {
        if (value != null && value.trim().length() > 0) {
            return true;
        }
        return false;
    }

    private static boolean isEmpty(String value) {
        if (value == null || value.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
