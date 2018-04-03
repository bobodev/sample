package com.sample.scaffold.service.biz;

import com.sample.scaffold.contract.dto.SingleDto;


public interface ISingleService {
    SingleDto findOneSingle(Long id) throws Exception;
    void deleteSingle(Long id) throws Exception;
    void saveSingle(SingleDto singleDto) throws Exception;
}
