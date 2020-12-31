#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2020/12/30 14:59
# @Author  : Hewen Deng
# @File    : select_test_dataset.py
# @Desc    : 随机挑选测试集
import glob

from tqdm import tqdm
import random
import os
import shutil


def copy_files(src_dir, file_names, save_dir):
    """
    将集合中的文件移动到指定文件夹内
    :param src_dir: 原文件目录
    :param file_names: 存储文件名的集合（仅文件名，无后缀）
    :param save_dir: 目标文件目录
    :return:
    """
    for file_name in tqdm(file_names):
        json_name = file_name + ".json"
        jpg_name = file_name + ".jpg"

        json_path = os.path.join(src_dir, json_name)
        jpg_path = os.path.join(src_dir, jpg_name)

        shutil.move(json_path, os.path.join(save_dir, json_name))
        shutil.move(jpg_path, os.path.join(save_dir, jpg_name))


if __name__ == "__main__":
    save_dir = r"D:\Fils\CUR_WORK\stamp_dataset\stamp_datasets\test"
    dir_path = r"D:\Fils\CUR_WORK\stamp_dataset\stamp_datasets\train"
    json_list = glob.glob(dir_path + "\*.json", recursive=True)

    select_num = 8000
    assert select_num < len(json_list), "设置转移文件数量太大！"
    print("共需转移文件：{}/{}\n准备移动中...".format(select_num, len(json_list)))

    exit_index = set()
    while len(exit_index) < select_num:
        index = random.randint(0, len(json_list) - 1)
        exit_index.add(os.path.basename(json_list[index]).split(".")[0])

    print("开始转移！")
    copy_files(dir_path, exit_index, save_dir)
