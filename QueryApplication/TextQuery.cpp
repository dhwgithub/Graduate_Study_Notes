#include "TextQuery.h"

TextQuery::TextQuery(ifstream& is) : file(new vector<string>)
{
	string text;
	while (getline(is, text))
	{
		file->push_back(text);//file分行保存读入的文本
		int n = file->size() - 1;
		istringstream line(text);
		string word;
		while (line >> word)
		{
			auto& lines = wm[word];//wm是一个map，wm.second是一个set，保存每个单词出现的行，不能重复
			if (!lines)
			{
				lines.reset(new set<line_no>);
			}
			lines->insert(n);
		}
	}
}
//这个函数是后边各种查询功能的基础
QueryResult TextQuery::query(const string& sought) const
{
	static shared_ptr<set<line_no>> nodata(new set<line_no>);
	auto loc = wm.find(sought);
	if (loc == wm.end())
		return QueryResult(sought, nodata, file);
	else
		return QueryResult(sought, loc->second, file);
}
