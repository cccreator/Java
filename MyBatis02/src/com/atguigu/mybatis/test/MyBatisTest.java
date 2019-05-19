package com.atguigu.mybatis.test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.beans.Employee;
import com.atguigu.mybatis.mapper.EmployeeMapper;



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
	 * һ������ SqlSession����Ļ��� �����Ͼ���SqlSession�����һ��Map
	 * ÿһ��Session�������Լ���һ������ ��������
	 * һ������Ĭ���ǿ�����
	 * ��������
	 * 		�����ݿ��һ�λỰ�ڼ� ͨ��һ��Session ��ѯ�������ݻ����һ��������
	 * �Ժ������ȡ��ͬ������ ֱ�Ӵ滺���ж���������Ҫ����sql
	 * 
	 * һ������ʧЧ���
	 * 1.Session��ͬ
	 * 2.Session��ͬ  ��ѯ������ͬ
	 * 3.SqlSession��ͬ �������β�ѯ�ڼ��������ɾ�Ĳ���
	 * 4.SqlSession ��ͬ �����ֶ������һ�λ���	
	 * 
	 * ��������
	 * ȫ�ֻ��� ����nameSpace ����Ļ��档�������棬��������Ĭ���ǹرյ���Ҫ�ֶ����ÿ���
	 * 	�������� ��
	 * 	1.һ���Ự��ѯһ������������ݾͻᱻ�ŵ���ǰ�Ự��һ��������
	 * 	2.����Ự�ύ���߹ر�,һ�������е����ݻᱣ�浽����������
	 * ʹ�ò���
	 * 1.��ȫ�������ļ��п�����������
	 * <setting name="cacheEnabled" value="true"/>
	 * 2.����Ҫ�Ķ��������sql�����ļ�������ʹ�ö�������
	 * <cache></cache>
	 * 3.���ǵ�pojo��Ҫʵ�����л��ӿ�
	 * �ͻ�����ص�����
	 * 1.cacheEnabled=false:�رյ��Ƕ������棬һ���������ɿ���ʹ��
	 * ÿһ��select��ǩ����useCache=true����
	 * ���useCache=false �����ö������� ʹ��һ������
	 * 3.ÿһ����ɾ�ı�ǩ����flushCache����
	 *  �����β�ѯ�ڼ�������ɾ�Ĳ�����һ�������涼�����
	 *  ÿһ����ɾ�ı�ǩ����flushCache=false�������Ϊtrueÿ�β�ѯ֮�󶼻�������� ������û�б�ʹ�õ�
	 *  4.clearCacheֻ�������ǰsessionһ������
	 *  5.localCacheScope:����һ������ ���ػ����������
	 *  
	 *  	SESSION �Ự�ڼ�
	 *  	STATEMENT ÿһ��sqlִ���ڼ䣬���Խ���һ������
	 *  6.�������ݵĲ���˳�� �������� -�� һ������ -�����ݿ� 
	 *  
	 *  ���ϵ������Ļ��棺ehCache
	 *  1.����ehCache��jar��
	 */
	@Test
	public void testFirstLevelCache() throws Exception{
		SqlSessionFactory ssf = getSqlSessionFactory();
		SqlSession session = ssf.openSession();
		try{
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee emp1 = mapper.getEmpById(1001);
		}finally{
			
			
		}
	}
}
