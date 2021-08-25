#ifndef NOTQUERY_H
#define NOTQUERY_H

#include "Query_base.h"
#include "Query.h"

class NotQuery : public Query_base
{
	friend Query operator~(const Query&);

	NotQuery(const Query& q) : query(q) {}

	string rep() const { return "~(" + query.rep() + ")"; }
	QueryResult eval(const TextQuery&) const;

	Query query;
};
#endif
