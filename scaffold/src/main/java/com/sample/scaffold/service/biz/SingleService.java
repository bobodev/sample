package com.sample.scaffold.service.biz;

import com.sample.scaffold.contract.dto.SingleDto;
import com.sample.scaffold.core.validate.annotation.ValidateAnno;
import com.sample.scaffold.model.Single;
import com.sample.scaffold.repository.SingleRepository;
import com.sample.scaffold.util.BeanMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleService implements ISingleService {

    @Autowired
    private SingleRepository singleRepository;

    @Override
    @ValidateAnno
    public SingleDto findOneSingle(Long id) throws Exception {
        Single single = singleRepository.findOne(id);
        SingleDto singleDto = BeanMapperUtil.getInstance().map(single, SingleDto.class);
        return singleDto;
    }

    @Override
    @ValidateAnno
    public void deleteSingle(Long id) throws Exception {
        singleRepository.delete(id);
    }

    @Override
    @ValidateAnno(cascadeValidate = true, fastFail = true)
    public void saveSingle(SingleDto singleDto) throws Exception {
        Single single = BeanMapperUtil.getInstance().map(singleDto, Single.class);
        this.singleRepository.save(single);
    }
}
