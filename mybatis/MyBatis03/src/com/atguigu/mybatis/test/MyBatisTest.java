package com.atguigu.mybatis.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.beans.Employee;
import com.atguigu.mybatis.mapper.EmployeeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class MyBatisTest {

	//����ȡSqlSessionFactory�Ĵ����װ��һ������
	public SqlSessionFactory  getSqlSessionFactory() throws Exception{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = 
				new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	/**
	 * ��������
	 * 
	 * һ�����棨���ػ��棩 
	 * 		SqlSession����Ļ���,�����Ͼ���SqlSession�����һ��map��
	 *      ÿһ��SqlSession�������Լ���һ������.��������.
	 *      һ��������Ĭ�Ͽ�����
	 *      ��������: �����ݿ��һ�λỰ�ڼ䣨ͨ��һ��SqlSession��,��ѯ�������ݻ����һ��������.
	 *              �Ժ������Ҫ��ȡ��ͬ������,ֱ�Ӵӻ����л�ȡ,������Ҫ����sql�����ݿ��ѯ.
	 * һ������ʧЧ�����:
	 * 		1.SqlSession��ͬ.     	
	 * 		2.SqlSession��ͬ,���ǲ�ѯ������ͬ.  
	 * 		3.SqlSession��ͬ,���������β�ѯ�ڼ�ִ������ɾ�ĵĲ���.
	 * 		4.SqlSession��ͬ,�ֶ������һ������.
	 * 
	 * �������棨ȫ�ֻ��棩 ����namespace����Ļ���. ��������Ĭ���ǹرյģ������ֶ����ÿ���.
	 * 		��������:
	 * 		1.һ���Ự(SqlSession)����ѯһ������,������ݾͻᱻ���ڵ�ǰ�Ự��һ��������.
	 * 		2.����Ự�ύ���߹ر�,һ�������е����ݻᱻ���浽����������.
	 * 	
	 * 		ʹ�ò���:
	 * 		1.��ȫ�������ļ��п�����������:
	 * 		   <setting name="cacheEnabled" value="true"/>
	 *		2.����Ҫʹ�ö��������sqlӳ���ļ�������ʹ�ö�������:
	 *		   <cache></cache>
	 *		3.���ǵ�POJO��Ҫʵ�����л��ӿ�.
	 *
	 * �ͻ�����ص�����:
	 * 1. cacheEnabled=false:�رյ��Ƕ�������,һ���������ɿ���ʹ��.
	 * 2. ÿһ��select��ǩ����useCache=true����.
	 * 	    ���useCache=false:  ��ʹ�ö������棬����ʹ��һ������.
	 * 3. ÿһ����ɾ�ı�ǩ����flushCache=true����:
	 * 	   �����β�ѯ�ڼ�������ɾ�Ĳ���,һ�������涼�����.
	 *    ÿһ����ѯ��ǩ����flushCache=false,�����Ϊtrue,ÿ�β�ѯ֮�󶼻�������棬������û�б�ʹ�õ�.
	 * 4.sqlSession.clearCache(): ֻ����յ�ǰsession��һ������.
	 * 
	 * 5.localCacheScope:����һ������(���ػ���)��������
	 * 		SESSION :�Ự�ڼ�
	 *  	STATEMENT:  ���Խ���һ������.
	 * 	
	 * �������ݵĲ���˳��:  ��һ��.
	 *  ��������
	 * 	һ������ 	  
	 *  ���ݿ�
	 * 
	 * ���ϵ������Ļ���:  EhCache
	 * 	1.����Ehcache��jar��  �Լ������
	 * 	  ehcache-core-2.6.8.jar
		  mybatis-ehcache-1.0.3.jar
          slf4j-api-1.6.1.jar
          slf4j-log4j12-1.6.2.jar
	 *  2.����Ehcache�������ļ�
	 *    
	 * 	3.��ӳ���ļ�������ʹ��Ehcache
	 * 	  <cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
	 */
	@Test
	public void testFirstLevelCache()throws Exception{
		SqlSessionFactory ssf = getSqlSessionFactory();
		SqlSession session = ssf.openSession();
		//SqlSession session2 = ssf.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			//EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);
			
			Employee emp1 = mapper.getEmpById(1001);
			System.out.println("emp1:" + emp1);
			System.out.println("-------------------------------");
			//���
			
//			mapper.addEmp(new Employee("Jerry","f", "jerry@sina.com"));
//			session.commit();
			
			//��������е�����
			//session.clearCache();
			
			Employee emp2 = mapper.getEmpById(1001);
			System.out.println("emp2:"+ emp2);
			
		}finally{
			session.close();
		}
	}
	
	
	@Test
	public void testSecondLevelCache()throws Exception{
		SqlSessionFactory ssf = getSqlSessionFactory();
		SqlSession session = ssf.openSession();
		SqlSession session2 = ssf.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);
			
			Employee emp1 = mapper.getEmpById(1001);
			System.out.println("emp1:" + emp1);
			System.out.println("-------------------------------");
			session.commit(); //session.close();
			
			//session.clearCache();
			//���
//			mapper.addEmp(new Employee("White","f", "white@sina.com"));
//			session.commit();
			
			Employee emp2 = mapper2.getEmpById(1001);
			System.out.println("emp2:"+ emp2);
			
		}finally{
			session.close();
			session2.close();
		}
	}
	
	@Test
	public void testPageHelper()throws Exception{
		SqlSessionFactory ssf = getSqlSessionFactory();
		SqlSession session = ssf.openSession();
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			//��ѯ֮ǰ���÷�ҳ��Ϣ
			Page<Object> page = PageHelper.startPage(9,1);  //��ʾ�ڼ�ҳ,ÿҳ��ʾ������
			List<Employee> emps = mapper.getEmps();
			//��ѯ֮���ȡ��ҳ��Ϣ
			PageInfo<Employee> info = new PageInfo<Employee>(emps,5);
			
			for (Employee employee : emps) {
				System.out.println(employee);
			}
			//��ȡ��ҳ��ص���Ϣ
			System.out.println("��ǰҳ��:"+page.getPageNum());
			System.out.println("�ܼ�¼��:"+page.getTotal());
			System.out.println("��ҳ��:"+page.getPages());
			System.out.println("ÿҳ��ʾ�ļ�¼��:"+page.getPageSize());
			
			System.out.println("------------------------");
			
			System.out.println("��ǰҳ��:"+info.getPageNum());
			System.out.println("�ܼ�¼��:"+info.getTotal());
			System.out.println("��ҳ��:"+info.getPages());
			System.out.println("ÿҳ��ʾ�ļ�¼��:"+info.getPageSize());
			System.out.println("�Ƿ��ǵ�һҳ:"+ info.isIsFirstPage());
			System.out.println("�Ƿ������һҳ:"+info.isIsLastPage());
			System.out.println("�Ƿ�����һҳ:"+info.isHasPreviousPage());
			System.out.println("�Ƿ�����һҳ:"+info.isHasNextPage());
			
			//��ҳ�߼�
			System.out.println("������ʾ��ҳ��:");
			int [] nums = info.getNavigatepageNums();
			for (int i : nums) {
				System.out.print(i + " ");
			}
			System.out.println();
		}finally{
			session.close();
		}
	}
	
	
	
}
