#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2020/12/28 16:20
# @Author  : Hewen Deng
# @File    : add_grey.py
# @Desc    : 生成灰度图
import json
import os
import cv2
from tqdm import tqdm

from auto_invoices.split_invoices import get_json_file_list


def write_grey_img(save_dir, json_path):
    """
    制作灰度图像
    :param save_dir: 要保存灰度图像的目录
    :param json_path: 要保存的json文件路径
    :return:
    """
    src_name = os.path.basename(json_path).split(".")[0]
    name = src_name + "_GREY"
    save_json = os.path.join(save_dir, name) + ".json"
    save_jpg = os.path.join(save_dir, name) + ".jpg"

    src_img = cv2.imread(json_path.split(".")[0] + ".jpg")
    grey_img = cv2.cvtColor(src_img, cv2.COLOR_BGR2GRAY)
    cv2.imwrite(save_jpg, grey_img)

    with open(json_path, 'r', encoding="utf-8") as f:
        dicts = json.load(f)
        dicts["imagePath"] = name + ".jpg"

        with open(save_json, "w", encoding="utf-8") as f:
            f.write(json.dumps(dicts, ensure_ascii=False))


if __name__ == "__main__":
    save_dir = r"D:\Fils\CUR_WORK\stamp_dataset\stamp_datasets\grey"
    dir_path = r"D:\Fils\CUR_WORK\stamp_dataset\stamp_datasets\train"
    json_list = get_json_file_list(dir_path)

    for json_path in tqdm(json_list):
        try:
            write_grey_img(save_dir, json_path)
        except Exception:
            print("文件异常：{}".format(json_path))
