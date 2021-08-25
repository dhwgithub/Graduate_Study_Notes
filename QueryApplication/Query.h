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
	Query(const string& s) : q(new WordQuery(s)) {}//是为了WordQuery类准备 的

	QueryResult eval(const TextQuery& t) const
	{
		return q->eval(t);
	}//虽然q是Query_base类的，但是由于其中存在纯虚函数，所以Query_base类型的//对象不存在，所以实际是指向四种继承类之一，eval函数也是继承类重定义之后的。
	string rep() const { return q->rep(); }

private:
	Query(shared_ptr<Query_base> query) : q(query) {}//是为了And、Or、Not类准备的

	shared_ptr<Query_base> q;//由于Query_base是基类，所以他的智能指针可以指向其继承类，即后边四种
};

ostream& operator<<(ostream& os, const Query& query);//在这里只能声明，不能定义

#endif
