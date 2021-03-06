package test.mysql;

import halo.query.HaloIdException;
import halo.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import test.SuperBaseModelTest;
import test.UserServiceImpl;
import test.bean.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/query-test.xml"})
@Transactional
public class QueryTest extends SuperBaseModelTest {

    int roleId;

    Role role;

    @Resource
    private UserServiceImpl userServiceImpl;

    @Resource
    Query query;

    private Map<String, Object> objMap;

    @After
    public void after() {
        User user = (User) objMap.get("user");
        User user1 = (User) objMap.get("user1");
        user.delete();
        user1.delete();
        query.delete(role);
    }

    @Before
    public void before() {
        role = new Role();
        role.setCreateTime(new Date());
        roleId = query.insertForNumber(role).intValue();
        role.setRoleId(roleId);
        objMap = new HashMap<String, Object>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        User user = new User();
        user.setAddr("abc");
        user.setCreatetime(new Timestamp(cal.getTimeInMillis()));
        user.setIntro("intro");
        user.setNick("我的昵称我的昵称袁伟aabb");
        user.setSex(1);
        user.setUuid(new BigInteger("18446744073709551615"));
        user.setUuid10(1234567890123L);
        user.setUuid11(1234567890);
        user.setUuid12(new BigDecimal("1111111111"));
        user.setUuid2(23.04);
        user.setUuid3(35.09);
        user.setUuid4(10.9f);
        user.setUuid5(10.7f);
        user.setUuid6((short) 12);
        user.setUuid7(Short.valueOf("11"));
        user.setUuid8((byte) 3);
        user.setUuid9(Byte.valueOf("5"));
        user.setUsersex(UserSex.FEMALE);
        user.setEnableflag(true);
        user.setUserid(query.insertForNumber(user).longValue());
        objMap.put("user", user);
        User user1 = new User();
        user1.setAddr("abc");
        user1.setCreatetime(new Timestamp(cal.getTimeInMillis()));
        user1.setIntro("intro");
        user1.setNick("我的昵称我的昵称袁伟");
        user1.setSex(1);
        user1.setUuid(new BigInteger("18446744073709551615"));
        user1.setUuid10(1234567890123L);
        user1.setUuid11(1234567890);
        user1.setUuid12(new BigDecimal("1111111111"));
        user1.setUuid2(23.04);
        user1.setUuid3(35.09);
        user1.setUuid4(10.9f);
        user1.setUuid5(10.7f);
        user1.setUuid6((short) 12);
        user1.setUuid7(Short.valueOf("11"));
        user1.setUuid8((byte) 3);
        user1.setUuid9(Byte.valueOf("5"));
        user1.setUsersex(UserSex.MALE);
        user1.setEnableflag(false);
        user1.setUserid(query.insertForNumber(user1).longValue());
        objMap.put("user1", user1);
    }


