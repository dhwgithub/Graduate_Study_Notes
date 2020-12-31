#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2020/12/24 20:41
# @Author  : Hewen Deng
# @File    : transform_json.py
# @Desc    : 通过原始 json 文件绘制内容并展示
import json
import cv2
from shutil import copyfile
import split_invoices
from tqdm import tqdm
import os.path


def read_json_draw_rec(json_path, img_path):
    """
    通过原始 json 文件为 img 图像绘制矩形框
    :param json_path:
    :param img_path:
    :return:
    """
    with open(json_path, 'r', encoding="utf-8") as f:

        dicts = json.load(f)["figure"]  # 获取印章数据字典
        show_img = cv2.imread(img_path)

        # 遍历每一个印章数据字典
        for stamp in dicts:
            if not str(stamp["type"]).endswith("stamp"):
                continue

            x1 = int(stamp["x"])
            y1 = int(stamp["y"])
            w1 = int(stamp["w"])
            h1 = int(stamp["h"])
            cv2.rectangle(show_img, (x1, y1), (x1 + w1, y1 + h1), (0, 0, 255), 2)  # 红框

            # x2 = int(stamp["box"]["x"])
            # y2 = int(stamp["box"]["y"])
            # w2 = int(stamp["box"]["w"])
            # h2 = int(stamp["box"]["h"])
            # print(x2, y2, w2, h2)
            # cv2.rectangle(show_img, (x2, y2), (0, 0), (0, 255, 0), 2)  # 绿框

            # point1 = (int(stamp["points"][0]["x"]), int(stamp["points"][0]["y"]))
            # point2 = (int(stamp["points"][1]["x"]), int(stamp["points"][1]["y"]))
            # point3 = (int(stamp["points"][2]["x"]), int(stamp["points"][2]["y"]))
            # point4 = (int(stamp["points"][3]["x"]), int(stamp["points"][3]["y"]))
            # # print(point1, point2, point3, point4)
            # cv2.rectangle(show_img, point1, point3, (255, 0, 0), 2)  # 蓝框
            # cv2.rectangle(show_img, point2, point4, (0, 255, 0), 2)  # 绿框

        cv2.imwrite("./test.jpg", show_img)


def obj_json_draw_rec(json_path, img_path):
    """
    绘制目标图像信息
    :param json_path:
    :param img_path:
    :return:
    """
    with open(json_path, 'r', encoding="utf-8") as f:

        dicts = json.load(f)["shapes"]
        show_img = cv2.imread(img_path)

        for stamp in dicts:
            point1 = stamp["points"][0]
            point2 = stamp["points"][1]

            x1 = int(point1[0] + 0.5)
            y1 = int(point1[1] + 0.5)
            x2 = int(point2[0] + 0.5)
            y2 = int(point2[1] + 0.5)
            # print(x1, y1, x2, y2)
            cv2.rectangle(show_img, (x1, y1), (x2, y2), (0, 0, 255), 2)  # 红框

        cv2.imwrite("./test.jpg", show_img)


def gen_json_file(json_path, img_path):
    """
    转换 json 文件
    :param json_path:
    :param img_path:
    :return:
    """
    file_name = json_path.split("\\")[-1].split(".")[0]
    # print(file_name)

    # json 文件初始化
    data_dict = {"version": "4.5.6",
                 "flags": {},
                 "shapes": [],
                 "imagePath": file_name + ".jpg",
                 "imageData": None,
                 "imageHeight": -1,
                 "imageWidth": -1}

    img = cv2.imread(img_path)
    data_dict["imageHeight"] = img.shape[0]
    data_dict["imageWidth"] = img.shape[1]

    with open(json_path, 'r', encoding="utf-8") as f:
        dicts = json.load(f)["figure"]  # 获取印章数据字典

        # 遍历每一个印章数据字典
        for stamp in dicts:
            if not str(stamp["type"]).endswith("stamp"):
                continue

            dict_unit = {"label": stamp["type"], "points": [], "group_id": None, "shape_type": "rectangle", "flags": {}}

            x1 = float(stamp["x"])
            y1 = float(stamp["y"])
            w1 = float(stamp["w"])
            h1 = float(stamp["h"])
            dict_unit["points"].append([x1, y1])
            dict_unit["points"].append([x1 + w1, y1 + h1])

            data_dict["shapes"].append(dict_unit)

    # 写入 json 文件
    json_data = json.dumps(data_dict, ensure_ascii=False)
    with open(r"D:\Fils\CUR_WORK\stamp_dataset\Dataset\{}.json".format(file_name), "w", encoding="utf-8") as f:
        f.write(json_data)
    with open(r"D:\Fils\CUR_WORK\YOLOv4_tensorflow-master\data\JPEGImages\pre_train\{}.json".format(file_name), "w", encoding="utf-8") as f:
        f.write(json_data)

    copyfile(img_path, r"D:\Fils\CUR_WORK\stamp_dataset\Dataset\{}.jpg".format(file_name))
    copyfile(img_path, r"D:\Fils\CUR_WORK\YOLOv4_tensorflow-master\data\JPEGImages\pre_train\{}.jpg".format(file_name))


if __name__ == "__main__":
    # json_path = r"D:\Fils\CUR_WORK\temp\auto_invoices\src_json\a1DA2dhxbsepmNkOay.json"
    # jpg_path = r"D:\Fils\CUR_WORK\temp\auto_invoices\src_json\a1DA2dhxbsepmNkOay.jpg"
    # read_json_draw_rec(json_path, jpg_path)

    # json_path = r"D:\Fils\CUR_WORK\temp\auto_invoices\to_json\A0kRxoB8YmCVHcptlT.json"
    # jpg_path = r"D:\Fils\CUR_WORK\temp\auto_invoices\to_json\A0kRxoB8YmCVHcptlT.jpg"
    # obj_json_draw_rec(json_path, jpg_path)

    # json_path = r"D:\Fils\CUR_WORK\stamp_dataset\invoices_res\invoices\MmTQC5cENVDHXlkUa1.json"
    # img_path = r"D:\Fils\CUR_WORK\stamp_dataset\invoices_res\invoices\MmTQC5cENVDHXlkUa1.jpg"
    # gen_json_file(json_path, img_path)

    dir_path = r"D:\Fils\CUR_WORK\stamp_dataset\invoices_res\invoices"
    json_list = split_invoices.get_json_file_list(dir_path)
    exit_num = 0
    for json_path in tqdm(json_list):
        try:
            img_path = json_path.split(".")[0] + ".jpg"
            exit_path = os.path.join(r"D:\Fils\CUR_WORK\stamp_dataset\Dataset\TestData", os.path.basename(json_path))
            if os.path.exists(exit_path):
                exit_num += 1
                continue
            gen_json_file(json_path, img_path)
        except Exception:
            print("异常信息：{}".format(json_path))
    print("exit_file:{}".format(exit_num))
