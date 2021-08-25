#include "Query.h"
#include "WordQuery.h"
ostream& operator<<(ostream& os, const Query& query)
{
	return os << query.rep();
}