    @Test
    public void testUserServcice() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        User user = new User();
        user.setAddr("abc");
        user.setCreatetime(new Timestamp(cal.getTimeInMillis()));
        user.setIntro("intro");
        user.setNick("我的昵称我的昵称袁伟3344");
        user.setSex(1);
        user.setUuid(new BigInteger("18446744073709551615"));
        user.setUuid10(1234567890123L);
        user.setUuid11(1234567890);
        user.setUuid12(new BigDecimal("1111111111"));
        user.setUuid2(23.04);
        user.setUuid3(35.09);
        user.setUuid4(10.9f);
        user.setUuid5(10.7f);
        user.setUuid6((short) 12);
        user.setUuid7(Short.valueOf("11"));
        user.setUuid8((byte) 3);
        user.setUuid9(Byte.valueOf("5"));
        user.setUsersex(UserSex.FEMALE);
        user.setEnableflag(true);
        this.userServiceImpl.createUserTx(user);
    }

    @Test
    public void insert_select_update_delete() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        User user = new User();
        user.setAddr("abc");
        user.setCreatetime(new Timestamp(cal.getTimeInMillis()));
        user.setIntro("intro");
        user.setNick("我的昵称我的昵称袁伟5566");
        user.setSex(1);
        user.setUuid(new BigInteger("18446744073709551615"));
        user.setUuid10(1234567890123L);
        user.setUuid11(1234567890);
        user.setUuid12(new BigDecimal("1111111111"));
        user.setUuid2(23.04);
        user.setUuid3(35.09);
        user.setUuid4(10.9f);
        user.setUuid5(10.7f);
        user.setUuid6((short) 12);
        user.setUuid7(Short.valueOf("11"));
        user.setUuid8((byte) 3);
        user.setUuid9(Byte.valueOf("5"));
        user.setUsersex(UserSex.MALE);
        user.setEnableflag(true);
        //insert
        user.setUserid(query.insertForNumber(user).longValue());
        //select
        User dbUser = query.objById(User.class, user.getUserid());
        Assert.assertNotNull(dbUser);
        //update
        query.update(dbUser);
        Assert.assertEquals(user.getAddr(), dbUser.getAddr());
        Assert.assertEquals(user.getIntro(), dbUser.getIntro());
        Assert.assertEquals(user.getNick(), dbUser.getNick());
        Assert.assertEquals(user.getSex(), dbUser.getSex());
        Assert.assertEquals(user.getUserid(), dbUser.getUserid());
        Assert.assertEquals(user.getUuid(), dbUser.getUuid());
        Assert.assertEquals(user.getUuid2(), dbUser.getUuid2());
        Assert.assertEquals(String.valueOf(user.getUuid3()),
                String.valueOf(dbUser.getUuid3()));
        Assert.assertEquals(String.valueOf(user.getUuid4()),
                String.valueOf(dbUser.getUuid4()));
        Assert.assertEquals(user.getUuid5(), dbUser.getUuid5());
        Assert.assertEquals(user.getUuid6(), dbUser.getUuid6());
        Assert.assertEquals(user.getUuid7(), dbUser.getUuid7());
        Assert.assertEquals(user.getUuid8(), dbUser.getUuid8());
        Assert.assertEquals(user.getUuid9(), dbUser.getUuid9());
        Assert.assertEquals(user.getUuid10(), dbUser.getUuid10());
        Assert.assertEquals(user.getUuid11(), dbUser.getUuid11());
        Assert.assertEquals(user.getUuid12(), dbUser.getUuid12());
        Assert.assertEquals(user.getCreatetime().getTime(), dbUser
                .getCreatetime().getTime());
        Assert.assertEquals(user.getUsersex(), dbUser.getUsersex());
        Assert.assertEquals(user.isEnableflag(), dbUser.isEnableflag());
        //delete
        query.delete(dbUser);
        dbUser = query.objById(User.class, dbUser.getUserid());
        Assert.assertNull(dbUser);
    }

    @Test
    public void insert_select_update_deleteForNull() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        User user = new User();
        user.setAddr("abc");
        user.setCreatetime(new Timestamp(cal.getTimeInMillis()));
        user.setIntro("intro");
        user.setNick("nickname");
        user.setSex(null);
        user.setUuid(new BigInteger("18446744073709551615"));
        user.setUuid10(null);
        user.setUuid11(1234567890);
        user.setUuid12(null);
        user.setUuid2(null);
        user.setUuid3(35.09);
        user.setUuid4(10.9f);
        user.setUuid5(null);
        user.setUuid6((short) 12);
        user.setUuid7(null);
        user.setUuid8((byte) 3);
        user.setUuid9(null);
        user.setUsersex(UserSex.MALE);
        user.setEnableflag(true);
        user.setUserid(query.insertForNumber(user).longValue());
        User dbUser = query.objById(User.class, user.getUserid());
        Assert.assertNotNull(dbUser);
        query.update(dbUser);
        Assert.assertEquals(user.getAddr(), dbUser.getAddr());
        Assert.assertEquals(user.getIntro(), dbUser.getIntro());
        Assert.assertEquals(user.getNick(), dbUser.getNick());
        Assert.assertNull(dbUser.getSex());
        Assert.assertEquals(user.getUserid(), dbUser.getUserid());
        Assert.assertEquals(user.getUuid(), dbUser.getUuid());
        Assert.assertNull(dbUser.getUuid2());
        Assert.assertEquals(String.valueOf(user.getUuid3()),
                String.valueOf(dbUser.getUuid3()));
        Assert.assertEquals(String.valueOf(user.getUuid4()),
                String.valueOf(dbUser.getUuid4()));
        Assert.assertNull(dbUser.getUuid5());
        Assert.assertEquals(user.getUuid6(), dbUser.getUuid6());
        Assert.assertNull(dbUser.getUuid7());
        Assert.assertEquals(user.getUuid8(), dbUser.getUuid8());
        Assert.assertNull(dbUser.getUuid9());
        Assert.assertNull(dbUser.getUuid10());
        Assert.assertEquals(user.getUuid11(), dbUser.getUuid11());
        Assert.assertNull(dbUser.getUuid12());
        Assert.assertEquals(user.getCreatetime().getTime(), dbUser
                .getCreatetime().getTime());
        Assert.assertEquals(user.getUsersex(), dbUser.getUsersex());
        Assert.assertEquals(user.isEnableflag(), dbUser.isEnableflag());
        query.delete(dbUser);
        dbUser = query.objById(User.class, dbUser.getUserid());
        Assert.assertNull(dbUser);
    }

    @Test
    public void select() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        TestUser testUser = new TestUser();
        testUser.setCreatetime(d);
        testUser.setGender((byte) 1);
        testUser.setMoney(99.448d);
        testUser.setPurchase(89.345f);
        testUser.setNick("nickname");
        testUser.setUserid(query.insertForNumber(testUser)
                .longValue());
        Member m = new Member();
        m.setUserid(testUser.getUserid());
        m.setGroupid(99);
        m.setNick("membernick");
        m.setMemberUserId(query.insertForNumber(m).longValue());
        List<Member> list = query.mysqlList(Member.class,
                "where 1=1 and member_.userid=?", 0,
                10, new Object[]{m.getUserid()});
        Member o = list.get(0);
        Assert.assertEquals(m.getMemberUserId(), o.getMemberUserId());
        Assert.assertEquals(m.getUserid(), o.getUserid());
        Assert.assertEquals(m.getGroupid(), o.getGroupid());
        Assert.assertEquals(m.getNick(), o.getNick());
    }

    @Test
    public void selectMultTable() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        TestUser testUser = new TestUser();
        testUser.setCreatetime(d);
        testUser.setGender((byte) 1);
        testUser.setMoney(99.448d);
        testUser.setPurchase(89.345f);
        testUser.setNick("nickname");
        testUser.setUserid(query.insertForNumber(testUser)
                .longValue());
        Member m = new Member();
        m.setUserid(testUser.getUserid());
        m.setGroupid(99);
        m.setNick("membernick");
        m.setMemberUserId(query.insertForNumber(m).longValue());
        List<Member> list = query
                .mysqlList(
                        new Class[]{TestUser.class,
                                Member.class},
                        "where testuser_.userid=member_.userid and member_.userid=?",
                        0, 1,
                        new Object[]{m.getUserid()},
                        new RowMapper<Member>() {

                            public Member mapRow(ResultSet rs, int rowNum)
                                    throws SQLException {
                                Member mm = query
                                        .getRowMapper(Member.class)
                                        .mapRow(rs, rowNum);
                                TestUser tu = query.getRowMapper(
                                        TestUser.class)
                                        .mapRow(rs, rowNum);
                                mm.setTestUser(tu);
                                return mm;
                            }
                        });
        for (Member o : list) {
            Assert.assertEquals(m.getMemberUserId(), o.getMemberUserId());
            Assert.assertEquals(m.getUserid(), o.getUserid());
            Assert.assertEquals(m.getGroupid(), o.getGroupid());
            Assert.assertEquals(m.getNick(), o.getNick());
            TestUser tu = o.getTestUser();
            Assert.assertEquals(testUser.getMoney() + "", tu.getMoney()
                    + "");
            Assert.assertEquals(testUser.getNick(), tu.getNick());
            Assert.assertEquals(testUser.getUserid(), tu.getUserid());
            Assert.assertEquals(testUser.getCreatetime().getTime(), tu
                    .getCreatetime().getTime());
            Assert.assertEquals(testUser.getGender() + "", tu.getGender()
                    + "");
            Assert.assertEquals(testUser.getPurchase() + "",
                    tu.getPurchase() + "");
        }
    }

    @Test
    public void count1() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        TestUser testUser = new TestUser();
        testUser.setCreatetime(d);
        testUser.setGender((byte) 1);
        testUser.setMoney(99.448d);
        testUser.setPurchase(89.345f);
        testUser.setNick("nickname");
        testUser.setUserid(query.insertForNumber(testUser)
                .longValue());
        TestUser testUser1 = new TestUser();
        testUser1.setCreatetime(d);
        testUser1.setGender((byte) 1);
        testUser1.setMoney(99.448d);
        testUser1.setPurchase(89.345f);
        testUser1.setNick("nickname");
        testUser1.setUserid(query.insertForNumber(testUser1)
                .longValue());
        int count = query.count(TestUser.class, "where money=?",
                new Object[]{99.448d});
        Assert.assertEquals(2, count);
    }

    @Test
    public void updateSeg() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        TestUser testUser = new TestUser();
        testUser.setCreatetime(d);
        testUser.setGender((byte) 1);
        testUser.setMoney(99.448d);
        testUser.setPurchase(89.345f);
        testUser.setNick("nickname");
        testUser.setUserid(query.insertForNumber(testUser)
                .longValue());
        float money = 190.899f;
        float add = 19.89f;
        query.update(TestUser.class, "set money=? where userid=?",
                new Object[]{money, testUser.getUserid()});
        TestUser dbo = query.objById(TestUser.class, testUser.getUserid());
        Assert.assertNotNull(dbo);
        Assert.assertEquals(money + "", dbo.getMoney() + "");
        query.update(TestUser.class, "set money=money+? where userid=?",
                new Object[]{add, testUser.getUserid()});
        dbo = query.objById(TestUser.class, testUser.getUserid());
        Assert.assertNotNull(dbo);
        Assert.assertEquals((money + add) + "", dbo.getMoney() + "");
    }

    @Test
    public void deleteSeg() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        TestUser testUser = new TestUser();
        testUser.setCreatetime(d);
        testUser.setGender((byte) 1);
        testUser.setMoney(99.448d);
        testUser.setPurchase(89.345f);
        testUser.setNick("nickname");
        testUser.setUserid(query.insertForNumber(testUser)
                .longValue());
        query.delete(TestUser.class, "where userid=?",
                new Object[]{testUser.getUserid()});
        TestUser dbo = query.objById(TestUser.class, testUser.getUserid());
        Assert.assertNull(dbo);
    }

    @Test
    public void list1() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        TestUser testUser = new TestUser();
        testUser.setCreatetime(d);
        testUser.setGender((byte) 1);
        testUser.setMoney(99.448d);
        testUser.setPurchase(89.345f);
        testUser.setNick("nickname");
        testUser.setUserid(query.insertForNumber(testUser)
                .longValue());
        Assert.assertEquals(1, query.list(TestUser.class, "where userid=?",
                new Object[]{testUser.getUserid()}).size());
    }

    @Test
    public void obj1() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        TestUser testUser = new TestUser();
        testUser.setCreatetime(d);
        testUser.setGender((byte) 1);
        testUser.setMoney(99.448d);
        testUser.setPurchase(89.345f);
        testUser.setNick("nickname");
        testUser.setUserid(query.insertForNumber(testUser)
                .longValue());
        TestUser dbo = query.obj(TestUser.class, "where userid=?",
                new Object[]{testUser.getUserid()});
        Assert.assertNotNull(dbo);
    }

    @Test
    public void update() {
        User user = (User) objMap.get("user");
        user.setNick("ooo");
        user.update();
        String nick = "akweiwei";
        User.update("set nick=? where userid=?",
                new Object[]{nick, user.getUserid()});
        User dbUser = User.objById(user.getUserid());
        Assert.assertNotNull(dbUser);
        Assert.assertEquals(nick, dbUser.getNick());
    }

    @Test
    public void list() {
        User user = (User) objMap.get("user");
        List<User> list = User.list("where userid=?",
                new Object[]{user.getUserid()});
        Assert.assertEquals(1, list.size());
        list = User.list(null, null);
        if (list.isEmpty()) {
            Assert.fail("must not empty list");
        }
    }

    @Test
    public void objById() {
        User user = (User) objMap.get("user");
        User dbUser = User.objById(user.getUserid());
        Assert.assertNotNull(dbUser);
    }

    @Test
    public void obj() {
        User user = (User) objMap.get("user");
        User dbUser = User.obj("where userid=?",
                new Object[]{user.getUserid()});
        Assert.assertNotNull(dbUser);
    }

    @Test
    public void deleteById() {
        User user = (User) objMap.get("user");
        user.delete();
        User dbUser = User.objById(user.getUserid());
        Assert.assertNull(dbUser);
    }

    @Test
    public void mysqlList() {
        User user = (User) objMap.get("user");
        List<User> list = User.mysqlList("where userid=?", 0, 5,
                new Object[]{user.getUserid()});
        Assert.assertEquals(1, list.size());
        list = User.mysqlList("where sex=?", 0, 5,
                new Object[]{user.getSex()});
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void count() {
        User user = (User) objMap.get("user");
        int count = User.count("where userid=?",
                new Object[]{user.getUserid()});
        Assert.assertEquals(1, count);
        count = User.count("where sex=?",
                new Object[]{user.getSex()});
        Assert.assertEquals(2, count);
    }

    @Test
    public void update1() {
        int result = query.update(role);
        Assert.assertEquals(1, result);
    }

    @Test
    public void deleteById1() {
        int result = query.deleteById(Role.class, new Object[]{roleId});
        Assert.assertEquals(1, result);
    }

    @Test
    public void deleteWhere() {
        int result = query.delete(Role.class, "where role_id=?",
                new Object[]{roleId});
        Assert.assertEquals(1, result);
    }

    @Test
    public void map() {
        User user = (User) objMap.get("user");
        User user1 = (User) objMap.get("user1");
        Map<Long, User> map = query.map(User.class, "where sex=?", "userid",
                new Object[]{1},
                new Object[]{user.getUserid(), user1.getUserid()});
        Assert.assertNotNull(map);
        Assert.assertEquals(2, map.size());
        User u0 = map.get(user.getUserid());
        User u1 = map.get(user.getUserid());
        List<Integer> p0 = new ArrayList<Integer>();
        p0.add(1);
        List<Long> p1 = new ArrayList<Long>();
        p1.add(user.getUserid());
        p1.add(user1.getUserid());
        query.map2(User.class, "where sex=?", "userid", p0, p1);
        map = User.map2("where sex=?", "userid", p0, p1);
        Assert.assertNotNull(map);
        Assert.assertEquals(2, map.size());
        Assert.assertNotNull(u0);
        Assert.assertNotNull(u1);
        List<User> list = this.query.listInValues2(User.class, "where sex=?", "userid", "order by userid desc", p0, p1);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(user1.getUserid(), list.get(0).getUserid());
    }

    @Test
    public void updateForSnapshot1() {
        User user = (User) objMap.get("user");
        User snapshoot = Query.snapshot(user);
        user.setAddr("akweidinegd" + Math.random());
        user.setSex(null);
        user.setCreatetime(new Timestamp(System.currentTimeMillis()));
        query.update(user, snapshoot);
        User userdb = query.objById(User.class, user.getUserid());
        Assert.assertEquals(user.getSex(), userdb.getSex());
        Assert.assertEquals(user.getAddr(), userdb.getAddr());
//        Assert.assertEquals(user.getCreatetime(), userdb.getCreatetime());//竟然有误差
    }

    @Test
    public void updateForSnapshot2() {
        User user = (User) objMap.get("user");
        user.setSex(null);
        User snapshoot = Query.snapshot(user);
        user.setAddr("akweidinegd" + Math.random());
        user.setSex(null);
        user.setCreatetime(new Timestamp(System.currentTimeMillis()));
        query.update(user, snapshoot);
    }

    @Test
    public void updateForSnapshot3() {
        User user = (User) objMap.get("user");
        user.setSex(null);
        User snapshoot = Query.snapshot(user);
        user.setAddr("akweidinegd" + Math.random());
        user.setSex(UserSex.MALE.getValue());
        user.setCreatetime(new Timestamp(System.currentTimeMillis()));
        query.update(user, snapshoot);
    }

    @Test
    public void updateForSnapshotNoChange() {
        User user = (User) objMap.get("user");
        User snapshoot = Query.snapshot(user);
        query.update(user, snapshoot);
    }

    @Test
    public void testBatchInsert() throws Exception {
        int size = 5;
        List<Role> roles = new ArrayList<Role>();
        for (int i = 0; i < size; i++) {
            Role role = new Role();
            role.setCreateTime(new Date());
            roles.add(role);
        }
        List<Role> roles2 = query.batchInsert(roles);
        Assert.assertNotNull(roles2);
        Assert.assertEquals(roles.size(), roles2.size());
        for (Role r : roles2) {
            Assert.assertNotEquals(0, r.getRoleId());
        }
    }

    @Test
    public void testBatchUpdate() throws Exception {
        User user = (User) objMap.get("user");
        User user1 = (User) objMap.get("user1");
        String nick = "akwei";
        String nick1 = "akwei1";
        List<Object[]> valuesList = new ArrayList<Object[]>();
        valuesList.add(new Object[]{nick, user.getUserid()});
        valuesList.add(new Object[]{nick1, user1.getUserid()});
        int[] res = query.batchUpdate(User.class, "set nick=? where userid=?", valuesList);
        Assert.assertEquals(2, res.length);
        Assert.assertEquals(1, res[0]);
        Assert.assertEquals(1, res[1]);
        User dbUser = query.objById(User.class, user.getUserid());
        Assert.assertNotNull(dbUser);
        Assert.assertEquals(nick, dbUser.getNick());
        dbUser = query.objById(User.class, user1.getUserid());
        Assert.assertNotNull(dbUser);
        Assert.assertEquals(nick1, dbUser.getNick());
    }

    @Test
    public void testBatchDelete() throws Exception {
        User user = (User) objMap.get("user");
        User user1 = (User) objMap.get("user1");
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{user.getUserid()});
        list.add(new Object[]{user1.getUserid()});
        int[] res = query.batchDelete(User.class, "where userid=?", list);
        Assert.assertEquals(2, res.length);
        for (int i = 0; i < res.length; i++) {
            Assert.assertEquals(1, res[i]);
        }
        User dbUser = query.objById(User.class, user.getUserid());
        Assert.assertNull(dbUser);
        dbUser = query.objById(User.class, user1.getUserid());
        Assert.assertNull(dbUser);
    }

    @Test
    public void testNoId_enum() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderid(1);
        orderItem.setItemid(2);
        orderItem.setStatus(OrderItemStatus.NO);
        Number n = query.insertForNumber(orderItem);
        Assert.assertEquals(0, n.intValue());
        List<OrderItem> orderItems = query.list(OrderItem.class, null, null);
        Assert.assertEquals(1, orderItems.size());
    }

    @Test
    public void testNoIdUpdateErr() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderid(1);
        orderItem.setItemid(2);
        orderItem.setStatus(OrderItemStatus.NO);
        query.insertForNumber(orderItem);
        try {
            query.update(orderItem);
            Assert.fail("must fail for update err");
        } catch (HaloIdException e) {
        }
    }

    @Test
    public void testNoIdDeleteErr() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderid(1);
        orderItem.setItemid(2);
        orderItem.setStatus(OrderItemStatus.NO);
        try {
            query.delete(orderItem);
            Assert.fail("must fail for delete err");
        } catch (HaloIdException e) {
        }
    }

    @Test
    public void testReplace() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        User user = new User();
        user.setUserid(1603);
        user.setAddr("abddddc");
        user.setCreatetime(new Timestamp(cal.getTimeInMillis()));
        user.setIntro("intro");
        user.setNick("+++我的昵称我的昵称袁伟aabb");
        user.setSex(1);
        user.setUuid(new BigInteger("18446744073709551615"));
        user.setUuid10(1234567890123L);
        user.setUuid11(1234567890);
        user.setUuid12(new BigDecimal("1111111111"));
        user.setUuid2(23.04);
        user.setUuid3(35.09);
        user.setUuid4(10.9f);
        user.setUuid5(10.7f);
        user.setUuid6((short) 12);
        user.setUuid7(Short.valueOf("11"));
        user.setUuid8((byte) 3);
        user.setUuid9(Byte.valueOf("5"));
        user.setUsersex(UserSex.FEMALE);
        int userId = this.query.replace(user).intValue();
        Assert.assertEquals(0, userId);
        userId = this.query.insertIgnore(user).intValue();
        Assert.assertEquals(0, userId);
    }

    @Test
    public void testInsertIngore() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        User user = new User();
        user.setAddr("abddddc");
        user.setCreatetime(new Timestamp(cal.getTimeInMillis()));
        user.setIntro("intro");
        user.setNick("+++我的昵称我的昵称袁伟aabb");
        user.setSex(1);
        user.setUuid(new BigInteger("18446744073709551615"));
        user.setUuid10(1234567890123L);
        user.setUuid11(1234567890);
        user.setUuid12(new BigDecimal("1111111111"));
        user.setUuid2(23.04);
        user.setUuid3(35.09);
        user.setUuid4(10.9f);
        user.setUuid5(10.7f);
        user.setUuid6((short) 12);
        user.setUuid7(Short.valueOf("11"));
        user.setUuid8((byte) 3);
        user.setUuid9(Byte.valueOf("5"));
        user.setUsersex(UserSex.FEMALE);
        int userId = this.query.insertIgnore(user).intValue();
        Assert.assertNotEquals(0, userId);
    }

    @Test
    public void testGetMap() throws Exception {
        User user = (User) objMap.get("user");
        Map<String, Object> map = this.query.getJdbcSupport().getMap("select " +
                "* from user where userid=?", new Object[]{user.getUserid()});
        Assert.assertEquals(user.getUserid(), ((Number) map.get("userid"))
                .intValue());
        Assert.assertEquals(1, ((Number) map.get("enableflag")).intValue());
    }

    @Test
    public void testGetMapList() throws Exception {
        User user = (User) objMap.get("user");
        List<Map<String, Object>> mapList = this.query.getJdbcSupport()
                .getMapList("select " + "* from user where userid=?", new
                        Object[]{user.getUserid() + System.currentTimeMillis()});
        Assert.assertEquals(0, mapList.size());
    }

    @Test
    public void testGetMapList1() throws Exception {
        User user = (User) objMap.get("user");
        List<Map<String, Object>> mapList = this.query.getJdbcSupport()
                .getMapList("select " + "* from user where userid=?", new
                        Object[]{user.getUserid()});
        Assert.assertNotEquals(0, mapList.size());

        Map<String, Object> map = mapList.get(0);
        Assert.assertEquals(user.getUserid(), ((Number) map.get("userid"))
                .intValue());
        Assert.assertEquals(1, ((Number) map.get("enableflag")).intValue());
    }

    @Test
    public void testReplace1() {
        {
            Minfo info = new Minfo();
            info.setName("akwei");
            info.setMkey("uuk");
            this.query.insert(info);
        }
        {
            Minfo info = new Minfo();
            info.setName("akweiii");
            info.setMkey("uuk");
            this.query.replace(info);
        }
    }

    @Test
    public void testReplace2() {
        {
            Minfo info = new Minfo();
            info.setName("akwei");
            info.setMkey("uuk");
            this.query.insert(info);
        }
        {
            Minfo info = new Minfo();
            info.setName("akweiii");
            info.setMkey("uuk");
            try {
                this.query.insert(info);
                Assert.fail("must DuplicateKeyException");
            } catch (DuplicateKeyException e) {

            }
        }
    }
}
