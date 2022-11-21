package dukiland.test.database.comm;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomSqlSessionFactory {

    static SqlSessionFactory sqlSessionFactory;
    static {
        String resource = "Configuration.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            log.info("Initialize Successed.");
        } catch (IOException ex) {
            log.error("CustomSqlSessionFactory.IOException", ex);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) {
        SqlSession session = getSqlSession();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("CREATE_TABLE", "CREATE TABLE STUDENTS (ID INT PRIMARY KEY, NAME VARCHAR(50))");
        paramMap.put("ID", "1");
        paramMap.put("NAME", "Duki");

        int uCnt = session.update("Sample.sample_create", paramMap);
        int iCnt = session.insert("Sample.sample_insert", paramMap);
        List<Map<String, Object>> result = session.selectList("Sample.sample_select", paramMap);

        log.info("Create Table : {}", uCnt);
        log.info("STUDENTS : {}", iCnt);
        log.info("STUDENTS LIST : {}", result);
    }

}
