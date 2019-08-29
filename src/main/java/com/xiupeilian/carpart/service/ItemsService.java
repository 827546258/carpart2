package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Items;

import java.util.List;

public interface ItemsService {
    List<Items> findItemsByQueryVo(Items items);
}
