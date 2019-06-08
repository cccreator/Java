package com.atguigu.mybatis.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.beans.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;

public class MyBatisTest {
	/**
	 * helloWorld��С��:
	 * 
	 * 1.����Mybatis��jar��
	 * 2.����һ��ȫ�������ļ�  mybatis-config.xml ,����ȫ�������ļ���������һ��SqlSessionFactory����.
	 * 3.����һ��sqlӳ���ļ�, EmployeeMapper.xml,�������ļ���������sql���.
	 * 4.��sqlӳ���ļ�ע�ᵽȫ�������ļ���
	 * 5.��SqlSessionFactory�л�ȡSqlSession����. sqlSession��������ݿ��һ�λỰ.
	 * 	 Ȼ�����selectOne("sql����Ψһ��ʶ",ִ��sql�Ĳ���)��ɲ�ѯ����.
	 * 6.���SqlSession����ر�.�ͷ���Դ.
	 */
	
	@Test
	public void  testMyBatis() throws Exception{
		//����SqlSessionFactory����.
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = 
				new SqlSessionFactoryBuilder().build(inputStream);
		
		//��ȡSqlSession����
		SqlSession session = sqlSessionFactory.openSession();
		
		//��ʼ��ɾ�Ĳ�.
		try {
			/**
			 *selectOne ������������˼:
			 *	1. sql����Ψһ��ʶ
			 *  2. ִ��sql����Ĳ���
			 *  
			 */
			Employee employee = 
					session.selectOne("myBatis.day01.suibian.selectEmployee", 1001);
		
			System.out.println(employee);
		} finally {
			//�ر�session
			session.close();
		}
	}
	
	
	/**
	 *  Mapper�ӿڵĺô�:
	 *  	1.�ӿ��ж���ķ�������ȷ������Լ��(��������������   ��������ֵ������)
	 *      2.�ӿڱ���:
	 *      	�ӿڱ�����ǳ���.����˹淶.��ǿ��Ҫ������������ʵ��.����ʹ��jdbc��hibernate��Mybatis.
	 *      	�ӿڽ��淶������ʵ�ַ���.
	 *  Mapper�ӿڿ����� MyBatis��Ϊ�ӿ����ɴ���ʵ���ࡣ���������ɾ������ɾ�Ĳ����.
	 *  ��ײ㻹��ʹ��selectOne,update�ȷ�������ɵ�.
	 *  
	 *  Mapper�ӿڿ�����Ҫע��:
	 *  	1.Mapper�ӿ�Ҫ��sqlӳ���ļ���̬��. sqlӳ���ļ���namespaceָ���ɽӿڵ�ȫ����.
	 *      2.Mapper�ӿڷ�����sqlӳ���ļ���sql���󶨡�  sql����idֵָ���ɽӿ��еķ�����.
	 */
	
	@Test
	public void testMapperInterface() throws Exception{
		//����SqlSessionFactory����.
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = 
				new SqlSessionFactoryBuilder().build(inputStream);
		
		//��ȡSqlSession����
		SqlSession session = sqlSessionFactory.openSession();
		
		//Mapper�ӿ� -->CRUD���� --- >�����ʵ����???(��̬����)
		try {
			//��ȡMapper�ӿڵĴ���ʵ����
			EmployeeMapper mapper =  session.getMapper(EmployeeMapper.class);
			System.out.println(mapper.getClass().getName());
			
			//ִ�д������е���ɾ�Ĳ�
			Employee employee = mapper.getEmpById(1001);
			System.out.println(employee);
		} finally{
			session.close();
		}
	}
	
	
	//����ȡSqlSessionFactory�Ĵ����װ��һ������
	public SqlSessionFactory  getSqlSessionFactory() throws Exception{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = 
				new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	
	@Test
	public void testCRUD() throws Exception{
		SqlSessionFactory ssf = getSqlSessionFactory();
		SqlSession session = ssf.openSession();  // ���Զ��ύ��session
		try {
			//��ȡMapper�ӿڵĴ���ʵ�������
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			
			//���Ա����Ϣ
			Employee employee = new Employee();
			employee.setLastName("BB");
			employee.setGender("m");
			employee.setEmail("bb@atguigu.com");
			boolean result = mapper.addEmp(employee);
			System.out.println(result);
			
			//��ȡ����ֵ
			System.out.println("����ֵ:" + employee.getId());
			
			//�޸�Ա����Ϣ
//			Employee employee = new Employee();
//			employee.setId(1004);
//			employee.setLastName("RoseAA");
//			employee.setGender("m");
//			employee.setEmail("rose@sina.com");
//			mapper.updateEmp(employee);
			
			//ɾ��Ա����Ϣ
			//mapper.deleteEmp(1004);
			
			
			//������ɾ�Ĳ�����Ҫ�����ύ
			session.commit();
		} finally{
			session.close();
		}
	}
	
	
	@Test
	public void testParameters() throws Exception{
		SqlSessionFactory ssf = getSqlSessionFactory();
		SqlSession session = ssf.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			
			Employee employee = mapper.getEmpByIdAndLastName(1001, "Tom");
			System.out.println(employee);
			
		}finally{
			session.close();
		}
	}
	
	@Test
	public void testMap() throws Exception{
		SqlSessionFactory ssf = getSqlSessionFactory();
		SqlSession session = ssf.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("aa", 1001);  // id 
			map.put("bb", "Tom"); // lastName
			map.put("tableName", "tbl_employee");
			Employee employee = mapper.getEmpByMap(map);
			System.out.println(employee);
			
		}finally{
			session.close();
		}
	}

}
