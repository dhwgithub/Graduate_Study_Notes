#include "NotQuery.h"

Query operator~(const Query& operand)
{
	return shared_ptr<Query_base>(new NotQuery(operand));
}
QueryResult NotQuery::eval(const TextQuery& text) const
{
	auto result = query.eval(text);
	auto ret_lines = make_shared<set<line_no>>();
	auto beg = result.begin(), end = result.end();//这里的begin、end以及get_file函数用到了我们之前自己定义的
	auto sz = result.get_file()->size();       //QueryResult类中的函数
	for (size_t n = 0;n != sz;++n)
	{
		if (beg == end || *beg != n)
			ret_lines->insert(n);
		else if (beg != end)
			++beg;
	}
	return QueryResult(rep(), ret_lines, result.get_file());
}
