import mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import po.User;
import po.UserCustom;
import po.UserQueryVo;

import java.io.InputStream;
import java.util.List;

public class UserMapperTest {
    private SqlSessionFactory sqlSessionFactory;

    //注解Before是在执行本类所有测试方法之前先调用这个方法
    @Before
    public void setup() throws Exception {
        //创建SqlSessionFactory
        String resource = "SqlMapConfig.xml";

        //将配置文件加载成流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂，传入mybatis配置文件的信息
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testFindUserById() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建UserMapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //调用userMapper的方法
        User user = userMapper.findUserById(1);

        System.out.println(user.getUsername());
    }

    @Test
    public void testFindUserList() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
        userCustom.setSex("1");
        userCustom.setUsername("张三");
        userQueryVo.setUserCustom(userCustom);
        //调用userMapper的方法

        List<UserCustom> list = userMapper.findUserList(userQueryVo);

        System.out.println(list);
    }
}
