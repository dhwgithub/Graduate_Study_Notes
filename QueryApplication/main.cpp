#include "WordQuery.h"
#include "NotQuery.h"
#include "OrQuery.h"
#include "AndQuery.h"
#include "Query.h"

//https://blog.csdn.net/xufuqiang2017/article/details/102922767?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7Edefault-16.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7Edefault-16.control
int main()
{
	ifstream use("D:/MyDataFiles/VisualStudio/data/src.txt");
	TextQuery file(use);

	Query q = Query("is") | Query("bird") | Query("wind");
	const auto results = q.eval(file);
	cout << "Executing Query for: " << q << endl;
	print(cout, results) << endl;
	return 0;
}
