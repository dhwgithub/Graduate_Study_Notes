#!/usr/bin/python
# coding=utf-8
"""
将xls/excel转换为json格式
"""
import json
import xlrd

data = xlrd.open_workbook('客户库.xls')  # 读取excel文件
table = data.sheet_by_name(data.sheet_names()[0])  # 获取第一个工作表

row_num = table.nrows
col_num = table.ncols

print("总行数：" + str(table.nrows))
print("总列数：" + str(table.ncols))
# print("第1行第1列：" + table.cell(0, 0).value)

data_dict = {}
for line_num in range(1, row_num):
    key = table.cell(line_num, 2).value
    value = table.cell(line_num, 1).value
    data_dict[key] = [value]

# # 显示字典内容
# print(data_dict)

# 写入 json 文件
json_data = json.dumps(data_dict, ensure_ascii=False)
with open(r"./客户库.json", "w", encoding="utf-8") as f:
    f.write(json_data)


