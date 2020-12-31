# -*- coding: utf-8 -*-
"""
随机生成excel规格化数据
"""
from datetime import datetime

import xlwt as xlwt
import random

from setting import excel_head
from setting import cur_line_type
from setting import gen_line_sum
import utils

global_unit = "Kg"
global_price = 0
global_tax = 0


def gen_data_fun(key):
    """
    根据关键字选择数据生成方式并返回
    :param key:
    :return:
    """
    global global_price, global_unit, global_tax
    global_price = float(global_price)
    global_tax = float(global_tax)

    if "YEAR/MONTH/DAY" == key:
        return utils.gen_data()
    elif "12_10_LEN" == key:
        return utils.gen_len_digit([12, 10])
    elif "8_LEN" == key:
        return utils.gen_len_digit([8])
    elif "ENTERPRISE" == key:
        return utils.gen_enterprise()
    elif "15_18_20_LEN" == key:
        return utils.gen_len_digit([15, 18, 20])
    elif "11_LEN" == key:
        return utils.gen_len_digit([11])
    elif "16_17_19_LEN" == key:
        return utils.gen_len_digit([16, 17, 19])
    elif "NONE" == key:
        return ""
    elif "PERSON_NAME" == key:
        return utils.gen_person_name()
    elif "PROVINCE" == key:
        return utils.gen_province()
    elif "GOOD_NAME" == key:
        good, global_unit = utils.gen_good_name_and_unit()
        return good
    elif "UNIT" == key:
        return global_unit
    elif "NUMS" == key:
        if "Kg" == global_unit:
            global_price = float("{:.2f}".format(0.1 + random.random() * 5))
        else:
            global_price = random.randint(1, 20)
        return "{:.2f}".format(global_price)
    elif "PRICE_TAX" == key:
        tax_one = float("{:.2f}".format(0.5 + random.random()))
        global_price *= tax_one
        return "{:.2f}".format(tax_one)
    elif "PRICE" == key:
        return "{:.2f}".format(global_price)
    elif "RATE" == key:
        global_tax = float("{:.2f}".format(0.1 + random.random() * 10.0))
        return "{:.2f}".format(global_tax)
    elif "TAX_SUM" == key:
        return "{:.2f}".format(global_price * global_tax / 100)


if __name__ == "__main__":
    wbk = xlwt.Workbook()  # 实例化一个excel文件
    sheet = wbk.add_sheet('Sheet1', cell_overwrite_ok=True)

    excel_head = excel_head.split("\t")
    cur_line_type = cur_line_type.split(" ")
    assert len(excel_head) == len(cur_line_type), "表头设置有误"

    # 生成头信息
    col_num = len(excel_head)  # 列数
    for i in range(col_num):
        sheet.write(0, i, str(excel_head[i]))

    # 数据生成
    for i in range(1, gen_line_sum):
        for j in range(col_num):
            sheet.write(i, j, gen_data_fun(cur_line_type[j]))

    today = datetime.today()
    today_date = datetime.date(today)

    wbk.save("./gen_excel" + str(today_date) + '.xls')
    print("生成成功")
