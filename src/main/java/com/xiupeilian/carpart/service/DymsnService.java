package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.Notice;

import java.util.List;

public interface DymsnService {
    public List<Dymsn> findDymsns();

    List<Notice> findNotice();
}
