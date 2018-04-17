package com.sample.scaffold.service.biz;

import com.sample.scaffold.contract.dto.SingleDto;

import javax.validation.constraints.NotNull;


public interface ISingleService {
    SingleDto findOneSingle(@NotNull(message = "id不允许为空") Long id) throws Exception;

    void deleteSingle(@NotNull(message = "id不允许为空") Long id) throws Exception;

    void saveSingle(SingleDto singleDto) throws Exception;
}
