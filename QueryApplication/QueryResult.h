#ifndef QUERYRESULT_H
#define QUERYRESULT_H

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <memory>
#include <set>
#include <algorithm>       //������ļ�һ��Ҫ�����������һ����������������

using namespace std;
using line_no = vector<string>::size_type;

class QueryResult
{
	//��Ԫ�������
	friend ostream& print(ostream&, const QueryResult&);

public:
	//���캯��
	QueryResult(string s, shared_ptr<set<line_no>> p, shared_ptr<vector<string>> f) :
		sought(s), lines(p), file(f) {}

	auto begin() { return (*lines).begin(); }//���������������ϵĸ��������к���û��
	auto end() { return lines->end(); }//��������Ҫ�Լ����壬��߻���Ҫ
	auto get_file() { return file; }//

private:
	string sought;//��Ҫ���ҵĹؼ���
	shared_ptr<set<line_no>> lines;///����ؼ��ʳ��ֵ��кţ��кŵ��������ǹؼ��ʳ��ֵĴ���
	shared_ptr<vector<string>> file;//������ļ�
};//�ඨ��Ľ�βһ��Ҫ�ӷֺţ��״��

ostream& print(ostream&, const QueryResult&);//�������ֻ�����������������
//������������Ƕ���Ļ����ᵼ���ض��壬֮���漰���ķǳ�Ա����Ҳ������

#endif
