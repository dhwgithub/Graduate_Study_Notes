#ifndef BINARYQUERY_H
#define BINARYQUERY_H

#include "Query_base.h"
#include "Query.h"

class BinaryQuery : public Query_base
{
protected:
	BinaryQuery(const Query& left, const Query& right, string s) :
		lhs(left), rhs(right), opSym(s) {}

	string rep() const { return "(" + lhs.rep() + " " + opSym + " " + rhs.rep() + ")"; }

	Query lhs, rhs;
	string opSym;
};

#endif
