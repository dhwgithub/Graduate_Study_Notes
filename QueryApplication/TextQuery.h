#ifndef TEXTQUERY_H
#define TEXTQUERY_H

#include "QueryResult.h"
#include <map>

class TextQuery
{

public:
	TextQuery(ifstream&);
	QueryResult query(const string&) const;

private:
	shared_ptr<vector<string>> file;
	map<string, shared_ptr<set<line_no>>> wm;
};

#endif
