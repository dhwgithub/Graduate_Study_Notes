#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2020/12/28 17:07
# @Author  : Hewen Deng
# @File    : use_gpu.py
# @Desc    : 设置使用GPU个数
import tensorflow as tf


def set_use_gpu(num=1):
    """
    设置使用 GPU 个数最多是多少，若无法使用GPU则返回 False
    :param num: 最多使用 GPU 个数（>= 1）
    :return:
    """
    gpu = tf.config.experimental.list_physical_devices(device_type='GPU')
    if len(gpu) < 1 or num < 1:
        return False
    for gid in gpu[:num]:
        tf.config.experimental.set_memory_growth(gid, True)
    return True
