import random


def gen_enterprise():
    """
    随机生成全国各行业公司名称（不存在）
    :return:
    """
    pre = "中国、贵州、中化、青海盐湖、沪、辽通、湖北、山东、唐山、南京、渤海、青岛、昆山、" \
          "江苏、齐鲁、大沽、锦化、恒通、江东、宜宾、四川、沈阳、一汽、上汽、东风、长安、北汽、" \
          "广州、华晨、万向、安徽、上海、徐州、哈尔滨、广西、大连、珠海、华立、正泰、许继、柯达、" \
          "中联、永鼎、佛山、内蒙古、杭州、天正、宝钢、鞍钢、武钢、金川、铜陵、湖南、海亮、云南、" \
          "包头、豫港、抚顺、大冶、烟台、宁波、河南、安阳、陕西、柳州市、新疆、秦皇岛、苏宁、国美、" \
          "华润、珠江、福建、金狮、光明、黑龙江、淮南、工商、交通、浦东、民生、海尔、康佳、国家、中外".split("、")
    mid = "石化、石油、工业、化工、三友、碱业、汽车、重汽、工程机械、东方电气、机床、远东、普立华、广告、" \
          "东方、铜业、有色金属、铝业、铜业、春兴、冶炼、豫光、八一、东岭锌业、宏达、水泥、天山水泥、文化传播、" \
          "塔牌、金隅、玻璃、华尔润、北方、电器、超市、三联、啤酒、伊利、蒙牛、三鹿、乳业、葡萄酒、网络科技、" \
          "老窖、杏花村汾酒、沱牌、天府、广电、长虹电子、开发投资、对外贸易、海云、轮船、国际海运、科技".split("、")
    last = "总公司、股份有限公司、分公司、有限公司、集团、有限责任公司、(集团)公司、公司、冶炼厂、" \
           "控股有限公司、生产基地、银行、实业、投资公司、电力".split("、")
    return random.choice(pre) + random.choice(mid) + random.choice(last)


def gen_province(is_province2=False, is_province3=False):
    """
    随机生成省份
    可选生成自治区、特别行政区
    :param is_province2:
    :param is_province3:
    :return:
    """
    province1 = "河北省、山西省、辽宁省、吉林省、黑龙江省、江苏省、浙江省、安徽省、福建省、江西省、" \
                "山东省、河南省、湖北省、湖南省、广东省、海南省、四川省、贵州省、云南省、陕西省、" \
                "甘肃省、青海省、台湾省".split("、")
    province2 = "内蒙古自治区、广西壮族自治区、西藏自治区、宁夏回族自治区、新疆维吾尔自治区".split("、")
    province3 = "香港特别行政区、澳门特别行政区".split("、")
    if is_province2:
        province1 += province2
    if is_province3:
        province1 += province3
    return random.choice(province1)


def gen_len_digit(len_list):
    """
    生成任意给定列表数字长度的字符串（第一个数字非 0）
    :param len_list:
    :return:
    """
    assert isinstance(len_list, list), "需要传入列表类型变量"

    gen_len = random.choice(len_list)
    data = str(random.randint(1, 9))
    for i in range(1, gen_len):
        data += str(random.randint(0, 9))
    return data


def gen_data(y_start=2016, y_end=2020):
    """
    生成在任意年区间的日期
    :param y_start:
    :param y_end:
    :return:
    """
    assert y_start <= y_end, "年份区间错误"

    year = random.randint(y_start, y_end)
    month = random.randint(1, 12)
    if month == 2:
        if year % 4 == 0 and year % 3200 != 0 and (year % 400 == 0 or year % 100 != 0):
            day = str(random.randint(1, 29)).zfill(2)
        else:
            day = str(random.randint(1, 28)).zfill(2)
    elif month in [1, 3, 5, 7, 8, 10, 12]:
        day = str(random.randint(1, 31)).zfill(2)
    else:
        day = str(random.randint(1, 30)).zfill(2)
    return str(year).zfill(4) + "/" + str(month).zfill(2) + "/" + str(day).zfill(2)


