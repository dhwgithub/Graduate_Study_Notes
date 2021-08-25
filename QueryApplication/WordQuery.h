#ifndef WORDQUERY_H
#define WORDQUERY_H

#include "Query_base.h"
//#include "Query.h"
class Query;//同样为了避免互相调用，只声明，不引用头文件

class WordQuery : public Query_base
{
	friend class Query;
	WordQuery(const string& s) :query_word(s) {}
	QueryResult eval(const TextQuery& t) const
	{
		return t.query(query_word);
	}
	string rep() const { return query_word; }
	string query_word;
};
#endif
