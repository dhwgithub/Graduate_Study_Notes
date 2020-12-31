#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2020/12/26 17:44
# @Author  : Hewen Deng
# @File    : delete_problems_file.py
# @Desc    : 去除有问题的文件
import os


def delete_problems_file(problems_path):
    """删除指定路径的文件"""
    if os.path.exists(problems_path):
        os.remove(problems_path)
    else:
        print("文件{}不存在！".format(problems_path))


if __name__ == "__main__":
    problems_file = r"D:\Fils\CUR_WORK\temp\auto_invoices\problems_file.txt"
    with open(problems_file, "r", encoding="utf-8") as f:
        for line in f.readlines():
            line = line[:-1]
            if line.startswith("获取标签异常文件："):
                jpg_path = line[9:]
                json_path = jpg_path.split(".")[0] + ".json"
                delete_problems_file(jpg_path)
                delete_problems_file(json_path)
    pass
