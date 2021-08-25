#ifndef QUERY_BASE_H
#define QUERY_BASE_H

//#include "Query.h"
#include "TextQuery.h"

class Query;//������ֻ�����Ϳ��ԣ�һ����������ͷ�ļ����������������໥����õ���������Ҳ�����Ƶ����

class Query_base //��Ҳ�Ƿ��ļ�������������鷳֮һ
{
	friend class Query;

protected:
	virtual ~Query_base() = default;

private:
	virtual QueryResult eval(const TextQuery&) const = 0;//���������Ǵ��麯��
	virtual string rep() const = 0;
};

#endif
