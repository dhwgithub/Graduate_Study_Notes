#ifndef QUERY_BASE_H
#define QUERY_BASE_H

//#include "Query.h"
#include "TextQuery.h"

class Query;//这里是只声明就可以，一定不能引用头文件，否则会造成两个类互相调用的情况，后边也有相似的情况

class Query_base //这也是分文件定义类引入的麻烦之一
{
	friend class Query;

protected:
	virtual ~Query_base() = default;

private:
	virtual QueryResult eval(const TextQuery&) const = 0;//这两个都是纯虚函数
	virtual string rep() const = 0;
};

#endif