def gen_person_name():
    """
    随机生成 2-3 个字的名字
    :return:
    """
    name = "赵 钱 孙 李 周 吴 郑 王 冯 陈 褚 卫 蒋 沈 韩 杨 朱 秦 尤 许 " \
           "何 吕 施 张 孔 曹 严 华 金 魏 陶 姜 戚 谢 邹 喻 柏 水 窦 章 " \
           "江 童 颜 郭 梅 盛 林 刁 钟 徐 邱 骆 高 夏 蔡 田 樊 胡 凌 霍 " \
           "姚 邵 湛 汪 祁 毛 禹 狄 米 贝 明 臧 计 伏 成 戴 谈 宋 茅 庞".split(" ")
    surname = "子、俊、梓、浩、泽、宇、思、博、佳、皓、金、伟、景、嘉、彦、家、奕、晨、" \
              "文、瑞、振、明、建、杰、海、天、铭、睿、炅、书、润、圣、培、承、恩、旭、" \
              "柏、志、恒、佳、梦、思、梓、雅、欣、诗、若、子、秋、雪、怡、月、楚、美、" \
              "雨、佩、金、婉、依、海、晨、钰、淑、琳、倩、雯、嘉、慧、涵、玉、文、静、" \
              "颖、婷、晓、舒、洁、紫、蓉、艺、宇、骄".split("、")
    data = random.choice(surname)
    if random.choice([True, False]):
        data += random.choice(surname)
    return random.choice(name) + data


def gen_good_name_and_unit():
    """
    随机返回元组形式的 物品+单位
    :return:
    """
    goods = "苹果 沙果 海棠 野樱莓 枇杷 欧楂 山楂 梨 香梨 雪梨 温柏 蔷薇果 花楸 杏 樱桃 桃 水蜜桃 油桃 " \
            "蟠桃 李子 梅子 青梅 西梅 白玉樱桃 黑莓 覆盆子 云莓 罗甘莓 白里叶莓 草莓 菠萝莓 橘子 砂糖桔 " \
            "橙子 柠檬 青柠 柚子 金桔 葡萄柚 香橼 佛手 指橙 黄皮果 西瓜 哈密瓜 香瓜 白兰瓜 刺角瓜 " \
            "金铃子 火龙果 黄龙果 红心火龙果 仙人掌果 水稻 玉米 青稞 蚕豆 小麦 白酒 红酒 果皮 奶茶 奶粉 " \
            "奶酪 订书机 起钉器 打孔器 剪刀 美工刀 切纸刀 票夹 削笔刀 胶棒 胶水 胶带 胶带座 计算器 仪尺 " \
            "笔筒 笔袋 台历架 光盘 U盘 键盘 鼠标 移动硬盘 录音笔 插线板 电池 耳麦 光驱 读卡器 存储卡 " \
            "电脑 投影仪 复印机 传真机 打印机 多功能一体机 扫描仪 相机 摄像机 交换机 路由器 猫 加湿器 " \
            "饮水机 电风扇 吸尘器 文件柜 更衣柜 多屉柜 杂柜 保险柜 办公桌 办公椅 毛衣 衬衣 外套 羽绒服 " \
            "西服 胸罩 裘皮 马夹 T恤 背心 卫衣 大衣 夹克衫 皮夹克 短裤 内裤 长裤 西裤 背带裤 牛仔裤 中裤 " \
            "短裙 长裙 连身装 母子装 袍子 吊带裙".split(" ")
    units = "Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg " \
            "Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg Kg " \
            "Kg Kg Kg Kg Kg Kg ml ml ml 杯 袋 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 " \
            "个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 个 " \
            "个 个 个 个 个 个 个 个 件 件 件 件 件 件 件 件 件 件 件 件 件 件 件 件 件 件 件 件 " \
            "件 件 件 件 件 件 件".split(" ")
    index = random.randint(0, len(goods) - 1)
    return goods[index], units[index]
