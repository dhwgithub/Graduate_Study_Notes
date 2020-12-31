# 表头设置
excel_head = r"日期	发票代码	发票号码	购货方|名称	购货方|纳税人识别号	购货方|地址电话	" \
             r"购货方|开户行账号	销货方|名称	销货方|纳税人识别号	销货方|地址电话	销货方|开户行账号	" \
             r"机器编号	备注	开票人	收款人	复核人	省份	发票类型	" \
             r"1|货物名称	1|规格	1|单位	1|数量	1|含税单价	金额	1|税率%	税额	" \
             r"2|货物名称	2|规格	2|单位	2|数量	2|含税单价	金额	2|税率%	税额	" \
             r"3|货物名称	3|规格	3|单位	3|数量	3|含税单价	金额	3|税率%	税额"
# 类型关键字设置
cur_line_type = r"YEAR/MONTH/DAY 12_10_LEN 8_LEN ENTERPRISE 15_18_20_LEN 11_LEN " \
                r"16_17_19_LEN ENTERPRISE 15_18_20_LEN 11_LEN 16_17_19_LEN " \
                r"NONE NONE PERSON_NAME PERSON_NAME PERSON_NAME PROVINCE NONE " \
                r"GOOD_NAME UNIT UNIT NUMS PRICE_TAX PRICE RATE TAX_SUM " \
                r"GOOD_NAME UNIT UNIT NUMS PRICE_TAX PRICE RATE TAX_SUM " \
                r"GOOD_NAME UNIT UNIT NUMS PRICE_TAX PRICE RATE TAX_SUM"
# 生成数据行设置
gen_line_sum = 5000
