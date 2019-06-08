package com.atguigu.mybatis.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.beans.Employee;

//�������Ϊjdbc��dao��д�� XxxxDao.
public interface EmployeeMapper {

	//����id��ѯ��Ӧ��Ա����Ϣ
	public Employee getEmpById(Integer id );
	
	//���Ա����Ϣ
	/**
	 * tips: �����Ҫ��������ɾ�Ĳ������ȡ�������ݿ��Ӱ������,
	 * 		 ����ֱ���ڽӿ��з����ķ���ֵ���弴��.
	 */
	public boolean addEmp(Employee employe);
	
	//�޸�Ա����Ϣ
	public void updateEmp(Employee employee);
	
	//����idɾ��Ա����Ϣ
	public void deleteEmp(Integer id );
	
	//���ݶ������
	public Employee getEmpByIdAndLastName(@Param("id")Integer id , @Param("lastName")String lastName);
		
	public Employee getEmpByMap(Map<String,Object> map );
}
