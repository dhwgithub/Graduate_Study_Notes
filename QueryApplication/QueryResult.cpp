
#include "QueryResult.h"

ostream& print(ostream& os, const QueryResult& qr)
{
	os << qr.sought << " occors " << qr.lines->size() << " " << " times " << endl;//����ؼ��ʺͳ��ִ���
	for (auto num : *qr.lines)
	{
		os << " \t(line " << num + 1 << ")" << *(qr.file->begin() + num) << endl;//����ؼ���������
	}
	return os;
}
