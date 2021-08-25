#ifndef QUERY_H
#define QUERY_H

#include "TextQuery.h"
#include "Query_base.h"
#include "WordQuery.h"

class Query
{
	friend Query operator~(const Query&);
	friend Query operator|(const Query&, const Query&);
	friend Query operator&(const Query&, const Query&);

public:
	Query(const string& s) : q(new WordQuery(s)) {}//��Ϊ��WordQuery��׼�� ��

	QueryResult eval(const TextQuery& t) const
	{
		return q->eval(t);
	}//��Ȼq��Query_base��ģ������������д��ڴ��麯��������Query_base���͵�//���󲻴��ڣ�����ʵ����ָ�����ּ̳���֮һ��eval����Ҳ�Ǽ̳����ض���֮��ġ�
	string rep() const { return q->rep(); }

private:
	Query(shared_ptr<Query_base> query) : q(query) {}//��Ϊ��And��Or��Not��׼����

	shared_ptr<Query_base> q;//����Query_base�ǻ��࣬������������ָ�����ָ����̳��࣬���������
};

ostream& operator<<(ostream& os, const Query& query);//������ֻ�����������ܶ���

#endif
