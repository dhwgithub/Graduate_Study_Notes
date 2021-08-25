#ifndef QUERYRESULT_H
#define QUERYRESULT_H

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <memory>
#include <set>
#include <algorithm>       //这个库文件一定要包括，后边有一个函数定义在其中

using namespace std;
using line_no = vector<string>::size_type;

class QueryResult
{
	//友元输出函数
	friend ostream& print(ostream&, const QueryResult&);

public:
	//构造函数
	QueryResult(string s, shared_ptr<set<line_no>> p, shared_ptr<vector<string>> f) :
		sought(s), lines(p), file(f) {}

	auto begin() { return (*lines).begin(); }//这三个函数在书上的给定程序中好像并没有
	auto end() { return lines->end(); }//给出，需要自己定义，后边会需要
	auto get_file() { return file; }//

private:
	string sought;//需要查找的关键词
	shared_ptr<set<line_no>> lines;///保存关键词出现的行号，行号的数量即是关键词出现的次数
	shared_ptr<vector<string>> file;//读入的文件
};//类定义的结尾一定要加分号，易错点

ostream& print(ostream&, const QueryResult&);//输出函数只允许在这里进行声明
//如果不声明而是定义的话，会导致重定义，之后涉及到的非成员函数也是这样

#endif
