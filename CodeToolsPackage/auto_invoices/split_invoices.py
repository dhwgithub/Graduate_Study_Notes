#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2020/12/24 8:52
# @Author  : Hewen Deng
# @File    : split_invoices.py
# @Desc    : 解析json并分类小票
import glob
import json
import os.path
import random
import string
from tqdm import tqdm
from shutil import copyfile


def is_have_invoices(json_path):
    """
    通过 json 文件路径查询是否含有 stamp
    路径查询：figure/type
    :param json_path:
    :return:
    """
    try:
        with open(json_path, 'r', encoding="utf-8") as f:
            data = json.load(f)["figure"]
        for type_obj in data:
            if str(type_obj["type"]).endswith("stamp"):
                return True
        return False
    except Exception:
        print("【判断小票】异常文件：{}".format(json_path))
        return False


def get_abs_path(rel_path):
    """
    获取当前绝对路径
    :param rel_path:
    :return:
    """
    return os.path.join(os.path.abspath("."), rel_path)


def get_random_name(num=18):
    """
    生成以字母开头的 num 位字符串
    :param num:
    :return:
    """
    return random.sample(string.ascii_letters, 1)[0] + ''.join(random.sample(string.ascii_letters + string.digits, num-1))


def copy_file(json_path, to_dir_path):
    """
    将指定 json 文件和同名 jpg 文件复制到指定文件夹并重命名
    :param json_path:
    :param to_dir_path:
    :return:
    """
    try:
        json_file = json_path
        jpg_file = os.path.join("\\".join(str(json_path).split("\\")[:-1]),
                                ".".join(str(json_path).split("\\")[-1].split(".")[:-1]) + ".jpg")

        file_name = get_random_name()
        last_path = os.path.join(to_dir_path, file_name)
        while os.path.exists(last_path + ".json"):
            file_name = get_random_name()
            last_path = os.path.join(to_dir_path, file_name)

        copyfile(json_file, last_path + ".json")
        copyfile(jpg_file, last_path + ".jpg")
    except Exception:
        print("【复制文件】异常文件：{}".format(json_path))


def get_json_file_list(dir_path):
    """
    获取当前文件夹内所有 json 文件路径
    若提供路径最后含 "\**" 则递归查找
    :param dir_path:
    :return:
    """
    return glob.glob(dir_path + "\*.json", recursive=True)


if __name__ == "__main__":
    # 获取所有 json 文件路径
    dir_path = r"C:\Users\Administrator\Desktop\stamp_dataset\**"
    json_list = get_json_file_list(dir_path)
    print("共有 json 文件：{}".format(len(json_list)))

    for json_path in tqdm(json_list):
        # 判断是否有指定类型参数
        if is_have_invoices(json_path):
            to_dir_path = get_abs_path("invoices")
        else:
            to_dir_path = get_abs_path("no_invoices")
        copy_file(json_path, to_dir_path)
    pass
